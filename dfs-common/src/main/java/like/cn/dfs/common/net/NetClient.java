package like.cn.dfs.common.net;

import cn.hutool.core.thread.NamedThreadFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import like.cn.dfs.common.codec.NettyPacket;
import like.cn.dfs.common.ex.RequestTimeoutException;
import like.cn.dfs.common.net.listener.NetClientFailListener;
import like.cn.dfs.common.net.listener.NettyConnectListener;
import like.cn.dfs.common.net.listener.NettyPacketListener;
import like.cn.dfs.common.utils.DefaultScheduler;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 负责和服务端（like nameNode）通讯的组件
 * <pre>
 * 1. 负责和服务端维持链接
 * 2. 提供同步、异步收发消息功能
 *
 * 可以通过指定 {@link #retryTimes} 来实现连接不上之后，可以重试多少次。
 *
 * 比如设置重试3次，累计重试3次都不成功后，则会不继续重试，
 * 会回调 {@link #addNetClientFailListener(like.cn.dfs.common.net.listener.NetClientFailListener)} 方法设置的监听器
 *
 * 如果需要监听连接状态变化，可以使用 {@link #addNettyPackageListener(like.cn.dfs.common.net.listener.NettyPacketListener)}
 *
 * 注意：如果设置了重试，{@link like.cn.dfs.common.net.listener.NettyConnectListener#onConnectStatusChanged(boolean)} 方法可能会被多次重复调用
 *
 *
 * 可以通过 {@link #send(like.cn.dfs.common.codec.NettyPacket)} 和 {@link #sendSync(like.cn.dfs.common.codec.NettyPacket)} 进行同步或异步的网络包发送
 *
 * 同样可以通过设置 {@link #addNettyPackageListener(like.cn.dfs.common.net.listener.NettyPacketListener)} 来异步监听底层的网络包
 *
 * </pre>
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/19 11:30
 */
@Slf4j
public class NetClient {

    /** 当前客户端的名字 */
    @Getter private final String name;
    /** 默认调度器 */
    private final DefaultScheduler defaultScheduler;
    /** 连接线程组 */
    private final EventLoopGroup connectThreadGroup;
    /** 默认通道处理程序 */
    private final DefaultChannelHandler defaultChannelHandler;
    /** 基础通道初始化 */
    private final BaseChannelInitializer baseChannelInitializer;
    /** 网络客户端失败的监听器集合 */
    private final List<NetClientFailListener> netClientFailListeners = new ArrayList<>();
    /** 标记是否启动 */
    private final AtomicBoolean started = new AtomicBoolean(false);
    /** 连接重试次数 */
    @Setter private int retryTimes;

    public NetClient(String name, DefaultScheduler defaultScheduler) {
        this(name, defaultScheduler, -1);
    }

    public NetClient(String name, DefaultScheduler defaultScheduler, int retryTimes) {
        this(name, defaultScheduler, retryTimes, 3000);
    }

    public NetClient(String name, DefaultScheduler defaultScheduler, int retryTimes, int requestTimeout) {
        this.name = name;
        this.retryTimes = retryTimes;
        this.defaultScheduler = defaultScheduler;
        this.connectThreadGroup = new NioEventLoopGroup(1, new NamedThreadFactory("net client event-", false));
        this.defaultChannelHandler = new DefaultChannelHandler(name, defaultScheduler, requestTimeout);
        this.defaultChannelHandler.addNettyConnectListener(connected -> { // 添加网络状态处理器
            if (connected) {
                synchronized (NetClient.class) {
                    NetClient.this.notifyAll();
                }
            }
        });
        this.baseChannelInitializer = new BaseChannelInitializer();
        this.baseChannelInitializer.addHandler(defaultChannelHandler);
    }

    /**
     * 连接
     */
    public void connect(String ip, int port) {
        connect(ip, port, 0, 0);
    }

    /**
     * 关闭客户端
     */
    public void shutdown() {
        if (log.isDebugEnabled()) {
            log.debug("Shutdown NetClient : [name={}]", name);
        }
        if (started.compareAndSet(true, false)) {
            if (this.connectThreadGroup != null) {
                this.connectThreadGroup.shutdownGracefully(1, 1, TimeUnit.SECONDS);
            }
            this.defaultScheduler.shutdown();
            this.defaultChannelHandler.clearAllListener();
        } else
            log.error("{} shutdown fail maybe it not start!", this.name);
    }

    /**
     * 发送消息
     * <pre>
     *    1.调用{@link #connect(String, int, int, int)} 连接到服务端，绑定{@link #baseChannelInitializer}
     *    2.{@link like.cn.dfs.common.net.DefaultChannelHandler#channelActive(io.netty.channel.ChannelHandlerContext)}
     *    绑定了到ip:port的socketChannel
     * </pre>
     */
    public void send(NettyPacket nettyPacket) throws InterruptedException {
        ensureConnected();
        defaultChannelHandler.send(nettyPacket);
    }

    /**
     * 发送消息，同步获取返回结果
     * @param nettyPacket netty 包
     */
    public NettyPacket sendSync(NettyPacket nettyPacket) throws InterruptedException, RequestTimeoutException {
        ensureConnected();
        return defaultChannelHandler.sendSync(nettyPacket);
    }

    /**
     * 同步等待确保连接已经建立。
     * 如果连接断开了，会阻塞直到连接重新建立
     */
    public void ensureConnected() throws InterruptedException {
        ensureConnected(-1);
    }

    /**
     * 同步等待确保连接已经建立。
     * 如果连接断开了，会阻塞直到连接重新建立
     * @exception InterruptedException 中断异常
     */
    public void ensureConnected(int timeout) throws InterruptedException {
        int remainTimeout = timeout;
        synchronized (this) {
            while (!isConnected()) {
                if (started.get()) {
                    // throw new InterruptedException("无法连接上服务器：" + name);
                    break;
                }
                if (timeout > 0) {
                    if (remainTimeout <= 0) {
                        throw new InterruptedException("无法连接上服务器：" + name);
                    }
                    this.wait(10);
                    remainTimeout -= 10;
                } else wait(10);
            }
        }
    }

    /**
     * 是否已经连接
     * @return boolean
     */
    public boolean isConnected() {
        return defaultChannelHandler.isConnected();
    }

    /**
     * 添加handler
     */
    public void addHandler(@NonNull AbstractChannelHandler handler) {
        defaultChannelHandler.markHasOtherHandler(true);
        baseChannelInitializer.addHandler(handler);
    }


    /**
     * 添加数据包侦听器
     */
    public void addNettyPackageListener(@NonNull NettyPacketListener listener) {
        this.defaultChannelHandler.addNettyPacketListener(listener);
    }

    /**
     * 添加网络连接状态监听器
     */
    public void addNettyConnectListener(@NonNull NettyConnectListener listener) {
        this.defaultChannelHandler.addNettyConnectListener(listener);
    }

    /**
     * 添加网络客户端连接失败的监听器
     */
    public void addNetClientFailListener(@NonNull NetClientFailListener netClientFailListener) {
        this.netClientFailListeners.add(netClientFailListener);
    }

    /**
     * 连接
     * <PRE>
     * 异步连接，保证在后台执行，主要作用是为了重试
     * </PRE>
     * @param ip           ip地址
     * @param port         端口号
     * @param connectTimes 当前重试次数
     * @param delay        延迟
     */
    private void connect(String ip, int port, final int connectTimes, int delay) {
        defaultScheduler.scheduleOnce("连接服务器", () -> {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(connectThreadGroup)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.ALLOCATOR, ByteBufAllocator.DEFAULT)
                    .channel(NioSocketChannel.class)
                    .handler(baseChannelInitializer);
            ResourceLeakDetector.setLevel(Level.ADVANCED);
            try {
                ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
                // mark start
                this.started.compareAndSet(false, true);
                channelFuture.channel().closeFuture().addListener(( ChannelFutureListener ) f -> f.channel().close());
                ChannelFuture sync = channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                log.info("发起连接后同步等待连接被打断");
            } finally {
                int curConnectTimes = connectTimes + 1;
                maybeRetry(ip, port, curConnectTimes);  // 重试
            }
        }, delay);
    }

    /**
     * 尝试重新连接
     */
    private void maybeRetry(String ip, int port, int connectTimes) {
        if (started.get()) {
            boolean retry = retryTimes < 0 || connectTimes <= retryTimes;
            if (retryTimes == -1 || retry) {
                log.error("重新发起连接：[started={}, name={}]", started.get(), name);
                connect(ip, port, connectTimes, 3000);
            } else {
                shutdown();
                log.info("重试次数超出阈值，不再进行重试：[retryTime={}]", retryTimes);
                for (NetClientFailListener listener : new ArrayList<>(netClientFailListeners)) {
                    try {
                        listener.onConnectFail();
                    } catch (Exception e) {
                        log.error("Exception occur on invoke listener :", e);
                    }
                }
            }
        }
    }
}

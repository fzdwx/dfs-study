package like.cn.dfs.common.net;

import cn.hutool.core.thread.NamedThreadFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import like.cn.dfs.common.utils.DefaultScheduler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * 网络服务
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/18 14:43
 */
@Slf4j
public class NetServer {

    private final String name;            // 当前Netty Server的名字
    private final boolean supportEpoll;   // 是否支持epoll
    private final EventLoopGroup boss;    // Netty 调度线程组
    private final EventLoopGroup worker;  // Netty 工作线程组
    private final DefaultScheduler defaultScheduler; // 线程调度器
    @Setter private BaseChannelInitializer baseChannelInitializer; // 消息通道的初始化器，主要定义了消息中的channel的处理器(编解码)

    public NetServer(String name, DefaultScheduler defaultScheduler) {
        this(name, defaultScheduler, 0, false);
    }

    public NetServer(String name, DefaultScheduler defaultScheduler, int workerThreads, boolean supportEpoll) {
        this.name = name;
        this.supportEpoll = supportEpoll;
        this.boss = new NioEventLoopGroup(0, new NamedThreadFactory("net-server-boss-", false));
        this.worker = new NioEventLoopGroup(workerThreads, new NamedThreadFactory("net-server-worker-", false));
        this.defaultScheduler = defaultScheduler;
        this.baseChannelInitializer = new BaseChannelInitializer();
    }

    /**
     * 添加自定义的处理器,在启动前添加
     */
    public void addHandlers(List<AbstractChannelHandler> handlers) {
        baseChannelInitializer.addHandlers(handlers);
    }

    /**
     * 绑定端口,同步等待关闭
     *
     * @throws InterruptedException 中断异常
     */
    public void bind(int port) throws InterruptedException {
        bind(singletonList(port));
    }

    /**
     * 绑定端口,同步等待关闭
     *
     * @throws InterruptedException 中断异常
     */
    public void bind(List<Integer> ports) throws InterruptedException {
        internalBind(ports);
    }

    /**
     * 异步绑定端口
     */
    public void bindAsync(int port) {
        bindAsync(singletonList(port));
    }

    /**
     * 异步绑定端口
     */
    public void bindAsync(List<Integer> ports) {
        defaultScheduler.scheduleOnce("绑定服务端口", () -> {
            try {
                internalBind(ports);
            } catch (InterruptedException e) {
                log.info("NetServer internalBind is Interrupted !!");
            }
        }, 0);
    }

    /**
     * 关闭net server
     */
    public void shutdown() {
        log.info("Shutdown NetServer : [name={}]", name);
        if (boss != null && worker != null) {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    /**
     * 绑定端口,启动服务
     *
     * @throws InterruptedException 中断异常
     */
    private void internalBind(List<Integer> ports) throws InterruptedException {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(supportEpoll ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(baseChannelInitializer)
            ;
            // 启用高级采样资源泄漏检测，以高开销为代价报告最近访问泄漏对象的位置
            ResourceLeakDetector.setLevel(Level.ADVANCED);
            // 启动channel监听指定的端口
            ArrayList<ChannelFuture> channelFutures = new ArrayList<>();
            for (int port : ports) {
                ChannelFuture future = bootstrap.bind(port).sync();
                log.info("[Net Server started] port : {}", port);

                ChannelFuture closeFuture = future.channel().closeFuture();
                closeFuture.addListener(( ChannelFutureListener ) f -> f.channel().close());
                closeFuture.sync();

                channelFutures.add(future);
            }
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}

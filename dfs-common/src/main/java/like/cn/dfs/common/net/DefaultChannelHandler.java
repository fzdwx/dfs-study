package like.cn.dfs.common.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import like.cn.dfs.common.Constants;
import like.cn.dfs.common.codec.NettyPacket;
import like.cn.dfs.common.ex.RequestTimeoutException;
import like.cn.dfs.common.net.listener.NettyConnectListener;
import like.cn.dfs.common.net.listener.NettyPacketListener;
import like.cn.dfs.common.utils.DefaultScheduler;
import like.cn.dfs.common.utils.NetUtils;
import like.cn.dfs.common.utils.PrettyCodes;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 默认消息处理器
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/19 11:35
 */
@Slf4j
public class DefaultChannelHandler extends AbstractChannelHandler {

    /** 当前处理器的名字 */
    private final String name;
    /** 同步请求支持 */
    private final SyncRequestSupport syncRequestSupport;
    private final List<NettyConnectListener> nettyConnectListeners = new CopyOnWriteArrayList<>();
    private final List<NettyPacketListener> nettyPacketListeners = new CopyOnWriteArrayList<>();
    private volatile boolean hasOtherHandler = false;
    private volatile SocketChannel socketChannel;

    public DefaultChannelHandler(String name, DefaultScheduler defaultScheduler, int requestTimeout) {
        this.name = name;
        this.syncRequestSupport = new SyncRequestSupport(name, defaultScheduler, requestTimeout);
    }

    /**
     * 发送消息，异步转同步获取响应
     * @param nettyPacket 网络包
     * @return 响应
     * @exception IllegalStateException 网络异常
     */
    public NettyPacket sendSync(NettyPacket nettyPacket) throws RequestTimeoutException {
        return this.syncRequestSupport.sendRequest(nettyPacket);
    }

    /**
     * 发送消息，不需要同步获取响应
     * <p>
     * 可以通过 {@link #addNettyPacketListener(like.cn.dfs.common.net.listener.NettyPacketListener)} 方法获取返回的数据包
     * @exception IllegalStateException 网络异常
     */
    public void send(NettyPacket nettyPacket) throws InterruptedException {
        this.setSequence(nettyPacket);
        this.socketChannel.writeAndFlush(nettyPacket);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        socketChannel = ( SocketChannel ) ctx.channel();
        syncRequestSupport.setSocketChannel(socketChannel);
        invokeConnectListener(true);
        log.debug("Socket channel is connected. {}", NetUtils.getChannelId(ctx.channel()));
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        socketChannel = null;
        syncRequestSupport.setSocketChannel(socketChannel);
        invokeConnectListener(false);
        log.debug("Socket channel is disconnected！{}", NetUtils.getChannelId(ctx.channel()));
        ctx.fireChannelInactive();
    }

    /**
     * 标记有其他处理程序
     * @param hasOtherHandler 有其他处理程序
     */
    public void markHasOtherHandler(boolean hasOtherHandler) {
        this.hasOtherHandler = hasOtherHandler;
    }

    /**
     * 是否连接,通过使用volatile变量，保证可见性
     */
    public boolean isConnected() {
        return socketChannel != null;
    }


    /**
     * 添加连接监听器
     * @param listener 侦听器 {@link like.cn.dfs.common.net.listener.NettyConnectListener}
     */
    public void addNettyConnectListener(NettyConnectListener listener) {
        this.nettyConnectListeners.add(listener);
    }

    /**
     * 添加数据包监听器
     * @see {@link #invokeListeners(RequestWrapper)}
     */
    public void addNettyPacketListener(NettyPacketListener listener) {
        this.nettyPacketListeners.add(listener);
    }

    /**
     * 清除所有侦听器
     */
    public void clearAllListener() {
        this.nettyConnectListeners.clear();
        this.nettyPacketListeners.clear();
    }

    public SocketChannel socketChannel() {
        return socketChannel;
    }

    /**
     * 回调连接监听器
     * @param isConnected 是否连接上
     */
    private void invokeConnectListener(boolean isConnected) {
        for (NettyConnectListener listener : nettyConnectListeners) {
            try {
                listener.onConnectStatusChanged(isConnected);
            } catch (Exception e) {
                log.error("Exception occur on invoke listener :", e);
            }
        }
    }

    /**
     * 设置请求序列号
     * @param nettyPacket 网状的包
     */
    private void setSequence(NettyPacket nettyPacket) {
        if (this.socketChannel == null) {
            throw new IllegalStateException("Socket channel is disconnect.");
        }
        nettyPacket.setSequence(name + "-" + Constants.REQUEST_COUNTER.get());
    }

    /**
     * 回调消息监听器
     * @param requestWrapper 网络包
     */
    private void invokeListeners(RequestWrapper requestWrapper) {
        for (NettyPacketListener listener : nettyPacketListeners) {
            try {
                listener.onMessage(requestWrapper);
            } catch (Exception e) {
                log.error("Exception occur on invoke listener :", e);
            }
        }
    }

    @Override
    protected boolean handler(ChannelHandlerContext ctx, NettyPacket nettyPacket) throws Exception {
        synchronized (this) {
            boolean ret = syncRequestSupport.onResponse(nettyPacket);
            RequestWrapper requestWrapper = new RequestWrapper(ctx, nettyPacket);
            invokeListeners(requestWrapper);
            return !hasOtherHandler || ret;
        }
    }

    @Override
    protected Set<Integer> interestPackageTypes() {
        return PrettyCodes.interestAll();
    }
}

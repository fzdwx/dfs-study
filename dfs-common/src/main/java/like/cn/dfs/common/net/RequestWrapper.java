package like.cn.dfs.common.net;

import io.netty.channel.ChannelHandlerContext;
import like.cn.dfs.common.codec.NettyPacket;

/**
 * 网络请求的包装器
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/19 12:11
 */
public class RequestWrapper {

    private final int nodeId;
    private final String requestSequence;
    private final OnResponseListener listener;
    private final ChannelHandlerContext ctx;
    /** 请求 */
    private final NettyPacket request;

    public RequestWrapper(ChannelHandlerContext ctx, NettyPacket nettyPacket) {
        this(ctx, nettyPacket, -1, null);
    }

    public RequestWrapper(ChannelHandlerContext ctx, NettyPacket nettyPacket, int nodeId, OnResponseListener listener) {
        this.ctx = ctx;
        this.request = nettyPacket;
        this.requestSequence = request.getSequence();
        this.nodeId = nodeId;
        this.listener = listener;
    }

    public NettyPacket getRequest() {
        return this.request;
    }

    // TODO: 2021/9/19

    public interface OnResponseListener {
        void onResponse(int bodyLength);
    }
}

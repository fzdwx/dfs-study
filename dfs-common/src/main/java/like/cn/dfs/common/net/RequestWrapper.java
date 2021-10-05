package like.cn.dfs.common.net;

import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelHandlerContext;
import like.cn.dfs.common.Constants;
import like.cn.dfs.common.codec.NettyPacket;
import like.cn.dfs.common.enums.NettyPacketType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 网络请求的包装器
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/19 12:11
 */
@Slf4j
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

    /**
     * 发送响应
     */
    public void sendResponse() {
        sendResponse(null);
    }

    /**
     * 发送响应
     * @param response 响应
     */
    public void sendResponse(MessageLite response) {
        byte[] body = response == null ? new byte[0] : response.toByteArray();
        NettyPacket nettyResponse = NettyPacket.buildPacket(body, NettyPacketType.getEnum(request.getPacketType()));
        // 手动分包
        List<NettyPacket> responses = nettyResponse.partitionChunk(request.isSupportChunked(), Constants.CHUNKED_SIZE);
        if (responses.size() > 1) {
            log.info("返回响应通过chunked方式，共拆分为{}个包", responses.size());
        }
        for (NettyPacket res : responses) {
            sendResponse(res, requestSequence);
        }
    }

    public void sendResponse(NettyPacket response, String sequence) {
        response.setSequence(sequence);
        response.setNodeId(nodeId);
        ctx.writeAndFlush(response);
        if (listener != null) {
            listener.onResponse(response.getBody().length);
        }
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    // TODO: 2021/9/19

    public interface OnResponseListener {
        void onResponse(int bodyLength);
    }
}

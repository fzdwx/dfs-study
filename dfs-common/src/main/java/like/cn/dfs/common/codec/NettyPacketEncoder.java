package like.cn.dfs.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 消息编码器
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/18 15:53
 */
public class NettyPacketEncoder extends MessageToByteEncoder<NettyPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyPacket msg, ByteBuf out) throws Exception {
        msg.write(out);
    }
}

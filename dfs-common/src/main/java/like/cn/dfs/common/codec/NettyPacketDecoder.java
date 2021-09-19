package like.cn.dfs.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.ReferenceCountUtil;

/**
 * 消息解码器
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021-09-18 15:33:51
 * @see LengthFieldBasedFrameDecoder
 */
public class NettyPacketDecoder extends LengthFieldBasedFrameDecoder {

    /**
     * 网络包解码器
     * <ol>
     *     <li>第0~2位表示当前包有多大(3个字节)，最长支持16MB</li>
     *     <li>initialBytesToStrip: 表示剥离3个字节，最终的数据从3开始读取</li>
     * </ol>
     */
    public NettyPacketDecoder(int maxFrameLength) {
        super(maxFrameLength, 0, 3, 0, 3);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf buf = ( ByteBuf ) super.decode(ctx, in);
        if (buf != null) {
            try {
                return NettyPacket.parseToPacket(buf); // 解析为我们定义好的格式对应的消息
            } finally {
                ReferenceCountUtil.release(buf);
            }
        }
        return null;
    }
}

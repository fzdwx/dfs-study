package like.cn.dfs.common.codec;

import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import like.cn.dfs.model.common.NettyPacketHeader;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 实际的消息内容格式
 * <pre>
 *    NettyPacket数据格式
 *   +--------+-------------------------------+---------------+-----------------------------+
 *   | HeaderLength | Actual Header (18byte)  | ContentLength | Actual Content (25byte)     |
 *   | 0x0012       | Header Serialization    | 0x0019        | Body  Serialization         |
 *   +--------------+-------------------------+---------------+-----------------------------+
 *     4byte                                     4 byte
 * </pre>
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/18 15:35
 */
@Slf4j
@Builder
public class NettyPacket {

    /** 消息体 */
    protected byte[] body;
    /** 请求头 */
    private Map<String, String> headers;

    /**
     * 解包
     *
     * @see {@link NettyPacket#write(io.netty.buffer.ByteBuf)}
     */
    public static NettyPacket parseToPacket(ByteBuf buf) throws InvalidProtocolBufferException {
        // head length
        int headLength = buf.readInt();
        byte[] headerBytes = new byte[headLength];

        // head
        buf.readBytes(headerBytes);
        NettyPacketHeader nettyPacketHeader = NettyPacketHeader.parseFrom(headerBytes);

        // body length
        int bodyLength = buf.readInt();
        byte[] bodyBytes = new byte[bodyLength];

        // body
        buf.readBytes(bodyBytes);
        return NettyPacket.builder()
                .headers(new HashMap<>(nettyPacketHeader.getHeadersMap()))
                .body(bodyBytes)
                .build();
    }

    /**
     * 将当前消息写入到byteBuf中
     *
     * @see {@link NettyPacket#parseToPacket(io.netty.buffer.ByteBuf)}
     */
    public void write(ByteBuf out) {
        NettyPacketHeader nettyPacketHeader = NettyPacketHeader.newBuilder()
                .putAllHeaders(headers).build();
        byte[] headerBytes = nettyPacketHeader.toByteArray();
        out.writeInt(headerBytes.length);
        out.writeBytes(headerBytes);
        out.writeInt(body.length);
        out.writeBytes(body);
    }

    /**
     * 获取当前消息的类型
     */
    public int getPacketType() {
        return Integer.parseInt(headers.getOrDefault("packetType", "0"));
    }

    public byte[] getBody() {
        return body;
    }
}

package like.cn.dfs.common.codec;

import cn.hutool.core.util.StrUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import like.cn.dfs.common.enums.NettyPacketType;
import like.cn.dfs.common.utils.PrettyCodes;
import like.cn.dfs.model.common.NettyPacketHeader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/18 15:35
 */
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NettyPacket {

    /** 消息体 */
    protected byte[] body;
    /** 请求头 */
    private Map<String, String> headers;

    /**
     * 解包
     * @see {@link like.cn.dfs.common.codec.NettyPacket#write(io.netty.buffer.ByteBuf)}
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
     * 构建消息，传入消息体，以及消息类型
     * @return {@link NettyPacket}
     */
    public static NettyPacket buildPacket(byte[] body, NettyPacketType packetType) {
        NettyPacket res = NettyPacket.builder()
                .body(body)
                .headers(PrettyCodes.trimMap())
                .build();
        res.setPacketType(packetType.value);
        return res;
    }

    /**
     * 将当前消息写入到byteBuf中
     * @see {@link like.cn.dfs.common.codec.NettyPacket#parseToPacket(io.netty.buffer.ByteBuf)}
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
     * 合并消息
     * @see {@link #parseToPacket(io.netty.buffer.ByteBuf)} 在这里分包
     */
    public void mergeChunkBody(NettyPacket other) {
        int newBodyLength = body.length + other.getBody().length;
        byte[] newBody = new byte[newBodyLength];
        System.arraycopy(body, 0, newBody, 0, body.length);
        System.arraycopy(other.getBody(), 0, newBody, body.length, other.getBody().length);
        this.body = newBody;
    }

    /**
     * 拆分消息体
     * @return {@link java.util.List}<{@link NettyPacket}>
     */
    public List<NettyPacket> partitionChunk(boolean supportChunked, int maxPackageSize) {
        if (!supportChunked) {
            return Collections.singletonList(this);
        }
        int bodyLength = body.length;
        if (bodyLength <= maxPackageSize) {
            // 不需要拆包
            return Collections.singletonList(this);
        }

        // 开始拆包
        int packageCount = bodyLength / maxPackageSize;
        if (bodyLength % maxPackageSize > 0) {
            packageCount++;
        }
        List<NettyPacket> results = new LinkedList<>();
        int remainLength = bodyLength;
        for (int i = 0; i < packageCount; i++) {
            int partitionBodyLength = Math.min(maxPackageSize, remainLength);
            byte[] partitionBody = new byte[partitionBodyLength];
            System.arraycopy(body, bodyLength - remainLength, partitionBody, 0, partitionBodyLength);
            remainLength -= partitionBodyLength;
            NettyPacket partitionPackage = new NettyPacket();
            partitionPackage.body = partitionBody;
            partitionPackage.headers = this.headers;
            partitionPackage.setSupportChunked(true);
            results.add(partitionPackage);
        }
        // 增加一个结束标记包
        NettyPacket tailPackage = new NettyPacket();
        tailPackage.body = new byte[0];
        tailPackage.headers = this.headers;
        tailPackage.setSupportChunked(true);
        results.add(tailPackage);
        return results;
    }

    /**
     * 获取当前消息的类型
     */
    public int getPacketType() {
        return Integer.parseInt(headers.getOrDefault("packetType", "0"));
    }

    /**
     * 设置当前消息的类型
     */
    public void setPacketType(int packetType) {
        this.headers.put("packetType", String.valueOf(packetType));
    }

    /**
     * 获取当前请求的超时时间
     */
    public long getTimeoutInMs() {
        return Long.parseLong(headers.getOrDefault("timeoutInMs", "0"));
    }

    /**
     * 设置当前请求的超时时间
     */
    public void setTimeoutInMs(long timeoutInMs) {
        headers.put("timeoutInMs", String.valueOf(timeoutInMs));
    }

    /**
     * 获取请求序列号
     * @return {@link String}
     */
    public String getSequence() {
        return headers.get("seq");
    }

    /**
     * 设置请求序列号
     * @param sequence 请求序列号
     */
    public void setSequence(@NonNull String sequence) {
        headers.put("seq", sequence);
    }

    /**
     * 是否支持chunk
     * @return boolean
     */
    public boolean isSupportChunked() {
        return Boolean.parseBoolean(headers.getOrDefault("supportChunked", "false"));
    }

    /**
     * 是否支持chunk
     */
    public void setSupportChunked(boolean chunkedFinish) {
        headers.put("supportChunked", String.valueOf(chunkedFinish));
    }

    public byte[] getBody() {
        return body;
    }

    public void setNodeId(int nodeId) {
        headers.put("nodeId", String.valueOf(nodeId));
    }

    @Override
    public String toString() {
        return "{" +
                "body=" + StrUtil.str(body, StandardCharsets.UTF_8) +
                ", headers=" + headers +
                '}';
    }

    public boolean isError() {
        return !isSuccess();
    }

    public String getError() {
        return headers.getOrDefault("error", null);
    }

    public String getUsername() {
        return headers.get("username");
    }

    public void setUsername(String username) {
        headers.put("username", username);
    }

    public void setAck(int ack) {
        headers.put("ack", String.valueOf(ack));
    }

    public boolean getBroadcast() {
        return Boolean.parseBoolean(headers.getOrDefault("broadcast", "false"));
    }

    public void setBroadcast(boolean broadcast) {
        headers.put("broadcast", String.valueOf(broadcast));
    }

    public String getUserToken() {
        return headers.getOrDefault("userToken", "");
    }

    public void setUserToken(String token) {
        headers.put("userToken", token);
    }

    private boolean isSuccess() {
        return getError() == null;
    }
}

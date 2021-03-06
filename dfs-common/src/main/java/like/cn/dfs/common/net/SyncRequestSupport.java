package like.cn.dfs.common.net;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.socket.SocketChannel;
import like.cn.dfs.common.Constants;
import like.cn.dfs.common.codec.NettyPacket;
import like.cn.dfs.common.ex.RequestTimeoutException;
import like.cn.dfs.common.utils.DefaultScheduler;
import lombok.Setter;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 发送同步请求的支持
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/19 11:41
 */
public class SyncRequestSupport {

    private final String name;
    private final Map<String, SyncRequestPromise> promiseMap = new ConcurrentHashMap<>();
    @Setter private SocketChannel socketChannel;

    public SyncRequestSupport(String name, DefaultScheduler defaultScheduler, int requestTimeout) {
        this.name = name;
        // 起一个任务每隔一秒执行一次，轮询，检查是否超时
        defaultScheduler.schedule("超时检测", () -> checkRequest(requestTimeout), 0, 1000, TimeUnit.MILLISECONDS);
    }

    /**
     * 收到服务端返回的消息
     * <p>
     * 主要作用是判断当前promiseMap中是否存在相同的seq的消息
     * <pre>
     *     1.保存为response
     *     2.判断是否支持合并
     * </pre>
     */
    public boolean onResponse(NettyPacket nettyPacket) {
        String sequence = nettyPacket.getSequence();
        if (StrUtil.isNotBlank(sequence)) {
            boolean isChunkFinish = !nettyPacket.isSupportChunked() || nettyPacket.getBody().length == 0;
            SyncRequestPromise promise;
            if (isChunkFinish) {
                promise = promiseMap.remove(sequence);
            } else {
                promise = promiseMap.get(sequence);
            }
            if (promise != null) {
                promise.result(nettyPacket);
                return true;
            }
        }
        return false;
    }

    /**
     * 同步发送请求
     *
     * @return 响应
     */
    public NettyPacket sendRequest(NettyPacket nettyPacket) throws RequestTimeoutException {
        this.setSequence(nettyPacket);
        SyncRequestPromise promise = new SyncRequestPromise(nettyPacket);
        promiseMap.put(nettyPacket.getSequence(), promise);
        socketChannel.writeAndFlush(nettyPacket);
        return promise.result();
    }

    /**
     * 设置请求的序列号
     *
     * @param nettyPacket 请求
     */
    private void setSequence(NettyPacket nettyPacket) {
        if (socketChannel == null || !socketChannel.isActive()) {
            throw new IllegalStateException("Socket channel is disconnect.");
        }
        nettyPacket.setSequence(name + "-" + Constants.REQUEST_COUNTER.getAndIncrement());
    }

    /**
     * 检查请求是否超时
     * <pre>
     *     避免请求被hang死
     * </pre>
     *
     * @param requestTimeout 单位ms
     */
    private void checkRequest(int requestTimeout) {
        synchronized (this) {
            for (Entry<String, SyncRequestPromise> entry : promiseMap.entrySet()) {
                SyncRequestPromise requestPromise = entry.getValue();
                if (requestPromise.isTimeout(requestTimeout)) requestPromise.markTimeout();
            }
        }
    }
}

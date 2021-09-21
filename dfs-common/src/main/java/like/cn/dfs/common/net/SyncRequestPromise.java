package like.cn.dfs.common.net;

import like.cn.dfs.common.codec.NettyPacket;
import like.cn.dfs.common.enums.NettyPacketType;
import like.cn.dfs.common.ex.RequestTimeoutException;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步获取结果
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/19 11:45
 */
@Slf4j
public class SyncRequestPromise {

    /** 开始时间 */
    private final long startTime;
    /** 请求 */
    private final NettyPacket request;
    /** 响应 */
    private NettyPacket response;
    /** 是否超时 */
    private boolean isTimeout;
    /** 响应是否完成 */
    private volatile boolean receiveResponseCompleted = false;

    public SyncRequestPromise(NettyPacket request) {
        this.request = request;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * 判断该请求是否超时，单位ms
     */
    public boolean isTimeout(int requestTimeout) {
        if (isTimeout) return true;
        long timeoutInMs = request.getTimeoutInMs();
        if (timeoutInMs < 0) {return false;}
        long now = System.currentTimeMillis();
        if (timeoutInMs > 0) {
            return startTime + timeoutInMs < now;
        } else return startTime + requestTimeout < 0;
    }

    /**
     * 标记为超时
     */
    public void markTimeout() {
        if (isTimeout) return;
        isTimeout = true;
        synchronized (this) {
            this.notifyAll();
        }
    }

    /**
     * 添加响应结果
     */
    public void result(NettyPacket nettyPacket) {
        synchronized (this) {
            if (nettyPacket.isSupportChunked()) {
                if (nettyPacket.getBody().length == 0) {
                    this.receiveResponseCompleted = true;
                    notifyAll();
                } else {
                    if (this.response == null) {
                        this.response = nettyPacket;
                    } else {
                        this.response.mergeChunkBody(nettyPacket);
                    }
                }
            } else {
                this.response = nettyPacket;
                this.receiveResponseCompleted = true;
                notifyAll();
            }
        }
    }

    /**
     * 获取响应结果
     */
    public NettyPacket result() throws RequestTimeoutException {
        waitForResult();
        return response;
    }

    /**
     * 等待结果返回
     */
    private void waitForResult() {
        synchronized (this) {
            try {
                while (!receiveResponseCompleted && !isTimeout) {
                    wait(10);
                }
                if (isTimeout) {
                    throw new RequestTimeoutException("请求超时: " +
                            NettyPacketType.getEnum(request.getPacketType()).getDescription());
                }
            } catch (Exception e) {
                log.info("NettyPackageWrapper#waitForResult is interrupt !");
            }
        }
    }
}

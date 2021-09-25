package like.cn.dfs.namenode.server;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.FileUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import like.cn.dfs.common.Constants;
import like.cn.dfs.common.codec.NettyPacket;
import like.cn.dfs.common.enums.NettyPacketType;
import like.cn.dfs.common.net.AbstractChannelHandler;
import like.cn.dfs.common.net.RequestWrapper;
import like.cn.dfs.common.utils.DefaultScheduler;
import like.cn.dfs.common.utils.PrettyCodes;
import like.cn.dfs.model.client.MkdirRequest;
import like.cn.dfs.namenode.filesystem.DiskNameSystem;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.Executor;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/18 16:59
 */
@Slf4j
public class NameNodeHandler extends AbstractChannelHandler {

    private final DiskNameSystem diskNameSystem;
    public DefaultScheduler defaultScheduler;
    protected int nodeId;

    public NameNodeHandler(DefaultScheduler defaultScheduler, DiskNameSystem diskNameSystem) {
        this.defaultScheduler = defaultScheduler;
        this.diskNameSystem = diskNameSystem;
        this.nodeId = -1; // TODO: 2021/9/21
    }

    private boolean handlerMkdir(RequestWrapper requestWrapper) throws InvalidProtocolBufferException {
        // TODO: 2021/9/21 认证
        NettyPacket request = requestWrapper.getRequest();
        MkdirRequest mkdirRequest = MkdirRequest.parseFrom(request.getBody());
        // String realFileName = Constants.FileSeparator + request.getUserName() + mkdirRequest.getPath();
        String realFileName = FileUtil.normalize(Constants.FileSeparator + mkdirRequest.getPath());
        int nodeId = this.getNodeId(realFileName);
        if (isThisNode(nodeId)) {
            this.diskNameSystem.mkdir(realFileName, mkdirRequest.getAttrMap());
            requestWrapper.sendResponse();
        }

        return true;
    }

    private boolean handleFetchBackupNodeInfo(RequestWrapper requestWrapper) {
        return true;
    }

    private boolean isThisNode(int nodeId) {
        return this.nodeId == nodeId;
    }

    /**
     * 根据文件名获取处理该请求的节点ID
     * @param filename 文件名
     * @return 节点ID
     */
    private int getNodeId(String filename) {
        // TODO: 2021/9/21 集群
        return this.nodeId;
    }

    @Override
    protected boolean handler(ChannelHandlerContext ctx, NettyPacket nettyPacket) throws Exception {
        log.info("服务端接收到消息: {} ", nettyPacket);
        NettyPacketType packetType = NettyPacketType.getEnum(nettyPacket.getPacketType());
        StopWatch stopWatch = new StopWatch();
        // stopWatch.start();
        RequestWrapper requestWrapper = new RequestWrapper(ctx, nettyPacket, nodeId, (bodyLength) -> {
            // TODO: 2021/9/21 监控
            // stopWatch.stop();
        });
        return switch (packetType) {
            case MKDIR -> handlerMkdir(requestWrapper);
            case FETCH_BACKUP_NODE_INFO -> handleFetchBackupNodeInfo(requestWrapper);
            default -> false;
        };
    }

    @Override
    protected Set<Integer> interestPackageTypes() {
        return PrettyCodes.interestAll();
    }

    @Override
    protected Executor getExecutor() {
        return null;
    }
}

package like.cn.dfs.namenode.server;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import like.cn.dfs.common.Constants;
import like.cn.dfs.common.codec.NettyPacket;
import like.cn.dfs.common.enums.NettyPacketType;
import like.cn.dfs.common.net.AbstractChannelHandler;
import like.cn.dfs.common.net.RequestWrapper;
import like.cn.dfs.common.utils.DefaultScheduler;
import like.cn.dfs.common.utils.PrettyCodes;
import like.cn.dfs.model.client.AuthenticateInfoRequest;
import like.cn.dfs.model.client.AuthenticateInfoResponse;
import like.cn.dfs.model.client.CreateFileRequest;
import like.cn.dfs.model.client.CreateFileResponse;
import like.cn.dfs.model.client.MkdirRequest;
import like.cn.dfs.model.common.DataNode;
import like.cn.dfs.namenode.datanode.DataNodeInfo;
import like.cn.dfs.namenode.datanode.DataNodeManager;
import like.cn.dfs.namenode.filesystem.DiskNameSystem;
import like.cn.dfs.namenode.filesystem.Node;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * nameNode 消息处理器
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/18 16:59
 */
@Slf4j
public class NameNodeHandler extends AbstractChannelHandler {

    private final DiskNameSystem diskNameSystem;
    private final DataNodeManager dataNodeManager;
    private final UserManager userManager;
    public DefaultScheduler defaultScheduler;
    protected int nodeId;

    public NameNodeHandler(UserManager userManager, DefaultScheduler defaultScheduler, DiskNameSystem diskNameSystem, DataNodeManager dataNodeManager) {
        this.defaultScheduler = defaultScheduler;
        this.diskNameSystem = diskNameSystem;
        this.dataNodeManager = dataNodeManager;
        this.userManager = userManager;
        this.nodeId = -1; // TODO: 2021/9/21
    }

    /**
     * 处理{@link like.cn.dfs.common.enums.NettyPacketType#MKDIR} 创建文件夹请求
     */
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

    /**
     * 处理{@link like.cn.dfs.common.enums.NettyPacketType#CREATE_FILE} 创建文件(上传文件)请求
     */
    private boolean handlerCreateFile(RequestWrapper requestWrapper) throws Exception {
        final NettyPacket request = requestWrapper.getRequest();
        final CreateFileRequest createFileRequest = CreateFileRequest.parseFrom(request.getBody());
        final String realFileName = Constants.FileSeparator + request.getUsername() + createFileRequest.getFilename();
        final int nodeId = getNodeId(realFileName);
        if (this.nodeId == nodeId) {
            final Map<String, String> attrMap = new HashMap<>(createFileRequest.getAttrMap());
            attrMap.put(Constants.ATTR_FILE_SIZE, String.valueOf(createFileRequest.getFileSize()));
            final int replicaNum = getReplicaNum(attrMap, attrMap.get(Constants.ATTR_REPLICA_NUM));
            Node node = diskNameSystem.listFiles(realFileName);
            if (!ObjectUtil.isNull(node)) {
                throw new RuntimeException("文件已存在 :" + createFileRequest.getFilename());
            }
            List<DataNodeInfo> dataNodeList = dataNodeManager.allocateDataNodes(request.getUsername(), replicaNum, realFileName);
            // Prometheus.incCounter("namenode_put_file_count", "NameNode收到的上传文件请求数量");
            // Prometheus.hit("namenode_put_file_qps", "NameNode瞬时上传文件QPS");
            List<DataNode> dataNodes = dataNodeList.stream()
                    .map(e -> DataNode.newBuilder().setHostname(e.getHostname())
                            .setNioPort(e.getNioPort())
                            .setHttpPort(e.getHttpPort())
                            .build())
                    .collect(Collectors.toList());
            diskNameSystem.createFile(realFileName, attrMap);
            List<String> collect = dataNodeList.stream().map(DataNodeInfo::getHostname).collect(Collectors.toList());
            log.info("创建文件：[filename={}, datanodes={}]", realFileName, String.join(",", collect));
            CreateFileResponse response = CreateFileResponse.newBuilder()
                    .addAllDataNodes(dataNodes)
                    .setRealFileName(realFileName)
                    .build();
            requestWrapper.sendResponse(response);
        } else {
            forwardRequestToOtherNameNode(nodeId, requestWrapper);
        }
        return true;
    }

    /**
     * 处理认证请求
     */
    private boolean handleAuthenticate(RequestWrapper requestWrapper) throws InvalidProtocolBufferException {
        AuthenticateInfoRequest request = AuthenticateInfoRequest.parseFrom(requestWrapper.getRequest().getBody());
        boolean isBroadcastRequest = requestWrapper.getRequest().getBroadcast();
        if (!isBroadcastRequest) {
            boolean authenticate = userManager.login(requestWrapper.getCtx().channel(), request.getAuthenticateInfo());
            if (!authenticate) {
                throw new RuntimeException("认证失败：" + request.getAuthenticateInfo());
            }
            log.info("收到认证请求：[authenticateInfo={}]", request.getAuthenticateInfo());
            // 获取用户认证成功后的Token信息，将这个Token信息发送给其他节点
            String token = userManager.getTokenByChannel(requestWrapper.getCtx().channel());
            requestWrapper.getRequest().setUserToken(token);
            // 为了保证一致性，只有所有节点都认证通过了，才可以返回响应给客户端
            broadcastSync(requestWrapper);
            // 第一个收到认证的节点，写回响应
            AuthenticateInfoResponse response = AuthenticateInfoResponse.newBuilder()
                    .setToken(token)
                    .build();
            requestWrapper.sendResponse(response);
        } else {
            // 代码走到这里表示客户端认证请求是别的节点广播过来的，只需要保存Token就行了
            String userToken = requestWrapper.getRequest().getUserToken();
            String username = request.getAuthenticateInfo().split(",")[0];
            userManager.setToken(username, userToken);
            requestWrapper.sendResponse();
            log.info("收到别的节点广播过来的认证信息：[username={}, token={}]", username, userToken);
        }
        return true;
    }

    /**
     * todo 广播请求给所有的NameNode节点, 同时获取所有节点的响应
     * @param requestWrapper 请求
     * @return 请求结果
     */
    private List<NettyPacket> broadcastSync(RequestWrapper requestWrapper) {
/*        NettyPacket request = requestWrapper.getRequest();
        boolean isBroadcastRequest = request.getBroadcast();
        if (!isBroadcastRequest) {
            // 请求是客户端发过来的，并不是别的NameNode广播过来的
            request.setBroadcast(true);
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            List<NettyPacket> nettyPackets = new ArrayList<>(peerNameNodes.broadcastSync(request));
            stopWatch.stop();
            if (!nettyPackets.isEmpty()) {
                log.debug("同步发送请求给所有的NameNode，并获取到了响应: [sequence={}, broadcast={}, packetType={}, cost={} s]",
                        request.getSequence(), peerNameNodes.getAllNodeId(),
                        PacketType.getEnum(request.getPacketType()).getDescription(),
                        stopWatch.getTime() / 1000.0D);
            }
            return nettyPackets;
        }*/
        return new ArrayList<>();
    }

    /**
     * todo 请求转发到其他的节点名称
     */
    private void forwardRequestToOtherNameNode(int nodeId, RequestWrapper requestWrapper) {
        NettyPacket request = requestWrapper.getRequest();
        log.debug("转发请求到别的NameNode: [targetNodeId={}, sequence={}, packetType={}]", nodeId,
                request.getSequence(), NettyPacketType.getEnum(request.getPacketType()).getDescription());
        String sequence = request.getSequence();
        // NettyPacket response = peerNameNodes.sendSync(nodeId, request);
        // requestWrapper.sendResponse(response, sequence);
    }

    private int getReplicaNum(Map<String, String> attrMap, String replicaNumStr) {
        int replicaNum = 0;
        if (StrUtil.isNotBlank(replicaNumStr)) {
            replicaNum = Integer.parseInt(replicaNumStr);
            // 最少不能少于配置的数量
            replicaNum = Math.max(replicaNum, diskNameSystem.getNameNodeConfig().getReplicaNum());
            // 最大不能大于最大的数量
            replicaNum = Math.min(replicaNum, Constants.MAX_REPLICA_NUM);
        } else {
            replicaNum = diskNameSystem.getNameNodeConfig().getReplicaNum();
            attrMap.put(Constants.ATTR_REPLICA_NUM, String.valueOf(replicaNum));
        }
        return replicaNum;
    }

    /**
     * 根据{@link like.cn.dfs.common.enums.NettyPacketType}进行路由分发
     */
    private boolean doHandler(NettyPacketType packetType, RequestWrapper requestWrapper) throws Exception {
        return switch (packetType) {
            case MKDIR -> handlerMkdir(requestWrapper);
            case CREATE_FILE -> handlerCreateFile(requestWrapper);
            case FETCH_BACKUP_NODE_INFO -> handleFetchBackupNodeInfo(requestWrapper);
            case AUTHENTICATE -> handleAuthenticate(requestWrapper);
            default -> false;
        };
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
        return doHandler(packetType, requestWrapper);
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

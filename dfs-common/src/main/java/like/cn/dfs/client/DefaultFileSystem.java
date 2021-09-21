package like.cn.dfs.client;

import cn.hutool.core.io.FileUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import like.cn.dfs.client.tools.CommandLineListener;
import like.cn.dfs.common.codec.NettyPacket;
import like.cn.dfs.common.enums.NettyPacketType;
import like.cn.dfs.common.ex.RequestTimeoutException;
import like.cn.dfs.common.ha.BackupNodeManager;
import like.cn.dfs.common.net.NetClient;
import like.cn.dfs.common.net.RequestWrapper;
import like.cn.dfs.common.utils.DefaultScheduler;
import like.cn.dfs.common.utils.PrettyCodes;
import like.cn.dfs.model.backup.BackupNodeInfo;
import like.cn.dfs.model.client.MkdirRequest;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 文件系统客户端默认实现
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/19 11:26
 */
@Slf4j
public class DefaultFileSystem implements FileSystem {

    /** 配置 */
    private final FileSystemConfig config;
    /** 默认调度器 */
    private final DefaultScheduler defaultScheduler;
    /** 和备份节点通信 */
    private final BackupNodeManager backupNodeManager;
    /** netty 客户端 */
    private NetClient netClient;
    private CommandLineListener commandLineListener;

    public DefaultFileSystem(FileSystemConfig config) {
        int connectRetryTime = config.connectRetryTimes() > 0 ? config.connectRetryTimes() : -1;
        this.config = config;
        this.defaultScheduler = new DefaultScheduler("fs-client scheduler");
        this.netClient = new NetClient("fs-client nameNode-" + config.ip(), defaultScheduler, connectRetryTime);
        this.backupNodeManager = new BackupNodeManager(defaultScheduler);
    }

    /**
     * 启动文件系统客户端
     */
    public void start() throws InterruptedException {
        // 添加监听器,主要处理backUpNode升级为nameNode这个事件（重新连接nameNode）
        this.netClient.addNettyPackageListener(this::onReceiveBackupNodeInfoMessage);
        this.netClient.addNettyConnectListener(connected -> {
            if (connected) {  // 当成功连接后，发送auth认证，获取back node信息
                this.authenticate();
                this.fetchBackupInfo();
            }
        });
        this.netClient.addNetClientFailListener(() -> {
            log.info("dfs-client检测到NameNode挂了，标记NameNode已经宕机");
            backupNodeManager.markNameNodeDown();
            if (commandLineListener != null) {
                commandLineListener.onConnectFailed();
            }
        });
        this.netClient.connect(config.ip(), config.port());
        this.netClient.ensureConnected();
        log.info("和NameNode建立连接成功");
    }

    @Override
    public void send(String message) throws InterruptedException {
        this.netClient.send(NettyPacket.buildPacket(message.getBytes(StandardCharsets.UTF_8), NettyPacketType.UNKNOWN));
    }

    @Override
    public void mkdir(String path) throws Exception {
        mkdir(path, PrettyCodes.trimMap());
    }

    @Override
    public void mkdir(String path, Map<String, String> attr) throws Exception {
        // TODO: 2021/9/21 发送请求先认证
        path = FileUtil.normalize(path);
        MkdirRequest mkdirRequest = MkdirRequest.newBuilder().setPath(path).putAllAttr(attr).build();
        NettyPacket message = NettyPacket.buildPacket(mkdirRequest.toByteArray(), NettyPacketType.MKDIR);
        this.safeSendSync(message);
        log.info("[mkdir] success {}", path);
    }

    @Override
    public void shutdown() {
        this.netClient.shutdown();
    }

    private void safeSendSync(NettyPacket nettyPacket) throws RequestTimeoutException, InterruptedException {
        // TODO: 2021/9/21 设置用户名
        this.netClient.sendSync(nettyPacket);
    }

    /**
     * 获取备份信息
     */
    private void fetchBackupInfo() throws InterruptedException {
        NettyPacket nettyPacket = NettyPacket.buildPacket(PrettyCodes.trimBytes(), NettyPacketType.FETCH_BACKUP_NODE_INFO);
        netClient.send(nettyPacket);
    }

    /**
     * 发起身份认证请求
     */
    private void authenticate() {
        // TODO: 2021/9/20 认证
    }

    /**
     * 接收备份节点信息消息
     *
     * @throws InvalidProtocolBufferException 无效的协议缓冲区例外
     * @see {@link like.cn.dfs.common.enums.NettyPacketType#FETCH_BACKUP_NODE_INFO}
     */
    private void onReceiveBackupNodeInfoMessage(RequestWrapper requestWrapper) throws InvalidProtocolBufferException {
        NettyPacketType packetType = NettyPacketType.getEnum(requestWrapper.getRequest().getPacketType());
        if (NettyPacketType.FETCH_BACKUP_NODE_INFO.equals(packetType)) {
            this.handlerFetchBackupNodeInfoResponse(requestWrapper);
        }
    }

    /**
     * 获取备份节点信息,判断backupNode是否升级为了nameNode
     */
    private void handlerFetchBackupNodeInfoResponse(RequestWrapper requestWrapper) throws InvalidProtocolBufferException {
        if (0 == requestWrapper.getRequest().getBody().length) {
            log.warn("拉取backUp node信息为空，设置 {} 无限重试", this.netClient.getName());
            this.netClient.setRetryTimes(-1);
            return;
        }
        netClient.setRetryTimes(3);
        BackupNodeInfo backupNodeInfo = BackupNodeInfo.parseFrom(requestWrapper.getRequest().getBody());
        // 判断是需要重新连接nameNode（nameNode down了，backUpNode升级）
        backupNodeManager.maybeEstablishConnect(backupNodeInfo, (ip, port) -> {
            config.ip(ip);
            config.port(port);
            netClient.shutdown();
            log.info("检测到BackupNode升级为NameNode了，替换NameNode链接信息，并重新建立链接：[hostname={}, port={}]", config.ip(), config.port());
            netClient = new NetClient("fs client-nameNode-" + config.ip(), defaultScheduler);
            try {
                this.start();
            } catch (Exception e) {
                log.error("连接nameNode 失败", e);
            }
        });
    }
}

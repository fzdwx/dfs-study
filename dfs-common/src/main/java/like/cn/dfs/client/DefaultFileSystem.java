package like.cn.dfs.client;

import cn.hutool.core.io.FileUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import like.cn.dfs.client.tools.CommandLineListener;
import like.cn.dfs.client.tools.OnMultiFileProgressListener;
import like.cn.dfs.common.Constants;
import like.cn.dfs.common.codec.NettyPacket;
import like.cn.dfs.common.enums.NettyPacketType;
import like.cn.dfs.common.ex.RequestTimeoutException;
import like.cn.dfs.common.ha.BackupNodeManager;
import like.cn.dfs.common.net.NetClient;
import like.cn.dfs.common.net.RequestWrapper;
import like.cn.dfs.common.net.file.FileTransportClient;
import like.cn.dfs.common.net.file.OnProgressListener;
import like.cn.dfs.common.utils.DefaultScheduler;
import like.cn.dfs.common.utils.PrettyCodes;
import like.cn.dfs.model.backup.BackupNodeInfo;
import like.cn.dfs.model.client.AuthenticateInfoRequest;
import like.cn.dfs.model.client.AuthenticateInfoResponse;
import like.cn.dfs.model.client.CreateFileRequest;
import like.cn.dfs.model.client.CreateFileResponse;
import like.cn.dfs.model.client.MkdirRequest;
import like.cn.dfs.model.common.DataNode;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 文件系统客户端默认实现
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/19 11:26
 */
@Slf4j
public class DefaultFileSystem implements FileSystem {

    private static final int AUTH_INIT = 0;
    private static final int AUTH_SUCCESS = 1;
    private static final int AUTH_FAIL = 2;

    /** 配置 */
    private final FileSystemConfig config;
    /** 默认调度器 */
    private final DefaultScheduler defaultScheduler;
    /** 和备份节点通信 */
    private final BackupNodeManager backupNodeManager;
    /** netty 客户端 */
    private NetClient netClient;
    private CommandLineListener commandLineListener;
    private volatile int authStatus = AUTH_INIT;

    public DefaultFileSystem(FileSystemConfig config) {
        int connectRetryTime = config.connectRetryTimes() > 0 ? config.connectRetryTimes() : -1;
        this.config = config;
        this.defaultScheduler = new DefaultScheduler("fs-client scheduler");
        this.netClient = new NetClient("fs-client nameNode-" + config.ip() + ":" + config.port(), defaultScheduler, connectRetryTime);
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
        // 异步连接
        this.netClient.connect(config.ip(), config.port());
        this.netClient.ensureConnected();
        log.info("connect success {}", this.netClient.getName());
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
    public void close() {
        this.netClient.shutdown();
        this.defaultScheduler.shutdown();
    }

    @Override
    public void upload(File src, String des) throws Exception {
        upload(src, des, 1, PrettyCodes.trimMap());
    }

    @Override
    public void upload(File src, String des, int numOfReplica) throws Exception {
        upload(src, des, numOfReplica, PrettyCodes.trimMap());
    }

    @Override
    public void upload(File src, String des, int numOfReplica, Map<String, String> attr) throws Exception {
        upload(src, des, numOfReplica, attr, null);
    }

    @Override
    public void upload(File src, String des, int numOfReplica, Map<String, String> attr, OnProgressListener listener) throws Exception {
        // TODO: 2021/9/21 发送请求先认证
        des = FileUtil.normalize(des);
        if (numOfReplica > Constants.MAX_REPLICA_NUM) {
            throw new RuntimeException("不合法的副本数量：" + numOfReplica);
        }
        for (String key : Constants.KEYS_ATTR_SET) {
            if (attr.containsKey(key)) {
                log.warn("文件属性包含关键属性：[key={}]", key);
            }
        }
        if (numOfReplica > 0) {
            attr.put(Constants.ATTR_REPLICA_NUM, String.valueOf(numOfReplica));
        }
        // 构建请求
        CreateFileRequest request = CreateFileRequest.newBuilder()
                .setFilename(des)
                .setFileSize(src.length())
                .putAllAttr(attr)
                .build();
        final NettyPacket nettyPacket = NettyPacket.buildPacket(request.toByteArray(), NettyPacketType.CREATE_FILE);
        final NettyPacket resp = safeSendSync(nettyPacket); // 发送请求(保存到dfs中的路径,文件大小,文件属性)
        // 等待nameNode的响应
        CreateFileResponse response = CreateFileResponse.parseFrom(resp.getBody());
        OnMultiFileProgressListener onMultiFileProgressListener =
                new OnMultiFileProgressListener(listener, response.getDataNodesList().size());
        for (DataNode dataNode : response.getDataNodesList()) {
            final int port = dataNode.getNioPort();
            final String hostname = dataNode.getHostname();
            NetClient netClient = new NetClient("FSClient-DataNode-" + hostname, defaultScheduler);
            FileTransportClient fileTransportClient = new FileTransportClient(netClient);
            netClient.connect(hostname, port);
            netClient.ensureConnected();
            if (log.isDebugEnabled()) {
                log.debug("开始上传文件到：[node={}:{}, filename={}]", hostname, port, des);
            }
            fileTransportClient.sendFile(response.getRealFileName(), src.getAbsolutePath(), onMultiFileProgressListener, true);
            fileTransportClient.shutdown();
            if (log.isDebugEnabled()) {
                log.debug("完成上传文件到：[node={}:{}, filename={}]", hostname, port, des);
            }
        }
        /*
         * 文件上传是上传到DataNode节点，客户端上传到DataNode之后，DataNode再上报给NameNode节点中间有一个时间差
         * 为了达到强一致性，保证文件上传后，立马是可以读取文件的，需要等待NameNode收到DataNode上报的信息，才认为是上传成功的。
         * 但是这样一来会降低上传文件的吞吐量。 因为会占用NameNode一个线程池的线程在哪里hang住等待3秒，
         * 有可能让DataNode上报的请求在队列里面一直等待，最终出现超时错误。这里有两种方案可以选择：
         *
         * 1、 客户端可以配置让NameNode确认是否等待，如果开启确认等待，则吞吐量会下降，但是保证强一致性。如果不开启确认等待，则吞吐量比较高，
         *     但是一致性不能保证，就是说上传完毕后有可能立即读文件读不到
         *
         * 2、 在NameNode那边等待的过程，不要直接在线程里面等待，而是建立一个任务Task，保存在集合中，后台起一个线程，就无限循环的去判断
         *     这个Task是否完成，如果完成才写回响应。这种方式可以保证强一致性，并且不会阻塞线程池中的线程。
         *
         * 目前我们先采用第一种方式实现，第二种后面可以考虑优化实现。
         *
         */
        NettyPacket confirmRequest = NettyPacket.buildPacket(request.toByteArray(), NettyPacketType.CREATE_FILE_CONFIRM);
        confirmRequest.setTimeoutInMs(-1);
        confirmRequest.setAck(config.ack());
        safeSendSync(confirmRequest);
    }

    /**
     * 同步发送信息
     * <pre>
     *     1.设置用户名
     *     2.用户权限认证 todo
     *
     * </pre>
     */
    private NettyPacket safeSendSync(NettyPacket nettyPacket) throws RequestTimeoutException, InterruptedException {
        nettyPacket.setUsername(config.username());
        final NettyPacket res = this.netClient.sendSync(nettyPacket); // 发送消息
        if (res.isError()) {
            throw new RuntimeException(res.getError());
        }
        return res;
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
        defaultScheduler.scheduleOnce("发起认证", () -> {
            try {
                String authInfo = config.username() + "," + config.secret();
                AuthenticateInfoRequest req = AuthenticateInfoRequest.newBuilder()
                        .setAuthenticateInfo(authInfo)
                        .build();
                NettyPacket nettyPacket = NettyPacket.buildPacket(req.toByteArray(), NettyPacketType.AUTHENTICATE);
                NettyPacket resp = safeSendSync(nettyPacket);
                AuthenticateInfoResponse authenticateInfoResponse = AuthenticateInfoResponse.parseFrom(resp.getBody());
                authStatus = AUTH_SUCCESS;
                config.userToken(authenticateInfoResponse.getToken());
                notifyAuthenticate();
                log.info("发起认证成功：[username={}, token={}]", config.username(), authenticateInfoResponse.getToken());
                if (commandLineListener != null) {
                    commandLineListener.onAuthResult(true);
                }
            } catch (Exception e) {
                log.error("发起认证失败：", e);
                authStatus = AUTH_FAIL;
                close();
                if (commandLineListener != null) {
                    commandLineListener.onAuthResult(false);
                }
            }
        });
    }

    /**
     * 认证成功后，唤醒等待的线程
     */
    private void notifyAuthenticate() {
        synchronized (this) {
            notifyAll();
        }
    }

    /**
     * 接收备份节点信息消息
     * @exception InvalidProtocolBufferException 无效的协议缓冲区例外
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

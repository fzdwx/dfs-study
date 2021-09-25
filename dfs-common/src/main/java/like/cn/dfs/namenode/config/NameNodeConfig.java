package like.cn.dfs.namenode.config;

import like.cn.dfs.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/25 11:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NameNodeConfig {

    /**
     * 默认的文件目录
     */
    public static final String DEFAULT_BASEDIR = "/srv/ruyuan/dfs/namenode";

    /**
     * 默认监听的端口号
     */
    public static final int DEFAULT_PORT = 2345;
    /**
     * 默认EditLog Buffer刷磁盘的阈值
     */
    public static final int DEFAULT_EDITLOG_FLUSH_THRESHOLD = 524288;
    /**
     * 默认DataNode心跳超时的阈值
     */
    public static final int DEFAULT_DATANODE_HEARTBEAT_TIMEOUT = 90000;
    /**
     * 默认副本数量
     */
    public static final int DEFAULT_REPLICA_NUM = 2;
    /**
     * 默认检查DataNode是否心跳超时的时间间隔
     */
    public static final int DEFAULT_DATANODE_ALIVE_CHECK_INTERVAL = 30000;


    private String baseDir;
    private int port;
    /** EditLog Buffer刷磁盘的阈值 */
    private int editLogFlushThreshold;
    private long dataNodeHeartbeatTimeout;
    private int replicaNum;
    private long dataNodeAliveCheckInterval;
    private String nameNodePeerServers;
    private int nameNodeId;
    private String nameNodeLaunchMode;
    private int httpPort;
    private long nameNodeTrashCheckInterval;
    private long nameNodeTrashClearThreshold;
    private int nameNodeApiCoreSize;
    private int nameNodeApiMaximumPoolSize;
    private int nameNodeApiQueueSize;

    public String getEditLogFile(long startTxId, long endTxId) {
        return baseDir + Constants.FileSeparator + "editslog-" + startTxId + "_" + endTxId + ".log";
    }
}

package like.cn.dfs.namenode.datanode;

import like.cn.dfs.namenode.rebalance.RemoveReplicaTask;
import like.cn.dfs.namenode.rebalance.ReplicaTask;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Datanode的信息
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/10/5 14:28
 */
@Data
@Slf4j
public class DataNodeInfo {

    public static final int STATUS_INIT = 1;
    public static final int STATUS_READY = 2;

    private Integer nodeId;
    private String hostname;
    private int httpPort;
    private int nioPort;
    private long latestHeartbeatTime;
    private volatile long storedDataSize;
    private volatile long freeSpace;
    private int status;
    private ConcurrentLinkedQueue<ReplicaTask> replicaTasks = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<RemoveReplicaTask> removeReplicaTasks = new ConcurrentLinkedQueue<>();

    /**
     * 添加副本复制任务
     */
    public void addReplicaTask(ReplicaTask task) {
        replicaTasks.add(task);
    }
}

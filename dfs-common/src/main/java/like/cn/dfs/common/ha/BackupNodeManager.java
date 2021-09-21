package like.cn.dfs.common.ha;

import like.cn.dfs.common.net.NetClient;
import like.cn.dfs.common.utils.DefaultScheduler;
import like.cn.dfs.model.backup.BackupNodeInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 负责和backup node 通信
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/19 12:08
 */
@Slf4j
public class BackupNodeManager {

    private final DefaultScheduler defaultScheduler;
    private volatile NetClient netClient;
    private ReportNameNodeStatusHandler reportNameNodeStatusHandler;

    public BackupNodeManager(DefaultScheduler defaultScheduler) {
        this.defaultScheduler = defaultScheduler;
    }

    /**
     * 和backupNode建立连接,判断nameNode节点是否down了，如果down了，backNode就升级为nameNode
     */
    public void maybeEstablishConnect(BackupNodeInfo backupNodeInfo, BackupUpGradeListener backupUpGradeListener) {
        if (netClient == null) {
            synchronized (this) {
                if (netClient == null) {
                    netClient = new NetClient("dataNode-backupNode-" + backupNodeInfo.getHostname(), defaultScheduler, 3);
                    reportNameNodeStatusHandler = new ReportNameNodeStatusHandler(); // 向backNode返回当前nameNode的状态
                    netClient.addHandler(reportNameNodeStatusHandler);
                    netClient.connect(backupNodeInfo.getHostname(), backupNodeInfo.getPort());
                    log.info("收到NameNode返回的BackupNode信息，建立连接：[hostname={}, port={}]", backupNodeInfo.getHostname(), backupNodeInfo.getPort());
                    netClient.addNetClientFailListener(() -> {  // 当name node down 了之后当前backUpNode升级为nameNode
                        reset();
                        if (backupUpGradeListener != null) {
                            backupUpGradeListener.onBackupUpGrade(backupNodeInfo.getHostname(), backupNodeInfo.getPort());
                        }
                    });
                }
            }
        }
    }

    /**
     * 标识NameNode节点宕机了
     */
    public void markNameNodeDown() {
        if (reportNameNodeStatusHandler != null) {
            reportNameNodeStatusHandler.markNameNodeDown();
        }
    }

    private void reset() {
        this.netClient.shutdown();
        this.netClient = null;
        this.reportNameNodeStatusHandler = null;
    }

}

package like.cn.dfs.namenode.datanode;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import like.cn.dfs.common.FileInfo;
import like.cn.dfs.common.utils.DefaultScheduler;
import like.cn.dfs.common.utils.PrettyCodes;
import like.cn.dfs.namenode.config.NameNodeConfig;
import like.cn.dfs.namenode.rebalance.ReplicaTask;
import like.cn.dfs.namenode.server.User;
import like.cn.dfs.namenode.server.UserManager;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * 管理datanode
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @module
 * @date 2021-10-05 14:21:31
 */
@Slf4j
public class DataNodeManager {

    private final UserManager userManager;
    private final Map<String, DataNodeInfo> dataNodes = new ConcurrentHashMap<>();
    private final ReentrantReadWriteLock replicaLock = new ReentrantReadWriteLock();
    private final NameNodeConfig nameNodeConfig;

    /**
     * <pre>
     * 每个文件对应存储的Datanode信息
     *
     * 比如文件aaa.png，存储在datanode01、datanode02
     *
     *    aaa.png : [
     *        datanode01,
     *        datanode02
     *    ]
     * </pre>
     */
    private final Map<String, List<DataNodeInfo>> replicaByFilename = new ConcurrentHashMap<>();

    /**
     * <pre>
     * 每个DataNode 存储的文件列表
     *
     * 比如datanode01存储有文件：aaa.jpg、bbb.jpg
     *
     *    datanode01 : [
     *        aaa.jpg,
     *        bbb.jpg
     *    ]
     * </pre>
     */
    private final Map<String, Map<String, FileInfo>> filesByDataNode = new ConcurrentHashMap<>();

    public DataNodeManager(UserManager userManager, NameNodeConfig nameNodeConfig, DefaultScheduler defaultScheduler) {
        this.nameNodeConfig = nameNodeConfig;
        this.userManager = userManager;
        final long dataNodeAliveCheckInterval = nameNodeConfig.getDataNodeAliveCheckInterval();
        defaultScheduler.schedule("dataNode 存活检测", new DataNodeAliveMonitor(), dataNodeAliveCheckInterval, dataNodeAliveCheckInterval, TimeUnit.MILLISECONDS);
    }

    /**
     * 从内存数据结构中移除dataNode的文件列表并返回
     * @param hostname 主机名
     * @return {@link Map}<{@link String}, {@link FileInfo}> 该dataNode的文件列表
     */
    public Map<String, FileInfo> removeFileByDataNode(String hostname) {
        replicaLock.writeLock().lock();
        try {
            return filesByDataNode.remove(hostname);
        } finally {
            replicaLock.writeLock().unlock();
        }
    }

    /**
     * 根据文件名选择一个可读的dataNode，并把不可读的dataNod从文件对应的dataNode数据结构删除
     * @param fileName         文件名称
     * @param toRemoveDataNode 不可读的DataNode
     * @return 可读的DataNode
     */
    public DataNodeInfo chooseReadableDataNodeByFileName(String fileName, DataNodeInfo toRemoveDataNode) {
        replicaLock.readLock().lock();
        try {
            List<DataNodeInfo> dataNodeInfos = replicaByFilename.get(fileName);
            if (CollUtil.isEmpty(dataNodeInfos)) return null;

            if (ObjectUtil.isNull(toRemoveDataNode)) {
                dataNodeInfos.remove(toRemoveDataNode);
            }
            final int size = dataNodeInfos.size();
            final int i = RandomUtil.getRandom().nextInt(size);
            return dataNodeInfos.get(i);
        } finally {
            replicaLock.readLock().unlock();
        }
    }

    /**
     * 分配数据节点
     * @param username 用户名
     * @param count    申请的机器数量
     * @param filename 文件名
     * @return {@link List}<{@link DataNodeInfo}>
     */
    public List<DataNodeInfo> allocateDataNodes(String username, int count, String filename) {
        final User user = userManager.getUser(username);
        final Set<String> dataNodesSet = user.getStorageInfo().getDataNodesSet();
        if (dataNodesSet.isEmpty()) {
            final List<DataNodeInfo> sortedReadyDataNode = getSortedReadyDataNode();
            return selectDataNodeFromList(sortedReadyDataNode, count, filename);
        } else {
            // 这里用户是指定了DataNode的，则在指定的DataNode中查找对应的DataNode
            List<DataNodeInfo> dataNodeInfos = dataNodes.values().stream()
                    .filter(dataNodeInfo -> dataNodeInfo.getStatus() == DataNodeInfo.STATUS_READY
                            && dataNodesSet.contains(dataNodeInfo.getHostname()))
                    .sorted()
                    .collect(Collectors.toList());
            return selectDataNodeFromList(dataNodeInfos, count, filename);
        }
    }

    /**
     * 获取状态是ready并且排序的dataNode节点
     * @return {@link List}<{@link DataNodeInfo}>
     */
    private List<DataNodeInfo> getSortedReadyDataNode() {
        return dataNodes.values().stream()
                .filter(dataNodeInfo -> dataNodeInfo.getStatus() == DataNodeInfo.STATUS_READY)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * 创建丢失副本的复制任务
     * @param dataNodeInfo 宕机的DataNode
     */
    private void createLostReplicaTask(DataNodeInfo dataNodeInfo) {
        Map<String, FileInfo> filesByDataNode = removeFileByDataNode(dataNodeInfo.getHostname());
        if (ObjectUtil.isNull(filesByDataNode)) return;
        for (FileInfo fileInfo : filesByDataNode.values()) {
            // 找到一个可读取文件的DataNode
            final DataNodeInfo sourceDataNode = chooseReadableDataNodeByFileName(fileInfo.getFileName(), dataNodeInfo);
            if (sourceDataNode == null) {
                log.warn("警告：找不到适合的DataNode用来获取文件：" + fileInfo.getFileName());
                continue;
            }
            DataNodeInfo destDataNode = allocateReplicateDataNodes(fileInfo, sourceDataNode);
            if (destDataNode == null) {
                log.warn("警告：找不到适合的DataNode用来Rebalance");
                continue;
            }
            ReplicaTask task = new ReplicaTask(fileInfo.getFileName(), sourceDataNode.getHostname(), sourceDataNode.getNioPort());
            log.info("创建副本复制任务：[filename={}, from={}, to={}]", fileInfo.getFileName(),
                    sourceDataNode.getHostname(), destDataNode.getHostname());
            destDataNode.addReplicaTask(task);
        }
    }

    /**
     * 为复制任务申请副本，申请的dataNode需要排除目标dataNode
     * @param fileInfo        文件信息
     * @param excludeDataNode 排除的DataNode
     * @return {@link DataNodeInfo}
     */
    private DataNodeInfo allocateReplicateDataNodes(FileInfo fileInfo, DataNodeInfo excludeDataNode) {
        List<DataNodeInfo> dataNodeInfos = dataNodes.values().stream()
                .filter(dataNodeInfo -> !dataNodeInfo.equals(excludeDataNode) &&
                        dataNodeInfo.getStatus() == DataNodeInfo.STATUS_READY)
                .sorted()
                .collect(Collectors.toList());

        try {
            // 选择足够的dataNode
            List<DataNodeInfo> dataNodesList = selectDataNodeFromList(dataNodeInfos, 1, fileInfo.getFileName());
            return dataNodesList.get(0);
        } catch (Exception e) {
            log.warn("allocateReplicateDataNodes select node failed.", e);
            return null;
        }
    }

    /**
     * 从dataNode集合中选择节点，排除已经包含该文件的dataNode节点
     * @param dataNodeInfos     数据节点信息
     * @param requiredNodeCount 所需的节点数
     * @param fileName          文件名称
     * @return {@link List}<{@link DataNodeInfo}>
     */
    private List<DataNodeInfo> selectDataNodeFromList(List<DataNodeInfo> dataNodeInfos, int requiredNodeCount, String fileName) {
        int existCount = 0;
        long minStoredDataSize = -1;
        /*
         在上传小文件时，文件较小，为了避免流量都打到同一台dataNode机器
         所以如果多台dataNode之间的存储空间大小误差在1g内，直接随机取一台
         如果误差范围小于1g，则按存储空间大小从低到高进行获取
         */
        final ArrayList<DataNodeInfo> candidateNodes = new ArrayList<>(16);
        long delta = 1024 * 1024 * 1024; // 1g
        for (DataNodeInfo dataNodeInfo : dataNodeInfos) {
            if (containsFiles(dataNodeInfo.getHostname(), fileName)) {
                existCount++;
                continue;
            }
            final long storedDataSize = dataNodeInfo.getStoredDataSize();
            if (minStoredDataSize < 0) {
                minStoredDataSize = storedDataSize;
            }
            if (dataNodeInfo.getStoredDataSize() - minStoredDataSize <= delta) {
                candidateNodes.add(dataNodeInfo); // 添加误差在1g以内的节点
            }
        }
        int findCount = 0;
        if (candidateNodes.size() == requiredNodeCount) {
            // 误差在1G以内的DataNode数量刚好和需要的节点数量一样
            return candidateNodes;
        } else if (candidateNodes.size() < requiredNodeCount) {
            // 误差在1G以内的DataNode数量小于需要的节点数量，则需要从datanode列表中继续取到足够的节点。
            int remainNodeCount = requiredNodeCount - candidateNodes.size();
            for (DataNodeInfo dataNodeInfo : dataNodeInfos) {
                if (candidateNodes.contains(dataNodeInfo) || containsFiles(dataNodeInfo.getHostname(), fileName)) {
                    continue;
                }
                candidateNodes.add(dataNodeInfo);
                remainNodeCount--;
                findCount++;
                if (remainNodeCount <= 0) {
                    return candidateNodes;
                }
            }
        } else {
            // 误差在1G以内的DataNode数量很多，超过所需的节点数量，则随机取几个
            Random random = new Random();
            List<DataNodeInfo> selectedDataNodes = new ArrayList<>();
            for (int i = 0; i < requiredNodeCount; i++) {
                int index = random.nextInt(candidateNodes.size());
                DataNodeInfo dataNodeInfo = candidateNodes.get(index);
                if (selectedDataNodes.contains(dataNodeInfo) || containsFiles(dataNodeInfo.getHostname(), fileName)) {
                    continue;
                }
                selectedDataNodes.add(dataNodeInfo);
            }
            return selectedDataNodes;
        }
        log.error("DataNode数量不足：[datanodeList={}]", dataNodes.values());
        throw new RuntimeException("DataNode数量不足: [applyCount=" + requiredNodeCount +
                ", findCount=" + findCount +
                ", existsFileNodeCount=" + existCount +
                ", filename=" + fileName);
    }

    private boolean containsFiles(String hostname, String fileName) {
        replicaLock.readLock().lock();
        try {
            final Map<String, FileInfo> files = filesByDataNode.getOrDefault(hostname, new HashMap<>(PrettyCodes.trimMapSize()));
            return files.containsKey(fileName);
        } finally {
            replicaLock.readLock().unlock();
        }
    }

    /**
     * dataNode是否存活的监控线程
     *
     * <pre>
     *     这里存在一种情况，假设一个DataNode宕机了，从DataNode集合中摘除
     *
     * </pre>
     */
    private class DataNodeAliveMonitor implements Runnable {
        @Override
        public void run() {
            Iterator<DataNodeInfo> iterator = dataNodes.values().iterator();
            List<DataNodeInfo> toRemoveDataNode = new ArrayList<>();
            while (iterator.hasNext()) {
                DataNodeInfo next = iterator.next();
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis < next.getLatestHeartbeatTime()) {
                    continue;
                }
                log.info("DataNode存活检测超时，被移除：[hostname={}, current={}, nodeLatestHeartbeatTime={}]",
                        next, DateUtil.format(new Date(currentTimeMillis), "yyyy-MM-dd HH:mm:ss"), DateUtil.format(new Date(next.getLatestHeartbeatTime()), "yyyy-MM-dd HH:mm:ss"));
                iterator.remove();
                toRemoveDataNode.add(next);
            }
            for (DataNodeInfo info : toRemoveDataNode) {
                createLostReplicaTask(info);
            }
        }
    }
}

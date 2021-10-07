package like.cn.dfs.datanode;

import like.cn.dfs.common.utils.DefaultScheduler;
import like.cn.dfs.datanode.config.DataNodeConfig;
import like.cn.dfs.datanode.server.DataNodeHandler;
import like.cn.dfs.datanode.server.DefaultFileTransportCallback;
import like.cn.dfs.datanode.server.StorageManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/10/6 16:54
 */
@Slf4j
public class DataNode {

    private final DataNodeConfig config;
    private final DefaultScheduler defaultScheduler;
    private final StorageManager storageManager;

    public DataNode(DataNodeConfig config) {
        this.config = config;
        this.defaultScheduler = new DefaultScheduler("DataNode-Scheduler-");
        this.storageManager = new StorageManager(config);
        final DefaultFileTransportCallback defaultFileTransportCallback = new DefaultFileTransportCallback(storageManager);
        DataNodeHandler dataNodeHandler = new DataNodeHandler(config, defaultFileTransportCallback, defaultScheduler);
    }

    public static void main(String[] args) {
        DataNodeConfig dataNodeConfig = DataNodeConfig.defaultConfig();
        final DataNode dataNode = new DataNode(dataNodeConfig);

    }
}

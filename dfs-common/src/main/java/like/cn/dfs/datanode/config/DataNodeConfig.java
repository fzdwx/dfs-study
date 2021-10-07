package like.cn.dfs.datanode.config;

import lombok.Builder;
import lombok.Data;

/**
 * dataNode 配置
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/10/6 16:56
 */
@Data
@Builder
public class DataNodeConfig {

    private String baseDir;
    private String nameNodeServers;
    private String dataNodeTransportServer;
    private String dataNodeHttpServer;

    private int heartbeatInterval;
    private int dataNodeId;
    private String fileLocatorType;
    private int dataNodeWorkerThreads;

    public static DataNodeConfig defaultConfig() {
        return DataNodeConfig.builder()
                .baseDir("/data/datanode")
                .dataNodeId(1)
                .dataNodeTransportServer("localhost:5671")
                .dataNodeHttpServer("localhost:8001")
                .heartbeatInterval(30000)
                .nameNodeServers("localhost:2341")
                .dataNodeWorkerThreads(200)
                .fileLocatorType("sha1")
                .build();
    }

}

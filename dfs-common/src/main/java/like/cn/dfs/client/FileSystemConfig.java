package like.cn.dfs.client;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文件客户端配置
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/19 11:19
 */
@Data
@Accessors(fluent = true)
public class FileSystemConfig {

    /** 要连接的name node 的ip地址 */
    private String ip;
    /** 要链接的name node 的端口号 */
    private int port;
    /** 连接重试次数 */
    private int connectRetryTimes;
}

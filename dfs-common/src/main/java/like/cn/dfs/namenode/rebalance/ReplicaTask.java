package like.cn.dfs.namenode.rebalance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 副本复制任务
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/10/5 14:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplicaTask {

    private String filename;
    private String hostname;
    private int port;
}

package like.cn.dfs.namenode.rebalance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 移除副本复制任务
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/10/5 14:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveReplicaTask {

    private String hostname;
    private String fileName;
}

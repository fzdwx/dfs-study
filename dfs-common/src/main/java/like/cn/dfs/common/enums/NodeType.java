package like.cn.dfs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件节点类型
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/25 12:43
 */
@Getter
@AllArgsConstructor
public enum NodeType {
    /**
     * 文件节点类型
     */
    FILE(1), DIRECTORY(2);

    private int value;

}

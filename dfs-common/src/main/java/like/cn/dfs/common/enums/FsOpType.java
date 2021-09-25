package like.cn.dfs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/25 11:41
 */
@Getter
@AllArgsConstructor
public enum FsOpType {

    /**
     * 创建文件夹
     */
    MKDIR(1),
    /**
     * 创建文件
     */
    CREATE(2),

    /**
     * 删除文件或者文件夹
     */
    DELETE(3),
    ;


    private int value;
}

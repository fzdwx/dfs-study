package like.cn.dfs.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件信息
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/10/5 14:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {

    private String hostname;

    private String fileName;

    private long fileSize;

}

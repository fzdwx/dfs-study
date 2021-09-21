package like.cn.dfs.namenode.filesystem;

import java.util.Map;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/21 11:56
 */
public interface FsNameSystem {

    void mkdir(String realFileName, Map<String, String> attrMap);
}

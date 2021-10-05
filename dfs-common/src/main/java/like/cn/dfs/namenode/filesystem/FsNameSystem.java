package like.cn.dfs.namenode.filesystem;

import java.util.Map;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/21 11:56
 */
public interface FsNameSystem {

    /**
     * 创建文件夹
     * @param filename 文件路径
     * @param attr     文件属性
     */
    void mkdir(String filename, Map<String, String> attr);

    /**
     * 创建文件
     * @param filename 文件名称
     * @param attr     文件属性
     * @return 是否创建成功
     */
    boolean createFile(String filename, Map<String, String> attr);

}

package like.cn.dfs.datanode.server.locate;

/**
 * 文件定位器
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @module
 * @date 2021-10-07 16:39:32
 */
public interface FileLocator {

    /**
     * 根据文件名寻找本机的绝对路径
     * @param filename 文件名
     * @return 本机的绝对路径
     */
    String locate(String filename);
}

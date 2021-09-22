package like.cn.dfs.client;

import java.util.Map;

/**
 * 文件系统的主要功能的接口定义
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/19 11:18
 */
public interface FileSystem {

    void send(String message) throws InterruptedException;

    /**
     * 创建目录
     *
     * @param path 路径
     */
    void mkdir(String path) throws Exception;

    /**
     * 创建目录
     *
     * @param path 路径
     * @param attr 文件属性
     */
    void mkdir(String path, Map<String, String> attr) throws Exception;

    /**
     * 关闭
     */
    void close();
}

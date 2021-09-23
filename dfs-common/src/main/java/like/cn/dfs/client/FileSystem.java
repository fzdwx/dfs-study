package like.cn.dfs.client;

import java.util.Map;

/**
 * 文件系统客户端的主要功能的接口定义
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @module
 * @date 2021-09-22 16:43:50
 */
public interface FileSystem {

    /**
     * 发送一个字符串到客户端
     * @param message 消息
     * @exception InterruptedException 中断异常
     */
    void send(String message) throws InterruptedException;

    /**
     * 创建目录
     * @param path 路径
     */
    void mkdir(String path) throws Exception;

    /**
     * 创建目录
     * @param path 路径
     * @param attr 文件属性
     */
    void mkdir(String path, Map<String, String> attr) throws Exception;

    /** 关闭 */
    void close();
}

package like.cn.dfs.client;

import like.cn.dfs.common.net.file.OnProgressListener;

import java.io.File;
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

    /** 关闭文件客户端 */
    void close();

    /**
     * 上传文件
     * @param src 源文件路径
     * @param des 保存到dfs中的路径
     */
    void upload(File src, String des) throws Exception;

    /**
     * 上传文件
     * @param src          源文件路径
     * @param des          保存到dfs中的路径
     * @param numOfReplica 文件副本数量
     */
    void upload(File src, String des, int numOfReplica) throws Exception;

    /**
     * 上传文件
     * @param src          源文件路径
     * @param des          保存到dfs中的路径
     * @param numOfReplica 文件副本数量
     * @param attr         文件属性
     */
    void upload(File src, String des, int numOfReplica, Map<String, String> attr) throws Exception;

    /**
     * 上传文件
     * @param src          源文件路径
     * @param des          保存到dfs中的路径
     * @param numOfReplica 文件副本数量
     * @param attr         文件属性
     * @param listener     上传进度监听器
     */
    void upload(File src, String des, int numOfReplica, Map<String, String> attr, OnProgressListener listener) throws Exception;
}

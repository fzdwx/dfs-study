package like.cn.dfs.client;

/**
 * 文件系统的主要功能的接口定义
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/19 11:18
 */
public interface FileSystem {

    void send(String message) throws InterruptedException;
}

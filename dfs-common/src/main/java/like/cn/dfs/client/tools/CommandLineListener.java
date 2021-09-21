package like.cn.dfs.client.tools;

/**
 * 命令行监听器
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/20 13:04
 */
public interface CommandLineListener {

    /**
     * 连接失败监听器
     */
    void onConnectFailed();

    /**
     * 认证结果
     *
     * @param result 结果
     */
    void onAuthResult(boolean result);
}

package like.cn.dfs.common.net.listener;

/**
 * 网络客户端连接失败监听器
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/20 12:19
 */
@FunctionalInterface
public interface NetClientFailListener {
    /**
     * 连接失败
     */
    void onConnectFail();
}

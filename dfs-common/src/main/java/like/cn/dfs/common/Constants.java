package like.cn.dfs.common;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 公共常量
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/18 15:21
 */
public class Constants {

    /**
     * 可传输的最大字节数
     */
    public static final int MAX_DATA_SIZE = 10 * 1024 * 1024;

    /**
     * NameNode的在线状态
     */
    public static final int NAME_NODE_STATUS_UP = 1;

    /**
     * NameNode的宕机
     */
    public static final int NAME_NODE_STATUS_DOWN = 0;

    /**
     * 分块传输，每一块的大小
     */
    public static final int CHUNKED_SIZE = ( int ) (MAX_DATA_SIZE * 0.5F);

    /**
     * 应用请求包计数器
     */
    public static AtomicLong REQUEST_COUNTER = new AtomicLong(1);

    /** 文件分隔符 */
    public static String FileSeparator = "/";
}

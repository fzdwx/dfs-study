package like.cn.dfs.common;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 公共常量
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
     * 文件最大副本数量
     */
    public static final int MAX_REPLICA_NUM = 5;

    //==================================================================================================== 文件属性 start
    /** 文件属性之删除时间 */
    public static final String ATTR_FILE_DEL_TIME = "DEL_TIME";

    /** 文件属性之副本数量 */
    public static final String ATTR_REPLICA_NUM = "REPLICA_NUM";

    /** 文件属性之文件大小 */
    public static final String ATTR_FILE_SIZE = "FILE_SIZE";

    /** 所有的文件属性 */
    public static final Set<String> KEYS_ATTR_SET = Set.of(ATTR_FILE_DEL_TIME, ATTR_REPLICA_NUM, ATTR_FILE_SIZE);

    /**
     * 应用请求包计数器
     */
    public static AtomicLong REQUEST_COUNTER = new AtomicLong(1);
    /** 文件分隔符 */
    public static String FileSeparator = "/";
}

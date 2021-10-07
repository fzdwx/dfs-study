package like.cn.dfs.datanode;

/**
 * dataNode 和nameNode通信的组件
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/10/7 17:02
 */
public class NameNodeClient {

    /**
     * 发送文件信息给NameNode
     * @param filename 文件名
     * @param fileSize 大小
     */
    public void informReplicaReceived(String filename, long fileSize) {
        // TODO: 2021/10/7  发送文件信息给NameNode
    }
}

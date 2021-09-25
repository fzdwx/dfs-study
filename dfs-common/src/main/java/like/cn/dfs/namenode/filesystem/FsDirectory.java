package like.cn.dfs.namenode.filesystem;

import cn.hutool.core.util.ObjectUtil;
import like.cn.dfs.common.Constants;
import like.cn.dfs.common.enums.NodeType;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 负责管理内存中文件目录树的核心组件
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/25 12:42
 */
@Slf4j
public class FsDirectory {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final Node root;

    public FsDirectory() {
        this.root = new Node(Constants.FileSeparator, NodeType.DIRECTORY.getValue());
    }

    /**
     * 创建文件目录
     */
    public void mkdir(String dir, Map<String, String> attr) {
        try {
            lock.writeLock().lock();
            final String[] paths = dir.split(Constants.FileSeparator);
            Node curr = this.root;
            for (String path : paths) {
                if ("".equals(path)) continue;

                curr = findDir(curr, path);
            }
            curr.putAllAttr(attr);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private Node findDir(Node curr, String path) {
        Node childrenNode = curr.getChildren(path);
        if (ObjectUtil.isNull(childrenNode)) {
            childrenNode = new Node(path, NodeType.DIRECTORY.getValue());
            curr.addChildren(childrenNode);

            log.info("[findDir] 新建目录:{}", childrenNode.getPath());
        } else {
            log.info("[findDir] 目录已经存在:{}", childrenNode.getPath());
        }
        curr = childrenNode;
        return curr;
    }
}

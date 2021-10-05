package like.cn.dfs.namenode.filesystem;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
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

                curr = findOrCreateDir(curr, path);
            }
            curr.putAllAttr(attr);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 查看某个目录文件
     * @param parent 目录文件
     * @return {@link Node}
     */
    public Node listFiles(String parent) {
        return listFiles(parent, Integer.MAX_VALUE);
    }

    /**
     * 创建文件
     * @param filename 文件名
     * @param attr     attr
     * @return boolean
     */
    public boolean createFile(String filename, Map<String, String> attr) {
        try {
            lock.writeLock().lock();
            final String[] paths = StrUtil.split(filename, Constants.FileSeparator).toArray(new String[0]);
            String fileNode = paths[paths.length - 1];
            Node fileParentNode = getFileParent(paths);
            Node childrenNode = fileParentNode.getChildren(fileNode);
            if (childrenNode != null) {
                log.warn("文件已存在，创建失败 : {}", filename);
                return false;
            }
            Node child = new Node(fileNode, NodeType.FILE.getValue());
            child.putAllAttr(attr);
            fileParentNode.addChildren(child);
            return true;
        } finally {
            lock.writeLock().unlock();
        }
    }

    private Node listFiles(String parent, int level) {
        return Node.deepCopy(unsafeListFiles(parent), level);
    }

    private Node unsafeListFiles(String parent) {
        if (root.getPath().equals(parent)) {
            return root;
        }
        lock.readLock().lock();
        try {
            final String[] paths = StrUtil.split(parent, Constants.FileSeparator).toArray(new String[0]);
            final String name = paths[paths.length - 1];
            final Node curr = getFileParent(paths);
            return curr.getChildren(name);
        } finally {
            lock.readLock().unlock();
        }
    }

    private Node getFileParent(String[] paths) {
        Node curr = this.root;
        for (final String path : paths) {
            if (StrUtil.isBlank(path)) continue;
            curr = findOrCreateDir(curr, path);
        }
        return curr;
    }

    private Node findOrCreateDir(Node curr, String path) {
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

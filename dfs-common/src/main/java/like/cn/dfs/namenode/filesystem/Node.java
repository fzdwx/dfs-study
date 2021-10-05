package like.cn.dfs.namenode.filesystem;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 代表文件目录，包含一堆子文件或者子文件目录
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/25 12:43
 */
@Getter
@Setter
@Slf4j
public class Node {

    /** 存放当前文件的子文件夹 */
    private final TreeMap<String, Node> children;
    /** 当前文件夹的name */
    private String path;
    private int type;
    private Map<String, String> attr;
    private Node parent;

    public Node() {
        this.children = new TreeMap<>();
        this.attr = new HashMap<>();
        this.parent = null;
    }

    public Node(String path, int type) {
        this();
        this.path = path;
        this.type = type;
    }

    public Node(String path, int type, Map<String, String> attr) {
        this();
        this.path = path;
        this.type = type;
        this.attr.putAll(attr);
    }

    /**
     * 深拷贝节点
     * @param node  节点
     * @param level 拷贝多少个子节点的层级
     * @return {@link Node} 深拷贝后的节点
     */
    public static Node deepCopy(Node node, int level) {
        if (ObjectUtil.isNull(node))
            return null;
        final Node res = new Node(node.getPath(), node.getType(), node.getAttr());
        if (level > 0) {
            final TreeMap<String, Node> children = node.children;
            if (!CollUtil.isEmpty(children)) {
                for (String path : children.keySet()) {
                    res.addChildren(deepCopy(children.get(path), level - 1));
                }
            }
        }
        return res;
    }

    /**
     * 根据子节点的path获取当前节点中对应的子节点
     * @param child 孩子
     * @return {@link Node}
     */
    public Node getChildren(String child) {
        synchronized (children) {
            return children.get(child);
        }
    }

    public void addChildren(Node childrenNode) {
        synchronized (children) {
            childrenNode.setParent(this);
            children.put(childrenNode.getPath(), childrenNode);
        }
    }

    public void putAllAttr(Map<String, String> attr) {
        this.attr.putAll(attr);
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    @Override
    public String toString() {
        return "Node{" +
                "children=" + children +
                ", path='" + path + '\'' +
                ", type=" + type +
                ", attr=" + attr +
                '}';
    }
}

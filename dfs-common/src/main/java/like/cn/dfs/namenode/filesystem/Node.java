package like.cn.dfs.namenode.filesystem;

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

    private final TreeMap<String, Node> children;
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

package like.cn.dfs.namenode.filesystem;

import java.util.Map;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/21 11:56
 */
public abstract class AbstractFsNameSystem implements FsNameSystem {

    /**
     * 负责管理内存文件目录树的组件
     */
    protected FsDirectory directory;

    public AbstractFsNameSystem() {
        this.directory = new FsDirectory();
    }

    @Override
    public void mkdir(String path, Map<String, String> attr) {
        this.directory.mkdir(path, attr);
    }
}

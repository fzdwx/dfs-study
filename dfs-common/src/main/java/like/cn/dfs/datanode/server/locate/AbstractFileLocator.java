package like.cn.dfs.datanode.server.locate;

import like.cn.dfs.common.utils.FileUtil;
import like.cn.dfs.common.utils.NetUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽象文件路径定位器
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/10/7 16:40
 */
@Slf4j
public abstract class AbstractFileLocator implements FileLocator {

    private final int hashSize;
    private final String basePath;

    public AbstractFileLocator(String basePath, int hashSize) {
        this.basePath = basePath;
        this.hashSize = hashSize;
        this.encodeFileName(NetUtils.getHostName());
    }


    @Override
    public String locate(String filename) {
        String afterTransferPath = encodeFileName(filename);
        int hash = FileUtil.hash(afterTransferPath, hashSize * hashSize);
        int parent = hash / hashSize;
        int child = hash % hashSize;

        String parentPath = FileUtil.format(parent);
        String childPath = FileUtil.format(child);
        return basePath + "/" + parentPath + "/" + childPath + "/" + afterTransferPath;
    }

    /**
     * 对文件名转码
     * @param filename 文件名
     * @return 返回文件名
     */
    protected abstract String encodeFileName(String filename);
}

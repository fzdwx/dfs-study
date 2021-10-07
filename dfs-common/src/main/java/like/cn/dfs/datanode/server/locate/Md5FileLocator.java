package like.cn.dfs.datanode.server.locate;


import cn.hutool.crypto.digest.DigestUtil;

/**
 * 基于MD5 HASH 算法定位文件
 */
public class Md5FileLocator extends AbstractFileLocator {

    public Md5FileLocator(String basePath, int hashSize) {
        super(basePath, hashSize);
    }

    @Override
    protected String encodeFileName(String filename) {
        return DigestUtil.md5Hex(filename);
    }
}

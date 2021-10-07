package like.cn.dfs.datanode.server.locate;

/**
 * 文件定位工厂
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/10/7 16:38
 */
public class FileLocatorFactory {
    /**
     * 根据配置的类型获取文件定位器
     * @param type     类型
     * @param basePath 基础目录
     * @return 文件定位器
     */
    public static FileLocator getFileLocator(String type, String basePath, int hashSize) {
        if ("simple".equals(type)) {
            return new SimpleFileLocator(basePath, hashSize);
        } else if ("md5".equals(type)) {
            return new Md5FileLocator(basePath, hashSize);
        } else if ("sha1".equals(type)) {
            return new Sha1FileLocator(basePath, hashSize);
        } else if ("aes".equals(type)) {
            return new AesFileLocator(basePath, hashSize);
        }
        return new SimpleFileLocator(basePath, hashSize);
    }
}

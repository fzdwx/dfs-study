package like.cn.dfs.datanode.server.locate;


import cn.hutool.crypto.SecureUtil;
import like.cn.dfs.common.utils.NetUtils;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 基于AES加密算法的 文件路径定位器
 */
@Slf4j
public class AesFileLocator extends AbstractFileLocator {

    private String key;

    public AesFileLocator(String basePath, int hashSize) {
        super(basePath, hashSize);
    }

    private String getKey() {
        if (key == null) {
            key = NetUtils.getHostName();
        }
        return key;
    }

    @Override
    protected String encodeFileName(String filename) {
        try {
            return Base64.getUrlEncoder()
                    .encodeToString(SecureUtil.aes(getKey().getBytes(StandardCharsets.UTF_8)).encrypt(filename));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package like.cn.dfs.datanode.server;

import like.cn.dfs.common.utils.FileUtil;
import like.cn.dfs.datanode.config.DataNodeConfig;
import like.cn.dfs.datanode.server.locate.FileLocator;
import like.cn.dfs.datanode.server.locate.FileLocatorFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 数据存储的管理器
 * <p>
 * 比如客户端上传的文件
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/10/7 16:37
 */
@Slf4j
public class StorageManager {

    private static final String STORAGE_INFO = "storage.info";
    private static final String STORAGE_TEMP = "storage.temp";
    private static final int HASH_SIZE = 256;
    private final String storageDir;
    private final FileLocator fileLocator;

    public StorageManager(DataNodeConfig config) {
        this.storageDir = config.getBaseDir() + "/storage";
        this.fileLocator = FileLocatorFactory.getFileLocator(config.getFileLocatorType(), storageDir, HASH_SIZE);
        this.initStorage(storageDir);
    }

    /**
     * 根据文件名获取本地绝对路径
     */
    public String getAbsolutePathByFileName(String filename) {
        return fileLocator.locate(filename);
    }

    /**
     * 记录收到一个文件
     * <pre>
     *     记录它的大小，名字，路径
     * </pre>
     */
    public void recordReplicaReceive(String filename, String absolutePath, long fileSize) throws IOException {
        recordReplicaReceive(filename, absolutePath, fileSize, STORAGE_INFO);
    }

    /**
     * 记录收到一个文件
     */
    private void recordReplicaReceive(String filename, String absolutePath, long fileSize, String storageInfoType) throws IOException {
        synchronized (this) {
            final File file = new File(absolutePath);
            final String parent = file.getParent();
            final File recordFile = new File(parent, storageInfoType);
            try (final FileOutputStream fos = new FileOutputStream(recordFile, true)) {
                final FileChannel fileChannel = fos.getChannel();
                final byte[] bytes = filename.getBytes(StandardCharsets.UTF_8);
                ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length + 12);
                byteBuffer.putInt(bytes.length);
                byteBuffer.putLong(fileSize);
                byteBuffer.put(bytes);
                byteBuffer.flip();
                fileChannel.write(byteBuffer);
                fileChannel.force(true);
            }
        }
    }

    /**
     * 初始化存储目录
     * @param storageDir 基础目录
     */
    private void initStorage(String storageDir) {
        File file = new File(storageDir);
        if (file.exists()) {
            return;
        }
        // 创建 65536个文件夹
        log.info("开始初始化存储文件目录: [baseDir={}]", storageDir);
        for (int i = 0; i < HASH_SIZE; i++) {
            for (int j = 0; j < HASH_SIZE; j++) {
                String parent = FileUtil.format(i);
                String child = FileUtil.format(j);
                File tar = new File(file, parent + "/" + child);
                if (!tar.mkdirs()) {
                    throw new IllegalStateException("初始化存储目录失败: " + tar.getAbsolutePath());
                }
            }

        }
        log.info("初始化存储文件目录成功...");
    }
}

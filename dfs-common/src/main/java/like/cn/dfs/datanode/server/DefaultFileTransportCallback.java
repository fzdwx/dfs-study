package like.cn.dfs.datanode.server;

import cn.hutool.core.util.ObjectUtil;
import like.cn.dfs.common.net.file.FileAttribute;
import like.cn.dfs.common.net.file.FileTransportCallback;
import like.cn.dfs.datanode.NameNodeClient;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * dataNode文件传输回调
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/10/7 16:54
 */
@Slf4j
public class DefaultFileTransportCallback implements FileTransportCallback {

    private NameNodeClient nameNodeClient;
    @Setter
    private StorageManager storageManager;

    public DefaultFileTransportCallback(StorageManager storageManager) {
        this.storageManager = storageManager;
    }

    @Override
    public String getPath(String filename) {
        String localFileName = storageManager.getAbsolutePathByFileName(filename);
        log.info("获取文件路径文件：[filename={}, location={}]", filename, localFileName);
        return localFileName;
    }

    @Override
    public void onProgress(String filename, long total, long current, float progress, int currentWriteBytes) {
        // TODO: 2021/10/7 性能监控
        // Prometheus.hit("datanode_disk_write_bytes", "DataNode瞬时写磁盘大小", currentWriteBytes);
    }

    @Override
    public void onCompleted(FileAttribute fileAttribute) throws InterruptedException, IOException {
        storageManager.recordReplicaReceive(fileAttribute.getFilename(), fileAttribute.getAbsolutePath(), fileAttribute.getSize());
        if (!ObjectUtil.isNull(nameNodeClient)) {
            nameNodeClient.informReplicaReceived(fileAttribute.getFilename(), fileAttribute.getSize());
        }
    }
}

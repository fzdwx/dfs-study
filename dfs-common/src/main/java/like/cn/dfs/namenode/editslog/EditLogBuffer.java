package like.cn.dfs.namenode.editslog;


import like.cn.dfs.common.utils.FileUtil;
import like.cn.dfs.namenode.config.NameNodeConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/25 11:43
 */
@Slf4j
public class EditLogBuffer {

    private final NameNodeConfig nameNodeConfig;
    private final ByteArrayOutputStream buffer;
    private volatile long startTxId = -1L;
    private volatile long endTxId = 0L;

    public EditLogBuffer(NameNodeConfig nameNodeConfig) {
        this.nameNodeConfig = nameNodeConfig;
        this.buffer = new ByteArrayOutputStream(nameNodeConfig.getEditLogFlushThreshold() * 2);
    }

    /**
     * 写入一条数据到缓冲区
     */
    public void write(EditLogWrapper editLogWrapper) throws IOException {
        if (startTxId == -1) {
            startTxId = editLogWrapper.getTxId();
        }
        endTxId = editLogWrapper.getTxId();
        buffer.write(editLogWrapper.toByteArray());
    }

    /**
     * 刷盘
     */
    public EditslogInfo flush() throws IOException {
        if (buffer.size() <= 0) {return null;}
        byte[] data = buffer.toByteArray();
        System.out.println(Arrays.toString(data));
        System.out.println(data);
        ByteBuffer dataBuffer = ByteBuffer.wrap(data);
        String filePath = nameNodeConfig.getEditLogFile(startTxId, endTxId);
        log.info("[flush] 保存editslog文件：[file={}]", filePath);
        FileUtil.saveFile(filePath, false, dataBuffer);
        return new EditslogInfo(startTxId, endTxId, filePath);
    }

    /**
     * 清除缓冲区
     */
    public void clear() {
        buffer.reset();
        startTxId = -1;
        endTxId = -1;
    }

    public int size() {
        return buffer.size();
    }
}

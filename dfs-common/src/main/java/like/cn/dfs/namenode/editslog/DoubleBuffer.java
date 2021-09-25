package like.cn.dfs.namenode.editslog;

import cn.hutool.core.util.ObjectUtil;
import like.cn.dfs.namenode.config.NameNodeConfig;

import java.io.IOException;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/25 11:43
 */
public class DoubleBuffer {

    private final NameNodeConfig nameNodeConfig;
    private EditLogBuffer currentBuffer;
    private EditLogBuffer syncBuffer;

    public DoubleBuffer(NameNodeConfig nameNodeConfig) {
        this.nameNodeConfig = nameNodeConfig;
        this.currentBuffer = new EditLogBuffer(nameNodeConfig);
        this.syncBuffer = new EditLogBuffer(nameNodeConfig);
    }

    /**
     * 是否可以刷新磁盘
     */
    public boolean shouldForceSync() {
        return currentBuffer.size() >= nameNodeConfig.getEditLogFlushThreshold();
    }

    /**
     * 写入edit log
     */
    public void write(EditLogWrapper editLogWrapper) throws IOException {
        currentBuffer.write(editLogWrapper);
    }

    /** 交换两块缓冲区 */
    public void setReadToSync() {
        EditLogBuffer temp = currentBuffer;
        currentBuffer = syncBuffer;
        syncBuffer = temp;
    }

    /**
     * 把缓冲区的edit log数据刷新到磁盘
     */
    public EditslogInfo flush() throws IOException {
        final EditslogInfo info = syncBuffer.flush();
        if (!ObjectUtil.isNull(info)) {
            syncBuffer.clear();
        }
        return info;
    }
}

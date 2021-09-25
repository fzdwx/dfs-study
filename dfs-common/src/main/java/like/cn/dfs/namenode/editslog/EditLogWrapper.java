package like.cn.dfs.namenode.editslog;

import cn.hutool.core.util.ByteUtil;
import like.cn.dfs.model.backup.EditLog;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/25 11:40
 */
@Slf4j
public class EditLogWrapper {
    private EditLog editLog;


    public EditLogWrapper(int opType, String path, Map<String, String> attr) {
        this.editLog = EditLog.newBuilder()
                .setOpType(opType)
                .setPath(path)
                .putAllAttr(attr)
                .build();
    }

    public EditLogWrapper(EditLog editLog) {
        this.editLog = editLog;
    }

    public static List<EditLogWrapper> parseFrom(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return parseFrom(byteBuffer);
    }

    public static List<EditLogWrapper> parseFrom(ByteBuffer byteBuffer) {
        List<EditLogWrapper> ret = new LinkedList<>();
        while (byteBuffer.hasRemaining()) {
            try {
                int bodyLength = byteBuffer.getInt();
                byte[] body = new byte[bodyLength];
                byteBuffer.get(body);
                EditLog editLog = EditLog.parseFrom(body);
                ret.add(new EditLogWrapper(editLog));
            } catch (Exception e) {
                log.error("Parse EditLog failed.", e);
            }
        }
        return ret;
    }

    public EditLog getEditLog() {
        return editLog;
    }

    public long getTxId() {
        return this.editLog.getTxId();
    }

    public void setTxId(long txId) {
        this.editLog = this.editLog.toBuilder()
                .setTxId(txId)
                .build();
    }

    public byte[] toByteArray() {
        byte[] body = editLog.toByteArray();
        int bodyLength = body.length;
        byte[] bytes = ByteUtil.intToBytes(bodyLength);
        byte[] ret = new byte[body.length + bytes.length];
        System.arraycopy(body, 0, ret, bytes.length, bodyLength);
        System.arraycopy(bytes, 0, ret, 0, bytes.length);
        return ret;
    }
}

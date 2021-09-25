package like.cn.dfs.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/25 12:19
 */
public class FileUtil {

    public static void saveFile(String filePath, boolean delOldFile, ByteBuffer buffer) throws IOException {
        cn.hutool.core.io.FileUtil.mkParentDirs(filePath);
        File file = cn.hutool.core.io.FileUtil.file(filePath);
        if (delOldFile) cn.hutool.core.io.FileUtil.del(file);

        try (final RandomAccessFile raf = new RandomAccessFile(file, "rw");
             final FileOutputStream fos = new FileOutputStream(raf.getFD());
             final FileChannel channel = fos.getChannel()) {
            channel.write(buffer);
            channel.force(true);
        }
    }
}

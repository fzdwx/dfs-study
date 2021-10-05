package like.cn.dfs.common.utils;

import cn.hutool.crypto.digest.DigestUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/25 12:19
 */
public class FileUtil {

    /**
     * The number of bytes in a kilobyte.
     */
    public static final long ONE_KB = 1024;
    /**
     * The number of bytes in a kilobyte.
     * @since 2.4
     */
    public static final BigDecimal ONE_KB_BI = BigDecimal.valueOf(ONE_KB);

    /**
     * The number of bytes in a megabyte.
     * @since 2.4
     */
    public static final BigDecimal ONE_MB_BI = ONE_KB_BI.multiply(ONE_KB_BI);

    /**
     * The number of bytes in a gigabyte.
     * @since 2.4
     */
    public static final BigDecimal ONE_GB_BI = ONE_KB_BI.multiply(ONE_MB_BI);

    /**
     * The number of bytes in a terabyte.
     * @since 2.4
     */
    public static final BigDecimal ONE_TB_BI = ONE_KB_BI.multiply(ONE_GB_BI);


    /**
     * The number of bytes in a petabyte.
     * @since 2.4
     */
    public static final BigDecimal ONE_PB_BI = ONE_KB_BI.multiply(ONE_TB_BI);

    /**
     * The number of bytes in an exabyte.
     * @since 2.4
     */
    public static final BigDecimal ONE_EB_BI = ONE_KB_BI.multiply(ONE_PB_BI);


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

    public static String formatSize(long length) {
        BigDecimal size = new BigDecimal(String.valueOf(length));
        String displaySize;

        if (size.divide(ONE_EB_BI, 2, RoundingMode.HALF_UP).compareTo(BigDecimal.ONE) > 0) {
            displaySize = size.divide(ONE_EB_BI, 2, RoundingMode.HALF_UP) + " EB";
        } else if (size.divide(ONE_PB_BI, 2, RoundingMode.HALF_UP).compareTo(BigDecimal.ONE) > 0) {
            displaySize = size.divide(ONE_PB_BI, 2, RoundingMode.HALF_UP) + " PB";
        } else if (size.divide(ONE_TB_BI, 2, RoundingMode.HALF_UP).compareTo(BigDecimal.ONE) > 0) {
            displaySize = size.divide(ONE_TB_BI, 2, RoundingMode.HALF_UP) + " TB";
        } else if (size.divide(ONE_GB_BI, 2, RoundingMode.HALF_UP).compareTo(BigDecimal.ONE) > 0) {
            displaySize = size.divide(ONE_GB_BI, 2, RoundingMode.HALF_UP) + " GB";
        } else if (size.divide(ONE_MB_BI, 2, RoundingMode.HALF_UP).compareTo(BigDecimal.ONE) > 0) {
            displaySize = size.divide(ONE_MB_BI, 2, RoundingMode.HALF_UP) + " MB";
        } else if (size.divide(ONE_KB_BI, 2, RoundingMode.HALF_UP).compareTo(BigDecimal.ONE) > 0) {
            displaySize = size.divide(ONE_KB_BI, 2, RoundingMode.HALF_UP) + " KB";
        } else {
            displaySize = size + " bytes";
        }
        return displaySize;
    }

    public static String fileMd5(String fileName) throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            return DigestUtil.md5Hex(fis);
        }
    }
}

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

    public static String format(int i) {
        if (i >= 100) {
            return String.valueOf(i);
        }
        if (i >= 10) {
            return "0" + i;
        }
        if (i >= 0) {
            return "00" + i;
        }
        return "";
    }

    public static int hash(String source, int maxSize) {
        int hash = toPositive(murmur2(source.getBytes()));
        return hash % maxSize;
    }

    public static int toPositive(int number) {
        return number & 0x7fffffff;
    }

    /**
     * Generates 32 bit murmur2 hash from byte array
     * @param data byte array to hash
     * @return 32 bit hash of the given array
     */
    public static int murmur2(final byte[] data) {
        int length = data.length;
        int seed = 0x9747b28c;
        // 'm' and 'r' are mixing constants generated offline.
        // They're not really 'magic', they just happen to work well.
        final int m = 0x5bd1e995;
        final int r = 24;

        // Initialize the hash to a random value
        int h = seed ^ length;
        int length4 = length / 4;

        for (int i = 0; i < length4; i++) {
            final int i4 = i * 4;
            int k = (data[i4 + 0] & 0xff) + ((data[i4 + 1] & 0xff) << 8) + ((data[i4 + 2] & 0xff) << 16) + ((data[i4 + 3] & 0xff) << 24);
            k *= m;
            k ^= k >>> r;
            k *= m;
            h *= m;
            h ^= k;
        }

        // Handle the last few bytes of the input array
        switch (length % 4) {
            case 3:
                h ^= (data[(length & ~3) + 2] & 0xff) << 16;
            case 2:
                h ^= (data[(length & ~3) + 1] & 0xff) << 8;
            case 1:
                h ^= data[length & ~3] & 0xff;
                h *= m;
        }

        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;
        return h;
    }
}

package like.cn.dfs.namenode.editslog;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import like.cn.dfs.common.Constants;
import like.cn.dfs.namenode.config.NameNodeConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/25 11:29
 */
@Slf4j
public class FsEditLog {

    private static final Pattern indexPattern = Pattern.compile("(\\d+)_(\\d+)");

    private final NameNodeConfig nameNodeConfig;
    /**
     * 双缓冲
     */
    private final DoubleBuffer editLogBuffer;
    /**
     * 每个线程保存的tx id
     */
    private final ThreadLocal<Long> localTxId = new ThreadLocal<>();
    /**
     * 每条editLog的id，自增
     */
    private volatile long txIdSeq = 0;
    /**
     * 当前刷新磁盘最大的txId
     */
    private volatile long syncTxId = 0;

    /**
     * 当前是否在刷磁盘
     */
    private volatile boolean isSyncRunning = false;

    /**
     * 是否正在调度一次刷盘的操作
     */
    private volatile Boolean isSchedulingSync = false;

    /**
     * 磁盘中的editLog文件, 升序
     */
    private List<EditslogInfo> editLogInfos = null;


    public FsEditLog(NameNodeConfig nameNodeConfig) {
        this.nameNodeConfig = nameNodeConfig;
        this.editLogBuffer = new DoubleBuffer(nameNodeConfig);
        this.loadEditLogInfos();
    }

    /**
     * 写入一条edit log
     */
    public void logEdit(EditLogWrapper editLogWrapper) {
        synchronized (this) {
            // 刚进来就直接检查一下是否有人正在调度一次刷盘的操作
            waitSchedulingSync();

            txIdSeq++;
            long txId = this.txIdSeq;
            localTxId.set(txId);

            try {
                // 构造一条edit log写入缓冲区
                editLogWrapper.setTxId(txId);
                editLogBuffer.write(editLogWrapper);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!editLogBuffer.shouldForceSync()) return;
            // 如果代码进行到这里，就说明需要刷磁盘
            isSchedulingSync = true;
        }
        // 异步刷磁盘
        logSync();
    }

    /*** 异步刷磁盘 */
    private void logSync() {
        synchronized (this) {
            Long txId = localTxId.get();
            localTxId.remove();
            /*
             * 在这种情况下需要等待：
             * 1. 有其他线程正在刷磁盘，但是其他线程刷的磁盘的最大txid比当前需要刷磁盘的线程id少。
             * 这通常表示：正在刷磁盘的线程不会把当前线程需要刷的数据刷到磁盘中
             */
            while (txId > syncTxId && isSyncRunning) {
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /*
             * 多个线程在上面等待，当前一个线程刷磁盘操作完成后，唤醒了一堆线程，此时只有一个线程获取到锁。
             * 这个线程会进行刷磁盘操作，当这个线程释放锁之后，其他被唤醒的线程会依次获取到锁。
             * 此时每个被唤醒的线程需要重新判断一次，自己要做的事情是不是被其他线程干完了
             */
            if (txId <= syncTxId) return;

            // 交换两块缓冲区
            editLogBuffer.setReadToSync();

            // 记录最大的txid
            syncTxId = txId;

            // 设置当前正在同步到磁盘的标志位
            isSchedulingSync = false;

            // 唤醒哪些正在wait的线程
            notifyAll();

            // 正在刷磁盘
            isSyncRunning = true;
        }

        try {
            final EditslogInfo editslogInfo = editLogBuffer.flush();
            if (!ObjectUtil.isNull(editslogInfo)) {
                editLogInfos.add(editslogInfo);
            }
        } catch (IOException e) {
            log.info("FSEditlog刷磁盘失败：", e);
        }

        synchronized (this) {
            // 同步完了磁盘之后，就会将标志位复位，再释放锁
            isSyncRunning = false;
            notifyAll();
        }
    }

    /**
     * 等待正在调度的刷磁盘的操作
     */
    private void waitSchedulingSync() {
        try {
            while (isSchedulingSync) {
                wait(1000);
                // 此时就释放锁，等待一秒再次尝试获取锁，去判断
                // isSchedulingSync是否为false，就可以脱离出while循环
            }
        } catch (Exception e) {
            log.info("waitSchedulingSync has interrupted !!");
        }
    }

    /** 从磁盘中加载所有的edit log文件信息 */
    private void loadEditLogInfos() {
        this.editLogInfos = new CopyOnWriteArrayList<>();
        File dir = FileUtil.file(nameNodeConfig.getBaseDir());
        if (!dir.isDirectory()) return;

        File[] files = dir.listFiles();
        if (ArrayUtil.isEmpty(files)) return;

        for (File file : files) {
            if (!file.getName().contains("edits")) continue;
            long[] index = getIndexFromFileName(file.getName());
            this.editLogInfos.add(new EditslogInfo(index[0], index[1], nameNodeConfig.getBaseDir() + Constants.FileSeparator + file.getName()));
        }
        this.editLogInfos.sort(null);
    }

    /**
     * 从文件名获取索引
     * @param name 文件名  1_1000.log
     * @return 数组，例如：[1,1000]
     */
    private long[] getIndexFromFileName(String name) {
        Matcher matcher = indexPattern.matcher(name);
        long[] res = new long[2];
        if (matcher.find()) {
            res[0] = Long.parseLong(matcher.group(1));
            res[1] = Long.parseLong(matcher.group(2));
        }
        return res;
    }
}

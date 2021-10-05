package like.cn.dfs.client.tools;

import like.cn.dfs.common.net.file.OnProgressListener;

/**
 * 多文件传输进度监听器
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/25 19:10
 */
public class OnMultiFileProgressListener implements OnProgressListener {

    private final OnProgressListener listener;
    private final int fileCount;
    private int currentFile = 0;

    public OnMultiFileProgressListener(OnProgressListener listener, int fileCount) {
        this.listener = listener;
        this.fileCount = fileCount;
    }

    @Override
    public void onProgress(long total, long current, float progress, int currentReadBytes) {
        int base = 100 / fileCount;
        float readProgress = (base * progress / 100.0F) + currentFile * base;
        if (listener != null) {
            listener.onProgress(total * fileCount, currentFile * total + current,
                    readProgress, currentReadBytes);
        }
    }

    @Override
    public void onCompleted() {
        currentFile++;
        if (currentFile == fileCount) {
            if (listener != null) {
                listener.onCompleted();
            }
        }
    }
}

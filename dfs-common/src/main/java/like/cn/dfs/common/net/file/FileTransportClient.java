package like.cn.dfs.common.net.file;

import like.cn.dfs.common.codec.NettyPacket;
import like.cn.dfs.common.enums.NettyPacketType;
import like.cn.dfs.common.net.NetClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 支持文件上传和下载的客户端
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/10/5 16:16
 */
public class FileTransportClient {

    private final NetClient netClient;
    private final Map<String, String> filePathMap = new ConcurrentHashMap<>();
    private final Map<String, OnProgressListener> listeners = new ConcurrentHashMap<>();

    public FileTransportClient(NetClient netClient) {
        this(netClient, true);
    }

    public FileTransportClient(NetClient netClient, boolean getFile) {
        this.netClient = netClient;
        if (getFile) {
            FileTransportCallback callback = new FileTransportCallback() {
                @Override
                public String getPath(String filename) {
                    return filePathMap.remove(filename);
                }

                @Override
                public void onProgress(String filename, long total, long current, float progress, int currentWriteBytes) {
                    OnProgressListener listener = listeners.get(filename);
                    if (listener != null) {
                        listener.onProgress(total, current, progress, currentWriteBytes);
                    }
                }

                @Override
                public void onCompleted(FileAttribute fileAttribute) {
                    OnProgressListener listener = listeners.remove(fileAttribute.getFilename());
                    if (listener != null) {
                        listener.onCompleted();
                    }
                }
            };
            FileReceiveHandler fileReceiveHandler = new FileReceiveHandler(callback);
            this.netClient.addNettyPackageListener(requestWrapper -> {
                NettyPacket request = requestWrapper.getRequest();
                if (request.getPacketType() == NettyPacketType.TRANSFER_FILE.getValue()) {
                    FilePacket filePacket = FilePacket.parseFrom(requestWrapper.getRequest().getBody());
                    fileReceiveHandler.handleRequest(filePacket);
                }
            });
        }
    }

    /**
     * 发送文件
     */
    public void sendFile(String filename, String absolutePath, OnProgressListener listener, boolean force) throws Exception {
        File file = new File(absolutePath);
        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在：" + absolutePath);
        }
        DefaultFileSendTask fileSender = new DefaultFileSendTask(file, filename, netClient.socketChannel(), listener);
        fileSender.execute(force);
    }

    /**
     * 关闭
     */
    public void shutdown() {
        listeners.clear();
        filePathMap.clear();
        netClient.shutdown();
    }
}

package like.cn.dfs.common.net.file;

import cn.hutool.core.util.RandomUtil;
import io.netty.channel.socket.SocketChannel;
import like.cn.dfs.common.codec.NettyPacket;
import like.cn.dfs.common.enums.NettyPacketType;
import like.cn.dfs.common.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 默认的写文件方式
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/10/5 16:27
 */
@Slf4j
public class DefaultFileSendTask {


    private final OnProgressListener listener;
    private final SocketChannel socketChannel;
    private final String filename;
    private final File file;
    private final FileAttribute fileAttribute;

    public DefaultFileSendTask(File file, String filename, SocketChannel socketChannel, OnProgressListener listener) throws IOException {
        this.file = file;
        this.filename = filename;
        this.socketChannel = socketChannel;
        this.fileAttribute = new FileAttribute();
        this.fileAttribute.setFileName(filename);
        this.fileAttribute.setSize(file.length());
        this.fileAttribute.setId(RandomUtil.randomString(12));
        this.fileAttribute.setMd5(FileUtil.fileMd5(file.getAbsolutePath()));
        this.listener = listener;
    }

    /**
     * 执行上传文件
     */
    public void execute(boolean force) {
        try {
            if (!file.exists()) {
                log.error("文件不存在：[filename={}, localFile={}]", filename, file.getAbsolutePath());
                return;
            }
            RandomAccessFile raf = new RandomAccessFile(file.getAbsoluteFile(), "r");
            FileInputStream fis = new FileInputStream(raf.getFD());
            FileChannel fileChannel = fis.getChannel();
            if (log.isDebugEnabled()) {
                log.debug("发送文件头：{}", filename);
            }
            FilePacket headPackage = FilePacket.builder()
                    .type(FilePacket.HEAD)
                    .fileMetaData(fileAttribute.getAttr())
                    .build();
            NettyPacket nettyPacket = NettyPacket.buildPacket(headPackage.toBytes(), NettyPacketType.TRANSFER_FILE);
            sendPackage(nettyPacket, force);
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            int len;
            int readLength = 0;
            while ((len = fileChannel.read(buffer)) > 0) {
                buffer.flip();
                byte[] data = new byte[len];
                buffer.get(data);
                byte[] content = FilePacket.builder()
                        .type(FilePacket.BODY)
                        .fileMetaData(fileAttribute.getAttr())
                        .body(data)
                        .build().toBytes();
                nettyPacket = NettyPacket.buildPacket(content, NettyPacketType.TRANSFER_FILE);
                sendPackage(nettyPacket, force);
                buffer.clear();
                readLength += len;
                float progress = new BigDecimal(String.valueOf(readLength)).multiply(new BigDecimal(100))
                        .divide(new BigDecimal(String.valueOf(fileAttribute.getSize())), 2, RoundingMode.HALF_UP)
                        .floatValue();
                if (log.isDebugEnabled()) {
                    log.debug("发送文件包，filename = {}, size={}, progress={}", filename, data.length, progress);
                }
                if (listener != null) {
                    listener.onProgress(fileAttribute.getSize(), readLength, progress, len);
                }
            }
            FilePacket tailPackage = FilePacket.builder()
                    .type(FilePacket.TAIL)
                    .fileMetaData(fileAttribute.getAttr())
                    .build();
            nettyPacket = NettyPacket.buildPacket(tailPackage.toBytes(), NettyPacketType.TRANSFER_FILE);
            sendPackage(nettyPacket, force);
            if (log.isDebugEnabled()) {
                log.debug("发送文件完毕，filename = {}", filename);
            }
            if (listener != null) {
                listener.onCompleted();
            }
        } catch (Exception e) {
            log.error("文件发送失败：", e);
        }
    }

    private void sendPackage(NettyPacket nettyPacket, boolean force) throws InterruptedException {
        if (force) {
            socketChannel.writeAndFlush(nettyPacket).sync();
        } else {
            socketChannel.writeAndFlush(nettyPacket);
        }
    }
}

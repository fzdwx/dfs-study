package like.cn.dfs.datanode.server;

import io.netty.channel.ChannelHandlerContext;
import like.cn.dfs.common.codec.NettyPacket;
import like.cn.dfs.common.net.AbstractChannelHandler;
import like.cn.dfs.common.net.file.FileReceiveHandler;
import like.cn.dfs.common.utils.DefaultScheduler;
import like.cn.dfs.common.utils.PrettyCodes;
import like.cn.dfs.datanode.config.DataNodeConfig;

import java.util.Set;

/**
 * dataNode消息处理器
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/10/7 17:08
 */
public class DataNodeHandler extends AbstractChannelHandler {

    private final DataNodeConfig config;
    private final DefaultScheduler scheduler;
    private final DefaultFileTransportCallback fileTransportCallback;
    private final FileReceiveHandler fileReceiveHandler;

    public DataNodeHandler(DataNodeConfig config, DefaultFileTransportCallback defaultFileTransportCallback, DefaultScheduler defaultScheduler) {
        this.config = config;
        this.scheduler = defaultScheduler;
        this.fileTransportCallback = defaultFileTransportCallback;
        // 负责接收文件: 1、客户端上传文件； 2、当前DataNode从其他DataNode中同步文件副本
        this.fileReceiveHandler = new FileReceiveHandler(fileTransportCallback);
    }

    @Override
    protected boolean handler(ChannelHandlerContext ctx, NettyPacket nettyPacket) throws Exception {
        return false;
    }

    @Override
    protected Set<Integer> interestPackageTypes() {
        return PrettyCodes.interestAll();
    }
}

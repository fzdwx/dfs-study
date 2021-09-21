package like.cn.dfs.common.ha;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ByteUtil;
import io.netty.channel.ChannelHandlerContext;
import like.cn.dfs.common.Constants;
import like.cn.dfs.common.codec.NettyPacket;
import like.cn.dfs.common.enums.NettyPacketType;
import like.cn.dfs.common.net.AbstractChannelHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 返回当前name node的状态
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/20 10:43
 */
@Slf4j
public class ReportNameNodeStatusHandler extends AbstractChannelHandler {

    private final AtomicBoolean nameNodeDown = new AtomicBoolean(false);

    /**
     * 标识NameNode节点宕机了
     */
    public void markNameNodeDown() {
        nameNodeDown.compareAndSet(false, true);
    }

    /**
     * 当channel写入当前name node节点的状态
     */
    @Override
    protected boolean handler(ChannelHandlerContext ctx, NettyPacket nettyPacket) throws Exception {
        // [status,0,0,0]
        boolean down = nameNodeDown.get();
        byte[] status = ByteUtil.intToBytes(down ? Constants.NAME_NODE_STATUS_DOWN : Constants.NAME_NODE_STATUS_UP);
        NettyPacket req = NettyPacket.buildPacket(status, NettyPacketType.GET_NAME_NODE_STATUS);
        ctx.writeAndFlush(req);
        if (log.isDebugEnabled()) {
            log.debug("BackupNode获取NameNode的状态：[nameNodeDown={}]", down);
        }
        return true;
    }

    @Override
    protected Set<Integer> interestPackageTypes() {
        return CollUtil.set(false, NettyPacketType.GET_NAME_NODE_STATUS.getValue());
    }
}

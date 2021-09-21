package like.cn.dfs.namenode.server;

import io.netty.channel.ChannelHandlerContext;
import like.cn.dfs.common.codec.NettyPacket;
import like.cn.dfs.common.net.AbstractChannelHandler;
import like.cn.dfs.common.utils.DefaultScheduler;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/18 16:59
 */
@Slf4j
public class NameNodeHandler extends AbstractChannelHandler {

    public DefaultScheduler defaultScheduler;

    public NameNodeHandler(DefaultScheduler defaultScheduler) {
        this.defaultScheduler = defaultScheduler;
    }

    @Override
    protected boolean handler(ChannelHandlerContext ctx, NettyPacket nettyPacket) throws Exception {
        byte[] body = nettyPacket.getBody();
        System.out.println(Arrays.toString(body));
        return true;
    }

    @Override
    protected Set<Integer> interestPackageTypes() {
        return Collections.emptySet();
    }

    @Override
    protected Executor getExecutor() {
        return null;
    }
}

package like.cn.dfs.common.net;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import like.cn.dfs.common.codec.NettyPacket;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * 自定义的消息处理器
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/18 15:59
 */
@Slf4j
@Sharable
public abstract class AbstractChannelHandler extends ChannelInboundHandlerAdapter {

    private Set<Integer> interestPackageTypes;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 选择是否用执行器执行当前的消息处理过程
        Executor executor = getExecutor();
        if (executor != null) {
            executor.execute(() -> {
                channelReadInternal(ctx, msg);
            });
        } else {
            channelReadInternal(ctx, msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("AbstractChannelHandler#exceptionCaught：", cause);
    }

    /**
     * 处理消息
     */
    private void channelReadInternal(ChannelHandlerContext ctx, Object msg) {
        try {
            String loggerId = DigestUtil.md5Hex("" + System.nanoTime() + RandomUtil.randomInt());
            MDC.put("logger_id", loggerId);
            NettyPacket nettyPacket = ( NettyPacket ) msg;
            boolean consumerMsg = false;
            // 判断当前handler是否对对应的消息有处理能力
            if (getPackageTypes().isEmpty() || getPackageTypes().contains(nettyPacket.getPacketType())) {
                try {
                    // 真正处理消息的位置
                    consumerMsg = handler(ctx, nettyPacket);
                } catch (Exception e) {
                    log.info("handler 发生异常:", e);
                }
            }
            if (!consumerMsg) {
                ctx.fireChannelRead(msg);
            }
        } finally {
            MDC.remove("logger_id");
        }
    }

    private Set<Integer> getPackageTypes() {
        if (interestPackageTypes == null) {
            interestPackageTypes = interestPackageTypes();
        }
        return Collections.unmodifiableSet(interestPackageTypes);
    }

    /**
     * 处理消息
     *
     * @return 是否消费了当前消息
     */
    protected abstract boolean handler(ChannelHandlerContext ctx, NettyPacket nettyPacket) throws Exception;

    /**
     * 关注的消息类型，如果为空则表示对所有类型的消息都关注
     * 相当于打一个tag
     */
    protected abstract Set<Integer> interestPackageTypes();

    /**
     * 获取执行器，如果返回不为空，则代表用该执行器执行
     */
    protected Executor getExecutor() {
        return null;
    }
}

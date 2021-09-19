package like.cn.dfs.common.net;

import cn.hutool.core.collection.CollectionUtil;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import like.cn.dfs.common.Constants;
import like.cn.dfs.common.codec.NettyPacketDecoder;
import like.cn.dfs.common.codec.NettyPacketEncoder;
import lombok.NonNull;

import java.util.LinkedList;
import java.util.List;

/**
 * 基础的消息处理器
 * <ol>
 *     <li>定义了解码器{@link NettyPacketDecoder}</li>
 *     <li>定义了编码器{@link NettyPacketEncoder}</li>
 *     <li>消息主体{@link like.cn.dfs.common.codec.NettyPacket}</li>
 * </ol>
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021-09-18 15:19:44
 */
public class BaseChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final List<AbstractChannelHandler> handlers = new LinkedList<>();

    /**
     * 添加自定义的handler
     *
     * @see {@link AbstractChannelHandler}
     */
    public void addHandler(@NonNull AbstractChannelHandler handler) {
        this.handlers.add(handler);
    }

    /**
     * 添加自定义的handler
     *
     * @see {@link AbstractChannelHandler}
     */
    public void addHandlers(List<AbstractChannelHandler> handlers) {
        if (CollectionUtil.isNotEmpty(handlers)) this.handlers.addAll(handlers);
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(
                new NettyPacketDecoder(Constants.MAX_DATA_SIZE),
                new LengthFieldPrepender(3),
                new NettyPacketEncoder()
        );
        for (AbstractChannelHandler handler : handlers) {
            ch.pipeline().addLast(handler);
        }
    }
}

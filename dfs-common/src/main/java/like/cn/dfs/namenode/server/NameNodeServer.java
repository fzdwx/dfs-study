package like.cn.dfs.namenode.server;

import like.cn.dfs.common.net.NetServer;
import like.cn.dfs.common.utils.DefaultScheduler;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

/**
 * NameNode对外提供服务的接口
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/18 14:41
 */
@Slf4j
public class NameNodeServer {

    private final NetServer netServer;
    private final NameNodeHandler nameNodeHandler;

    public NameNodeServer(NameNodeHandler nameNodeHandler, DefaultScheduler defaultScheduler) {
        this.nameNodeHandler = nameNodeHandler;
        this.netServer = new NetServer("nameNode", defaultScheduler);
    }

    /**
     * 启动socket server，监听指定的端口
     *
     * @throws InterruptedException 中断异常
     */
    public void start() throws InterruptedException {
        this.netServer.addHandlers(Collections.singletonList(nameNodeHandler));
        this.netServer.bind(1234);
    }

    /**
     * 关闭服务
     */
    public void shutdown() {
        log.info("Shutdown NameNodeServer.");
        this.netServer.shutdown();
    }
}

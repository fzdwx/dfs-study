package like.cn.dfs.namenode;

import like.cn.dfs.common.utils.DefaultScheduler;
import like.cn.dfs.namenode.filesystem.DiskNameSystem;
import like.cn.dfs.namenode.server.NameNodeHandler;
import like.cn.dfs.namenode.server.NameNodeServer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/18 14:25
 */
public class NameNode {

    private final AtomicBoolean started = new AtomicBoolean(false);

    private final NameNodeServer nameNodeServer;
    private final NameNodeHandler nameNodeHandler;
    private final DefaultScheduler defaultScheduler;
    private final DiskNameSystem diskNameSystem;

    public NameNode() {
        this.defaultScheduler = new DefaultScheduler("NameNode-Scheduler-");
        this.diskNameSystem = new DiskNameSystem(defaultScheduler);
        this.nameNodeHandler = new NameNodeHandler(defaultScheduler, diskNameSystem);
        this.nameNodeServer = new NameNodeServer(nameNodeHandler, defaultScheduler);
    }

    public static void main(String[] args) {
        try {
            NameNode nameNode = new NameNode();
            nameNode.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动nameNode
     *
     * @throws Exception 异常
     */
    public void start() throws Exception {
        if (started.compareAndSet(false, true)) {
            nameNodeServer.start();
        }
    }

    public void shutdown() {
        if (started.compareAndSet(true, false)) {
            defaultScheduler.shutdown();
            nameNodeServer.shutdown();
        }
    }
}

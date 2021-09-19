package like.cn.dfs.common.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 基础线程，添加了一些日志参数
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/18 17:07
 */
@Slf4j
public class NetThread extends Thread {

    public NetThread(final String name, boolean daemon) {
        super(name);
        configureThread(name, daemon);
    }

    public NetThread(String name, Runnable runnable, boolean daemon) {
        super(runnable, name);
        configureThread(name, daemon);
    }

    private void configureThread(String name, boolean daemon) {
        setDaemon(daemon);
        setUncaughtExceptionHandler((t, e) -> log.error("Uncaught exception in thread '{}':", name, e));
    }
}

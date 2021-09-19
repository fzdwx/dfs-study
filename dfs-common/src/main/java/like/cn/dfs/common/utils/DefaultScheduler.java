package like.cn.dfs.common.utils;

import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程调度器
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/18 17:02
 */
@Slf4j
public class DefaultScheduler {

    private final AtomicBoolean shutdown = new AtomicBoolean(true);
    private final AtomicInteger schedulerThreadId = new AtomicInteger(0);
    private ScheduledThreadPoolExecutor executor;

    public DefaultScheduler(String threadNamePrefix) {
        this(threadNamePrefix, Runtime.getRuntime().availableProcessors() * 2, true);
    }

    public DefaultScheduler(String threadNamePrefix, int coreThreads) {
        this(threadNamePrefix, coreThreads, true);
    }

    public DefaultScheduler(String threadNamePrefix, int coreThreads, boolean daemon) {
        if (shutdown.compareAndSet(true, false)) {
            executor = new ScheduledThreadPoolExecutor(coreThreads, r -> {
                return new NetThread(threadNamePrefix + schedulerThreadId.getAndIncrement(), r, daemon);
            });
            // 如果当前执行器关闭了的就不执行任何任务了
            executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
            executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        }
    }

    /**
     * 执行任务
     */
    public void scheduleOnce(String name, Runnable runnable) {
        scheduleOnce(name, runnable, 0);
    }

    /**
     * 执行延时任务
     */
    public void scheduleOnce(String name, Runnable runnable, int delay) {
        schedule(name, runnable, delay, 0, TimeUnit.MILLISECONDS);
    }

    /**
     * 执行任务
     *
     * @param name     任务名称
     * @param runnable task
     * @param delay    延迟时间
     * @param period   调度周期
     * @param timeUnit 时间单位
     */
    public void schedule(String name, Runnable runnable, int delay, int period, TimeUnit timeUnit) {
        if (shutdown.get()) return;

        if (log.isDebugEnabled()) {
            log.debug("Scheduling task {} with initial delay {} " + timeUnit.name() + " and period {} " + timeUnit.name() + ".", name, delay, period);
        }
        Runnable delegate = () -> {
            try {
                if (log.isTraceEnabled()) {
                    log.trace("Beginning execution of scheduled task {}.", name);
                }
                String loggerId = DigestUtil.md5Hex("" + System.nanoTime() + new Random().nextInt());
                MDC.put("logger_id", loggerId);
                runnable.run();
            } catch (Exception e) {
                log.error("Uncaught exception in scheduled task {} :", name, e);
            } finally {
                if (log.isTraceEnabled()) {
                    log.trace("Completed execution of scheduled task {}.", name);
                }
                MDC.remove("logger_id");
            }
        };

        if (period > 0) {
            executor.scheduleWithFixedDelay(delegate, delay, period, timeUnit);
        } else {
            executor.schedule(delegate, delay, timeUnit);
        }
    }

    /**
     * 关闭调度器
     */
    public void shutdown() {
        if (shutdown.compareAndSet(false, true)) {
            log.info("Shutdown DefaultScheduler.");
            executor.shutdown();
        }
    }
}

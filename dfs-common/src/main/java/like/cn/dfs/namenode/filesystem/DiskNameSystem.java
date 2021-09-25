package like.cn.dfs.namenode.filesystem;

import cn.hutool.core.date.StopWatch;
import like.cn.dfs.common.enums.FsOpType;
import like.cn.dfs.common.utils.DefaultScheduler;
import like.cn.dfs.namenode.config.NameNodeConfig;
import like.cn.dfs.namenode.editslog.EditLogWrapper;
import like.cn.dfs.namenode.editslog.FsEditLog;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/21 11:55
 */
@Slf4j
public class DiskNameSystem extends AbstractFsNameSystem {

    private final FsEditLog editLog;
    private final NameNodeConfig nameNodeConfig;

    public DiskNameSystem(NameNodeConfig nameNodeConfig, DefaultScheduler defaultScheduler) {
        this.nameNodeConfig = nameNodeConfig;
        this.editLog = new FsEditLog(nameNodeConfig);
    }

    @Override
    public void mkdir(String realFileName, Map<String, String> attrMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        super.mkdir(realFileName, attrMap);
        this.editLog.logEdit(new EditLogWrapper(FsOpType.MKDIR.getValue(), realFileName, attrMap));
        log.info("[mkdir] create dir :{}", realFileName);
        stopWatch.stop();
        // Prometheus.gauge("namenode_fs_memory_cost", "FSDirectory操作耗时", "op", "mkdir", stopWatch.getLastTaskTimeMillis());
    }

    public NameNodeConfig getNameNodeConfig() {
        return nameNodeConfig;
    }
}

package like.cn.dfs.common.ha;

/**
 * BackupNode升级为nameNode的监听器
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/19 12:30
 */
@FunctionalInterface
public interface BackupUpGradeListener {

    /**
     * BackNode 升级
     */
    void onBackupUpGrade(String ip, int port);
}

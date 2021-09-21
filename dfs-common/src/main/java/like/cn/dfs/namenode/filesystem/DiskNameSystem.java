package like.cn.dfs.namenode.filesystem;

import like.cn.dfs.common.utils.DefaultScheduler;

import java.util.Map;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/21 11:55
 */
public class DiskNameSystem extends AbstractFsNameSystem {

    public DiskNameSystem(DefaultScheduler defaultScheduler) {

    }

    @Override
    public void mkdir(String realFileName, Map<String, String> attrMap) {
        System.out.println("成功创建文件夹:" + realFileName);
    }
}

package like.cn.dfs.common.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 漂亮的代码
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021-09-20 10:53:20
 */
public class PrettyCodes {

    private PrettyCodes() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static int trimMapSize(int count) {
        return ( int ) (( float ) count / 0.75F + 1.0F);
    }

    public static int trimMapSize() {
        return 32;
    }

    public static int trimListSize() {
        return 10;
    }

    public static Map<String, String> trimMap(int size) {
        return new HashMap<>(size);
    }

    public static Map<String, String> trimMap() {
        return trimMap(trimMapSize());
    }

    public static byte[] trimBytes() {
        return new byte[0];
    }

    public static Set<Integer> interestAll() {
        return Collections.emptySet();
    }
}

import cn.hutool.core.util.ByteUtil;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/20 11:03
 */
public class Test2 {

    public static void main(String[] args) {
        byte[] status = ByteUtil.intToBytes(1);
        System.out.println(ByteUtil.bytesToInt(status));
    }
}

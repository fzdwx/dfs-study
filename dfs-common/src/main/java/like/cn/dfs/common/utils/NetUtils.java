package like.cn.dfs.common.utils;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/20 13:19
 */
public class NetUtils {

    private static String HOSTNAME;

    /**
     * 获取channel的id
     */
    public static String getChannelId(Channel channel) {
        SocketChannel socketChannel = ( SocketChannel ) channel;
        return socketChannel.id().asLongText().replaceAll("-", "");
    }

    /**
     * 获取主机名
     *
     * @return 主机名
     */
    public static String getHostName() {
        if (HOSTNAME == null) {
            try {
                HOSTNAME = (InetAddress.getLocalHost()).getHostName();
            } catch (UnknownHostException uhe) {
                // host = "hostname: hostname"
                String host = uhe.getMessage();
                if (host != null) {
                    int colon = host.indexOf(':');
                    if (colon > 0) {
                        return host.substring(0, colon);
                    }
                }
                HOSTNAME = "UnknownHost";
            }
        }
        return HOSTNAME;
    }
}

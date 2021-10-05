package like.cn.dfs.namenode.server;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import io.netty.channel.Channel;
import like.cn.dfs.common.utils.DefaultScheduler;
import like.cn.dfs.common.utils.NetUtils;
import like.cn.dfs.common.utils.PrettyCodes;
import like.cn.dfs.namenode.config.NameNodeConfig;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 认证管理器
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/10/5 15:11
 */
public class UserManager {
    private final NameNodeConfig nameNodeConfig;
    private final DefaultScheduler defaultScheduler;
    private final Map<String, User> userMap;
    private final Map<String, String> channelUserMap = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> userTokenMaps = new ConcurrentHashMap<>();

    public UserManager(NameNodeConfig nameNodeConfig, DefaultScheduler defaultScheduler) {
        this.defaultScheduler = defaultScheduler;
        this.nameNodeConfig = nameNodeConfig;
        this.userMap = loadUserInfoFromDisk(nameNodeConfig);
        this.defaultScheduler.schedule("刷新用户数据到磁盘", this::refreshAuthInfo,
                60 * 60 * 1000, 60 * 60 * 1000, TimeUnit.MILLISECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(this::refreshAuthInfo));
    }

    public User getUser(String username) {
        return userMap.get(username);
    }

    /**
     * 认证，保存每个用户对应的认证信息
     * @param channel          通道
     * @param authenticateInfo 验证信息
     * @return boolean
     */
    public boolean login(Channel channel, String authenticateInfo) {
        synchronized (this) {
            String[] split = authenticateInfo.split(",");
            String username = split[0];
            String secret = split[1];
            if (!userMap.containsKey(username)) {
                return false;
            }
            User user = userMap.get(username);
            if (!user.getSecret().equals(secret)) {
                return false;
            }
            String clientId = NetUtils.getChannelId(channel);
            Set<String> existsTokens = userTokenMaps.computeIfAbsent(username, k -> new HashSet<>());
            existsTokens.add(clientId + "-" + RandomUtil.randomString(10));
            channelUserMap.put(clientId, user.getUsername());
            return true;
        }
    }

    /**
     * 获取用户对应的token
     * @param channel 客户端连接
     * @return {@link String}
     */
    public String getTokenByChannel(Channel channel) {
        synchronized (this) {
            String clientId = NetUtils.getChannelId(channel);
            String username = channelUserMap.get(clientId);
            if (username == null) {
                return null;
            }
            Set<String> tokens = userTokenMaps.get(username);
            for (String token : tokens) {
                String existsClientId = token.split("-")[0];
                if (existsClientId.equals(clientId)) {
                    return token;
                }
            }
            return null;
        }
    }

    /**
     * 收到广播的认证请求保存token
     */
    public void setToken(String username, String userToken) {
        Set<String> existsTokens = userTokenMaps.computeIfAbsent(username, k -> new HashSet<>());
        existsTokens.add(userToken);
    }

    private void refreshAuthInfo() {
        try {
            String data = JSONUtil.toJsonStr(userMap.values());
            like.cn.dfs.common.utils.FileUtil.saveFile(nameNodeConfig.getAuthInfoFile(), true, ByteBuffer.wrap(data.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("刷新认证信息出错：", e);
        }
    }

    private Map<String, User> loadUserInfoFromDisk(NameNodeConfig nameNodeConfig) {
        final File file = FileUtil.file(nameNodeConfig.getAuthInfoFile());
        if (file.exists()) {
            final String s = FileUtil.readString(file, StandardCharsets.UTF_8);
            return JSONUtil.parseArray(s)
                    .toList(User.class)
                    .stream()
                    .collect(Collectors.toMap(User::getUsername, user -> user));
        }
        return new HashMap<>(PrettyCodes.trimMapSize());
    }
}

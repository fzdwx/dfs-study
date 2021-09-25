package like.cn.dfs.client;

import lombok.SneakyThrows;

/**
 * 文件客户端
 * <pre>
 * {@code 123}
 *  </pre>
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/9/19 11:17
 */
public class FsClient {

    public static FileSystem getFileSystem(FileSystemConfig config) throws InterruptedException {
        DefaultFileSystem fileSystem = new DefaultFileSystem(config);
        fileSystem.start();
        return fileSystem;
    }

    @SneakyThrows
    public static void main(String[] args) {
        FileSystem client = getFileSystem(new FileSystemConfig().ip("127.0.0.1").port(1234).connectRetryTimes(-1));

        client.send("hello");
        client.mkdir("/hello/world");
    }
}

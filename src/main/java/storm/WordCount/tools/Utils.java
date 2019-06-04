package storm.WordCount.tools;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

// makeRedisConnect 数据库连接
public class Utils {
    static StatefulRedisConnection<String, String> connection;
    static public RedisAsyncCommands<String, String> getRedis() {
        if (connection == null) {
            RedisClient client = RedisClient.create(
                    RedisURI.builder().withHost("localhost").withPort(7000).withDatabase(5)
                            .build()
            );
            connection = client.connect();
            return connection.async();
        }
        return connection.async(); // 使用异步命令
    }
}



package storm.StormRedis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

import java.util.concurrent.TimeUnit;

public class Utils {

    static private RedisClient client = RedisClient.create(
            RedisURI.builder().withHost("localhost").withPort(7000).withDatabase(5).build()
    );
    static private StatefulRedisConnection<String, String> connection = client.connect();

    static public RedisAsyncCommands<String, String> makeRedisConnect() {
        return connection.async();
    }
}

class MessageQueue {
    static private final String name = "MQ";
    static public void reset() throws Exception {
        Utils.makeRedisConnect().del(name).get(1, TimeUnit.MINUTES);
    }

    static public String pop() throws Exception {
        return Utils.makeRedisConnect().rpop(name).get(1, TimeUnit.MINUTES);
    }

    static public void push(String value) throws Exception {
        Utils.makeRedisConnect().lpush(name, value).get(1, TimeUnit.MINUTES);
    }

    static public boolean isEmpty() throws Exception {
        return Utils.makeRedisConnect().llen(name).get(1, TimeUnit.MINUTES) == 0;
    }
}


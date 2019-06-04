package storm.WordCount.tools;

import java.util.concurrent.TimeUnit;

// 消息队列
public class MessageQueue {
    static private String name = "MessageQueue"; // 与Python那边一致
    static public String pop() throws Exception {
        return Utils.getRedis().rpop(name).get(1, TimeUnit.MINUTES);
    }
    static public void push(String value) throws Exception {
        Utils.getRedis().lpush(name, value).get(1, TimeUnit.MINUTES);
    }
    static public boolean isEmpty() throws Exception {
        return Utils.getRedis().llen(name).get(1, TimeUnit.MINUTES) == 0;
    }
}


package storm.WordCount;

import storm.WordCount.bolt.*;
import storm.WordCount.spout.RedisSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

class Topology {
    static public TopologyBuilder builder() {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new RedisSpout());
        builder.setBolt("token-bolt", new TokenBolt(), 3) // 3个分词节点
               .shuffleGrouping("spout");
        builder.setBolt("word-count-bolt", new WordCountBolt(), 6) // 6个词频统计节点
               .fieldsGrouping("token-bolt", new Fields("word")); // 使用fieldGrouping
        //修改 添加WriteRedisBolt来将结果写入到redis中
        builder.setBolt("write-makeRedisConnect-bolt", new WriteRedisBolt())
               .globalGrouping("word-count-bolt");
        return builder;
    }
    static public TopologyBuilder builder2() {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new RedisSpout());
        builder.setBolt("token-bolt2", new TokenBolt2(), 3)
               .shuffleGrouping("spout");
        builder.setBolt("word2-count-bolt", new WordCountBolt2(), 6)
               .fieldsGrouping("token-bolt2", new Fields("word2"));
        //修改 添加WriteRedisBolt来将联合词频统计结果写入到redis中
        builder.setBolt("write-makeRedisConnect-bolt2", new WriteRedisBolt2())
               .globalGrouping("word2-count-bolt");
        return builder;
    }
}

public class Main {

    static public void main(String[] args) {
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("topology2", new Config(), Topology.builder2().createTopology());
    }

    static public void lab1() {
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("topology",new Config(), Topology.builder().createTopology());
    }
    static public void lab2() {
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("topology2",new Config(), Topology.builder2().createTopology());
    }

}

package storm.StormRedis;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

import java.util.Random;

public class Main {
    static public StormTopology createTopology() {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new Spout());
        builder.setBolt("bolt", new Bolt())
               .shuffleGrouping("spout");
        builder.setBolt("bolt1", new Bolt1())
               .shuffleGrouping("bolt");
        return builder.createTopology();
    }

    static public void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        new Thread(() -> {
            try {
                MessageQueue.reset();
                while (true) {
                    MessageQueue.push(new Random().nextInt() + "");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
//        LocalCluster cluster = new LocalCluster();
//        cluster.submitTopology("topology-forth", new Config(), createTopology());
        StormSubmitter.submitTopology("topology-forth", new Config(), createTopology());
    }
}

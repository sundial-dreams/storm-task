package storm.StormCluster;

import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class Topology {
    static public StormTopology topology() {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("numberSpoutFirst", new NumberSpoutFirst());
        builder.setSpout("numberSpoutSecond", new NumberSpoutSecond());
        builder.setBolt("primeBolt", new PrimeBolt())
               .shuffleGrouping("numberSpoutFirst")
               .shuffleGrouping("numberSpoutSecond");
        builder.setBolt("commonPrimeBolt", new CommonPrimeBolt())
               .shuffleGrouping("numberSpoutSecond");
        builder.setBolt("countPrimeBolt0", new CountPrimeBolt())
               .shuffleGrouping("primeBolt");
        builder.setBolt("countPrimeBolt1", new CountPrimeBolt())
               .fieldsGrouping("commonPrimeBolt", new Fields("primeNumber"));
        builder.setBolt("countPrimeBolt2", new CountPrimeBolt())
               .fieldsGrouping("commonPrimeBolt", new Fields("primeNumber"));
        builder.setBolt("reportBolt", new ReportBolt())
               .shuffleGrouping("countPrimeBolt2")
               .shuffleGrouping("countPrimeBolt1")
               .shuffleGrouping("countPrimeBolt0");
        return builder.createTopology();
    }

}

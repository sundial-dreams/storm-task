package storm.StormCluster;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;

public class Main {
    static public void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        StormSubmitter.submitTopology("topology-third", new Config(), Topology.topology());
        // LocalCluster cluster = new LocalCluster();
        // cluster.submitTopology("topology", new Config(), Topology.topology());
    }
}

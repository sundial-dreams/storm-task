package storm.StormCluster;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.Random;

public class NumberSpoutFirst extends BaseRichSpout {
    private TopologyContext context;
    private SpoutOutputCollector collector;
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        context = topologyContext;
        collector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        collector.emit(new Values(new Random().nextInt(100000)));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(
                new Fields("number")
        );
    }
}

class NumberSpoutSecond extends BaseRichSpout {
    private TopologyContext context;
    private SpoutOutputCollector collector;
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        context = topologyContext;
        collector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        collector.emit(new Values(new Random().nextInt(100000) + 100000));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(
                new Fields("number")
        );
    }
}
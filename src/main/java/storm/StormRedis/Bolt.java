package storm.StormRedis;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class Bolt extends BaseRichBolt {
    private TopologyContext context;
    private OutputCollector collector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        collector = outputCollector;
        context = topologyContext;
    }

    @Override
    public void execute(Tuple tuple) {
        String value = tuple.getStringByField("value");
//        System.out.println("value: " + value);
        int v = Math.abs(Integer.valueOf(value));
        try {
            if (v > Integer.MAX_VALUE * 0.9) throw new IllegalAccessException(); // 模拟出错
            collector.emit(tuple, new Values(v));
            collector.ack(tuple);
        } catch (Exception e) {
            collector.fail(tuple);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(
                new Fields("bolt-value")
        );
    }

}

class Bolt1 implements IRichBolt {
    private TopologyContext context;
    private OutputCollector collector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        context = topologyContext;
        collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        long value = tuple.getIntegerByField("bolt-value");
        try {
            System.out.println("value: " + value);
            collector.ack(tuple);
        } catch (Exception e) {
            collector.fail(tuple);
        }
    }

    @Override
    public void cleanup() {
        System.out.println("cleanup");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

}

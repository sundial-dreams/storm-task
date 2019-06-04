package storm.StormRedis;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Spout implements IRichSpout {
    private TopologyContext context;
    private SpoutOutputCollector collector;
    private Map<Long, String> waitAck = new HashMap<>();

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        context = topologyContext;
        collector = spoutOutputCollector;
    }

    @Override
    public void close() {
        System.out.println("close");
    }

    @Override
    public void activate() {
        System.out.println("activate");
    }

    @Override
    public void deactivate() {
        System.out.println("deactivate");
    }

    @Override
    public void nextTuple() {
        try {
            if (MessageQueue.isEmpty()) return;
            long id = System.nanoTime();
            String value = MessageQueue.pop();
            waitAck.put(id, value);
            collector.emit(new Values(value), id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void ack(Object o) {
        long id = (long) o;
        System.out.println("ack id: " + id);
        waitAck.remove(id);
    }

    @Override
    public void fail(Object o) {
        long id = (long) o;
        System.err.println("fail id: " + id);
        try {
            MessageQueue.push(waitAck.get(id)); // 扔回队列
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(
                new Fields("value")
        );
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
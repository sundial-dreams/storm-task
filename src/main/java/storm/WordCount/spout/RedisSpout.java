package storm.WordCount.spout;

import storm.WordCount.tools.*;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.*;

import java.util.Map;

public class RedisSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        collector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        try {
            // 队列为空，不执行nextTuple
            if (MessageQueue.isEmpty()) return;
            collector.emit(new Values(MessageQueue.pop()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(
                new Fields("title")
        );
    }
}

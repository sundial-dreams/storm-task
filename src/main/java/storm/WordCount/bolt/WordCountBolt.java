package storm.WordCount.bolt;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

public class WordCountBolt extends BaseBasicBolt {
    private Map<String, Integer> wordCount = new HashMap<>();

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String word = tuple.getStringByField("word");
        if (wordCount.containsKey(word)) wordCount.compute(word, (k, v) -> v + 1);
        else wordCount.put(word, 1);
        // 修改1 将词频统计结果发送给下一个Bolt
        System.err.println("word count bolt:" + word + ": " + wordCount.get(word));
        basicOutputCollector.emit(new Values(word, wordCount.get(word)));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        // 修改2 定义 Fields
        outputFieldsDeclarer.declare(
                new Fields("word", "frequency")
        );
    }
}


package storm.WordCount.bolt;
import org.apache.storm.topology.*;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.*;
import java.util.*;

public class WordCountBolt2 extends BaseBasicBolt {
    private Map<String, Integer> wordCount = new HashMap<>();
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String word2 = tuple.getStringByField("word2");
        if (wordCount.containsKey(word2)) wordCount.compute(word2, (k, v) -> v + 1);
        else wordCount.put(word2, 1);
        // 修改一
        basicOutputCollector.emit(new Values(word2, wordCount.get(word2)));

    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        // 修改二
        outputFieldsDeclarer.declare(
                new Fields("word2", "frequency")
        );
    }
}
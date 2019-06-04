package storm.WordCount.bolt;

import storm.WordCount.tools.NLPTokens;
import org.apache.storm.topology.*;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.*;

import java.util.List;

public class TokenBolt2 extends BaseBasicBolt {
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String title = tuple.getStringByField("title");
        if (title.length() == 0) return;
        List<String> words = NLPTokens.token(title);
        for (int i = 0, size = words.size(); i < size - 1; i++)
            for (int j = i + 1; j < size; j++)
                basicOutputCollector.emit(new Values(String.format("(%s, %s)", words.get(i), words.get(j))));

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(
                new Fields("word2")
        );
    }
}
package storm.WordCount.bolt;

import storm.WordCount.tools.UnionWord;
import storm.WordCount.tools.UnionWordDao;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

public class WriteRedisBolt2 extends BaseBasicBolt {
    private UnionWordDao unionWordDao = new UnionWordDao();

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            String unionWord = tuple.getStringByField("word2");
            int frequency = tuple.getIntegerByField("frequency");
            String[] words = unionWord.split(",");
            String w1 = words[0].substring(1), w2 = words[1].substring(0, words[1].length() - 1);
            System.err.println(unionWord + ":" + frequency);
            unionWordDao.save(new UnionWord(w1, w2, frequency));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) { }
}

package storm.WordCount.bolt;


import storm.WordCount.tools.Word;
import storm.WordCount.tools.WordDao;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

public class WriteRedisBolt extends BaseBasicBolt {
    private WordDao wordDao = new WordDao();

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            String word = tuple.getStringByField("word");
            int frequency = tuple.getIntegerByField("frequency");
            System.err.println(word + ":" + frequency);
            wordDao.save(new Word(word, frequency));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) { }
}

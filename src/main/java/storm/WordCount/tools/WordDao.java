package storm.WordCount.tools;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

interface IWordDao {
    void save(Word word) throws Exception;
}

public class WordDao implements IWordDao, Serializable {
    private String name = "word:";
    @Override
    public void save(Word word) throws Exception {
        Map<String, String> object = new HashMap<>();
        object.put("frequency", word.frequency + "");
        object.put("date", word.date.getTime() + "");
        Utils.getRedis().hmset(name + word.word, object).get(1, TimeUnit.MINUTES);
    }
}

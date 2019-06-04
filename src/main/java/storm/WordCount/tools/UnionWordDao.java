package storm.WordCount.tools;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

interface IUnionWordDao {
    void save(UnionWord word) throws Exception;
}

public class UnionWordDao implements IUnionWordDao, Serializable {
    private String name = "unionWord:";

    @Override
    public void save(UnionWord unionWord) throws Exception {
        Map<String, String> object = new HashMap<>();
        object.put("frequency", unionWord.frequency + "");
        object.put("date", unionWord.date.getTime() + "");
        Utils.getRedis().hmset(String.format("%s(%s, %s)", name, unionWord.word1, unionWord.word2), object)
             .get(1, TimeUnit.MINUTES);
    }
}
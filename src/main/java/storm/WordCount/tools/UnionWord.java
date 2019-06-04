package storm.WordCount.tools;


import java.time.Instant;
import java.util.Date;

public class UnionWord {
    public String word1, word2;
    public int frequency;
    public Date date;

    public UnionWord(String w1, String w2, int f) {
        this(w1, w2, f, Date.from(Instant.now()));
    }

    public UnionWord(String w1, String w2, int f, Date d) {
        word1 = w1;
        word2 = w2;
        frequency = f;
        date = d;
    }
}


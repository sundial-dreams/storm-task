package storm.WordCount.tools;

import java.time.Instant;
import java.util.Date;

public class Word {
    public String word;
    public int frequency;
    public Date date;
    public Word(String w, int f) {
        this(w, f, Date.from(Instant.now()));
    }
    public Word(String w, int f, Date d) {
        word = w;
        frequency = f;
        date = d;
    }
}

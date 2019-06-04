package storm.WordCount.tools;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.LinkedList;
import java.util.List;

public class NLPTokens {
    private static StanfordCoreNLP pipeline = null;

    public static List<String> token(String Sentence) {
        Annotation annotation = makePipeline().process(Sentence);
        List<String> result = new LinkedList<>();
        for (CoreMap coreMap : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            for (CoreLabel tokens : coreMap.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = tokens.get(CoreAnnotations.TextAnnotation.class);
                String pos = tokens.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                if (!word.matches("[！，。？、）（*&……%￥~·,]")) result.add(word);
            }
        }
        return result;
    }

    private static synchronized StanfordCoreNLP makePipeline() {
        return pipeline == null ? (pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties")) : pipeline;
    }
}

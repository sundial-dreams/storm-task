package storm.StormCluster;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

public class PrimeBolt extends BaseBasicBolt {
    private boolean isPrime(int value, int i) {
        return i > Math.sqrt(value) || (value % i != 0 && isPrime(value, i + 1));
    }
    private boolean isPrime(int value) {
        return isPrime(value, 2);
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        int value = tuple.getIntegerByField("number");
        if (isPrime(value)) basicOutputCollector.emit(new Values(value));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(
                new Fields("primeNumber")
        );
    }
}

class CommonPrimeBolt extends BaseBasicBolt {
    private boolean isPrime(int value) {
        for (int i = 2, v = (int) Math.sqrt(value); i <= v; i++) {
            if (value % 2 == 0) return false;
        }
        return true;
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        int value = tuple.getIntegerByField("number");
        if (isPrime(value)) basicOutputCollector.emit(new Values(value));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(
                new Fields("primeNumber")
        );
    }
}

class CountPrimeBolt extends BaseBasicBolt {
    private Map<Integer, Integer> primeCount = new HashMap<>();

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        int prime = tuple.getIntegerByField("primeNumber");
        if (primeCount.containsKey(prime)) primeCount.compute(prime, (k, v) -> v + 1);
        else primeCount.put(prime, 1);
        basicOutputCollector.emit(new Values(prime, primeCount.get(prime)));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(
                new Fields("prime", "count")
        );
    }
}

class ReportBolt extends BaseBasicBolt {
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        int prime = tuple.getIntegerByField("prime"), count = tuple.getIntegerByField("count");
        System.err.println(prime+ ": " + count);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) { }

}
package assignment.simulator.generation.randomization;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class RNGMechanism {
    private Random random;
    public RNGMechanism(Long seed) {
        random = new Random(seed);
    }

    public Double generateFromRange(Double min, Double max) {
        double generatedValue = (random.nextDouble() % (max - min + 1)) + min;
        return generatedValue;
    }

    public <T> T pickItemByItsProbability (Map<T, Double> itemsProbability) {
        Iterator<Map.Entry<T, Double>> iterator = itemsProbability.entrySet().iterator();

        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0;

        while(iterator.hasNext()) {
            Map.Entry<T, Double> entry = iterator.next();
            cumulativeProbability += entry.getValue();
            if (randomValue <= cumulativeProbability && entry.getValue() != 0.0D) {
                return entry.getKey();
            }
        }

        //TODO: Should never occur, i have to change it anyway.
        throw new NullPointerException();
    }

    public Long generateFromRange(Long min, Long max) {
        Long generatedValue = (random.nextLong() % (max - min + 1)) + min;
        return generatedValue;
    }
}

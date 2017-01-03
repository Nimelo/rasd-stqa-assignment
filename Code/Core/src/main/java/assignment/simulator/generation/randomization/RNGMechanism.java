package assignment.simulator.generation.randomization;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class RNGMechanism {
    private Random random;

    public RNGMechanism(Random random) {
        this.random = random;
    }

    public Double generateFromRange(Double min, Double max) {
        double generatedValue = ThreadLocalRandom.current().nextDouble(min, max);
        return generatedValue;
    }
}

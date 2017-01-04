package assignment.simulator.generation.distribution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
class ExponentialProbabilityDistributionTest {
    private ExponentialProbabilityDistribution exponentialProbabilityDistribution;
    @BeforeEach
    void setUp() {
        exponentialProbabilityDistribution = new ExponentialProbabilityDistribution(0.5);
    }

    @Test
    void get() {
        Double value = exponentialProbabilityDistribution.get(0.0);
        assertEquals(new Double(0.5), value);
    }

    @Test
    void get1() {
        Long value = exponentialProbabilityDistribution.get(1L);
        assertEquals(new Long(0), value);
    }

}
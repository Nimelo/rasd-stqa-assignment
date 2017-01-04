package assignment.simulator.generation.randomization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
class RNGMechanismTest {
    private RNGMechanism rngMechanism;
    @BeforeEach
    void setUp() {
        rngMechanism = new RNGMechanism(1L);
    }

    @Test
    void generateFromRange() {
        Double value = rngMechanism.generateFromRange(0.0, 1.0);
        boolean condition = value >= 0.0 && value < 1.0;
        assertEquals(true, condition);
    }

    @Test
    void pickItemByItsProbability() {
        HashMap<String, Double> hashMap = new HashMap<>();
        hashMap.put("A", 1.0);
        String value = rngMechanism.pickItemByItsProbability(hashMap);

        assertEquals("A", value);
    }

    @Test
    void generateFromRange1() {
        Long value = rngMechanism.generateFromRange(0L, 10L);
        boolean condition = value >= 0L && value < 10L;
        assertEquals(true, condition);
    }

}
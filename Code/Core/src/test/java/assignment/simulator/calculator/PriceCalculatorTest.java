package assignment.simulator.calculator;

import assignment.configurations.simulation.objects.Node;
import assignment.simulator.objects.Job;
import assignment.simulator.objects.RequestedResource;
import assignment.simulator.objects.time.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
class PriceCalculatorTest {
    private PriceCalculator priceCalculator;
    private List<Node> nodeList;

    @BeforeEach
    void setUp() {
        nodeList = new ArrayList<Node>() {{
           add(new Node() {{
               setName("NODE");
               setAmountOfCores(16L);
               setAmount(5L);
               setPrice(new BigDecimal(7L));
           }});
        }};

        priceCalculator = new PriceCalculator(nodeList);
    }

    @Test
    void calculatePriceResource() {
        BigDecimal pr = priceCalculator.calculatePriceResource(new RequestedResource("NODE", 1L), 1.0, 1L);
        assertEquals(new BigDecimal(7L), pr);
    }

    @Test
    void calculatePrice() {
        Job job = new Job(0L, 0L, 10L, new ArrayList<RequestedResource>() {{
            add(new RequestedResource("NODE", 1L));
        }}, new Timestamp(0L));

        BigDecimal price = priceCalculator.calculatePrice(job, 1.0);

        assertEquals(new BigDecimal(10L * 1L * 7L), price);
    }

}
package suppliers;

import assignment.events.timing.arguments.TickType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import suppliers.objects.Time;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by mrnim on 26-Dec-16.
 */
class SimulationTimeSupplierTest {
    private SimulationTimeSupplier supplier;
    @BeforeEach
    void setUp() {
        supplier = new SimulationTimeSupplier(3L, 5L, 0L, 1L, 1L, 7L);
    }

    @Test
    void getWeek() {
        Time time = supplier.get();
        assertEquals(new Long(1), time.getTick());
        assertEquals(TickType.WEEK, time.getTickType());
    }

    @Test
    void getCutoff() {
        SimulationTimeSupplier simulationTimeSupplier = new SimulationTimeSupplier(4L, 5L, 2L, 1L, 2L, 7L);

        Time time = simulationTimeSupplier.get();
        assertEquals(new Long(3L), time.getTick());
        assertEquals(TickType.CUTOFF, time.getTickType());
        assertEquals(new Long(1L), time.getEndOfCutOffTick());
    }

    @Test
    void getWeekend() {
        supplier.getNextTick(); // 1
        supplier.getNextTick(); // 2

        Time time = supplier.get();
        assertEquals(new Long(3), time.getTick());
        assertEquals(TickType.WEEKEND, time.getTickType());
    }

    @Test
    void findNextStartOfWeekendTickZero() {
        Long value = supplier.findNextStartOfWeekendTick();
        assertEquals(new Long(3L), value);
    }

    @Test
    void findNextStartOfWeekendTickRandomTick() {
        for (int i = 0; i < 10; i++) {
            supplier.getNextTick();
        }

        Long value = supplier.findNextStartOfWeekendTick();
        assertEquals(new Long(17L), value);
    }

    @Test
    void getNextTick() {

    }

}
package assignment.resources.monitors;

import assignment.resources.monitors.exceptions.NotSufficientAmountOfCPUs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by mrnim on 25-Dec-16.
 */
class ResourcesMonitorTest {
    private ResourcesMonitor resourcesMonitor;

    @BeforeEach
    void setUp() {
        this.resourcesMonitor = new ResourcesMonitor(100L, 0L);
    }

    @Test
    void getFreeCPUs() {
        assertEquals(new Long(100L), resourcesMonitor.getFreeCPUs());
    }

    @Test
    void getTotalCPUs() {
        assertEquals(new Long(100L), resourcesMonitor.getTotalCPUs());
    }

    @Test
    void getUsedCPUs() {
        assertEquals(new Long(0L), resourcesMonitor.getUsedCPUs());
    }

    @Test
    void canReserveFalse() {
        boolean value = resourcesMonitor.canReserveCPUs(1000L);
        assertEquals(false, value);
    }

    @Test
    void canReserveTrue() {
        boolean value = resourcesMonitor.canReserveCPUs(10L);
        assertEquals(true, value);
    }

    @Test
    void reserve() {
        assertThrows(NotSufficientAmountOfCPUs.class, () ->
        {
            resourcesMonitor.reserveCPUs(1000L);
        });
    }

    @Test
    void releaseCPUs() throws NotSufficientAmountOfCPUs {
        resourcesMonitor.reserveCPUs(20L);
        resourcesMonitor.releaseCPUs(10L);
        assertEquals(new Long(90L), resourcesMonitor.getFreeCPUs());
    }

}
package assignment.resources.monitors;

import assignment.resources.hardware.HardwareResources;
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
        this.resourcesMonitor = new ResourcesMonitor(new HardwareResources(0L,100L, 16L), 0L);
    }

    @Test
    void getFreeCPUs() {
        assertEquals(new Long(100L), resourcesMonitor.getFreeCores());
    }

    @Test
    void getTotalCPUs() {
        assertEquals(new Long(100L), resourcesMonitor.getTotalCores());
    }

    @Test
    void getUsedCPUs() {
        assertEquals(new Long(0L), resourcesMonitor.getUsedCores());
    }

    @Test
    void canReserveFalse() {
        boolean value = resourcesMonitor.canReserveCores(1000L);
        assertEquals(false, value);
    }

    @Test
    void canReserveTrue() {
        boolean value = resourcesMonitor.canReserveCores(10L);
        assertEquals(true, value);
    }

    @Test
    void reserve() {
        assertThrows(NotSufficientAmountOfCPUs.class, () ->
        {
            resourcesMonitor.reserveCores(1000L);
        });
    }

    @Test
    void releaseCPUs() throws NotSufficientAmountOfCPUs {
        resourcesMonitor.reserveCores(20L);
        resourcesMonitor.releaseCores(10L);
        assertEquals(new Long(90L), resourcesMonitor.getFreeCores());
    }

}
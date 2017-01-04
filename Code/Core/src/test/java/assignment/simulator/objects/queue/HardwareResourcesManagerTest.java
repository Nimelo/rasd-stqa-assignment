package assignment.simulator.objects.queue;

import assignment.simulator.objects.NodeResourceEntry;
import assignment.simulator.objects.RequestedResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
class HardwareResourcesManagerTest {
    private HardwareResourcesManager hardwareResourcesManager;
    @BeforeEach
    void setUp() {
        hardwareResourcesManager = new HardwareResourcesManager(new HashMap<String, NodeResourceEntry>() {{
            put("NODE", new NodeResourceEntry("NODE", 10L, 16L, 0L));
        }});

    }

    @Test
    void tryAllocateResources() {
        ArrayList<RequestedResource> requestedResources = new ArrayList<RequestedResource>() {{
            add(new RequestedResource("NODE", 10L));
        }};

        boolean value = hardwareResourcesManager.tryAllocateResources(requestedResources);
        assertEquals(true, value);

        requestedResources.add(new RequestedResource("NODE", 1000L));

        boolean value2 = hardwareResourcesManager.tryAllocateResources(requestedResources);
        assertEquals(false, value2);
    }

    @Test
    void checkIfCanAllocate() {
        boolean value = hardwareResourcesManager.checkIfCanAllocate(new RequestedResource("NODE", 10L));
        assertEquals(true, value);

        boolean value2 = hardwareResourcesManager.checkIfCanAllocate(new RequestedResource("NODE", 10000L));
        assertEquals(false, value2);
    }

    @Test
    void allocateResourceList() {
        ArrayList<RequestedResource> requestedResources = new ArrayList<RequestedResource>() {{
            add(new RequestedResource("NODE", 10L));
        }};

        hardwareResourcesManager.allocateResourceList(requestedResources);

        assertEquals(new Long(10L), hardwareResourcesManager.getNodeResourceEntryMap().get("NODE").getUsedCores());
    }

    @Test
    void allocateResource() {
        hardwareResourcesManager.allocateResource(new RequestedResource("NODE", 10L));

        assertEquals(new Long(10L), hardwareResourcesManager.getNodeResourceEntryMap().get("NODE").getUsedCores());
    }

    @Test
    void getNodeResourceEntryMap() {
        Map<String, NodeResourceEntry> nodeResourceEntryMap = hardwareResourcesManager.getNodeResourceEntryMap();
        assertNotEquals(null, nodeResourceEntryMap);
    }

    @Test
    void deallocateResources() {
        ArrayList<RequestedResource> requestedResources = new ArrayList<RequestedResource>() {{
            add(new RequestedResource("NODE", 10L));
        }};

        hardwareResourcesManager.allocateResourceList(requestedResources);
        hardwareResourcesManager.deallocateResources(requestedResources);

        assertEquals(new Long(0L), hardwareResourcesManager.getNodeResourceEntryMap().get("NODE").getUsedCores());
    }

    @Test
    void deallocateResource() {
        hardwareResourcesManager.allocateResource(new RequestedResource("NODE", 10L));
        hardwareResourcesManager.deallocateResource(new RequestedResource("NODE", 10L));

        assertEquals(new Long(0L), hardwareResourcesManager.getNodeResourceEntryMap().get("NODE").getUsedCores());
    }

}
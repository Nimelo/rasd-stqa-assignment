package assignment.simulator.generation;

import assignment.configurations.simulation.objects.*;
import assignment.simulator.objects.User;
import assignment.simulator.objects.queue.HardwareResourcesManager;
import assignment.simulator.objects.queue.Queue;
import assignment.simulator.objects.queue.UserJob;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
class QueueSpawnerTest {
    private QueueSpawner spawner;
    @BeforeEach
    void setUp() {
        List<QueueProperties> queueProperties = new ArrayList<QueueProperties>() {{
            add(new QueueProperties() {
                {
                    setName("QUEUE");
                    setMaximumExecutionTime(13L);
                    setMaxNumberOfConcurrentJobsPerUser(2L);
                    setPriceFactor(0.5D);
                    setReservedResources(new ArrayList<ReservedResource>() {{
                        add(new ReservedResource() {{
                            setNodeType("NODE_S");
                            setAmount(1L);
                        }});
                    }});
                    setConstraintResources(new ArrayList<ConstraintResource>() {{
                        add(new ConstraintResource() {{
                            setNodeType("NODE_S");
                            setAmountOfCores(0L);
                        }});
                    }});
                    setAvailabilityTime(new AvailabilityTime() {{
                        setBegin(new ShiftTime() {{
                            setDayOfWeek(DayOfWeek.TUESDAY);
                            setHours(0L);
                            setMinutes(0L);
                        }});
                        setEnd(new ShiftTime() {{
                            setDayOfWeek(DayOfWeek.FRIDAY);
                            setHours(0L);
                            setMinutes(0L);
                        }});
                    }});
                }
            });
        }};

        List<Node> nodes = new ArrayList<Node>() {{
            add(new Node() {{
                setName("NODE_S");
                setPrice(new BigDecimal(124));
                setAmount(12L);
                setAmountOfCores(16L);
            }});
        }};

        ArrayList<User> users = new ArrayList<User>() {{
            add(new User(0L, new BigDecimal(10L), 0.5, 0.5, null));
        }};

        spawner = new QueueSpawner(queueProperties, nodes, users);
    }

    @Test
    void spawnQueues() {
        List<Queue> queues = spawner.spawnQueues();

        assertEquals(1L, queues.size());

        assertQueue(queues.get(0));
    }

    private void assertQueue(Queue queue) {
        Map<Long, UserJob> userJobAmountMap = queue.getUserJobAmountMap();
        UserJob userJob = userJobAmountMap.get(0L);

        assertEquals(new Long(0L), userJob.getAmountOfExecutingJobs());
    }

    @Test
    void createHardwareResourceManagerBasedOn() {
        HardwareResourcesManager hrm = spawner.createHardwareResourceManagerBasedOn(new ArrayList<ReservedResource>() {{
            add(new ReservedResource() {{
                setNodeType("NODE_S");
                setAmount(1L);
            }});
        }});

        assertEquals(1, hrm.getNodeResourceEntryMap().size());

        assertEquals("NODE_S", hrm.getNodeResourceEntryMap().get("NODE_S").getNodeType());
        assertEquals(new Long(1L), hrm.getNodeResourceEntryMap().get("NODE_S").getNodeAmount());
    }

    @Test
    void findCoresPerNode() {
        Long coresPerNode = spawner.findCoresPerNode("NODE_S");

        assertEquals(new Long(16L), coresPerNode);
    }

}
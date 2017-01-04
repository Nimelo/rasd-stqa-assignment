package assignment.simulator.matchers;

import assignment.configurations.simulation.objects.*;
import assignment.simulator.matchers.exceptions.JobToQueueMatchingException;
import assignment.simulator.objects.Job;
import assignment.simulator.objects.NodeResourceEntry;
import assignment.simulator.objects.RequestedResource;
import assignment.simulator.objects.User;
import assignment.simulator.objects.queue.HardwareResourcesManager;
import assignment.simulator.objects.queue.Queue;
import assignment.simulator.objects.time.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
class JobToQueueMatcherTest {
    private JobToQueueMatcher matcher;
    private QueueProperties queueProperties;

    @BeforeEach
    void setUp() {
        queueProperties = new QueueProperties() {
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
                        setAmountOfCores(10L);
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
        };
        ArrayList<User> userList = new ArrayList<User>() {{
            add(new User(0L, new BigDecimal(10L), 0.5, 0.5, null, 1L, 1L));
        }};

        HardwareResourcesManager hardwareResourcesManager = new HardwareResourcesManager(new HashMap<String, NodeResourceEntry>() {{
            put("NODE_S", new NodeResourceEntry("NODE_S", 1L, 16L, 0L));
        }});

        matcher = new JobToQueueMatcher(new ArrayList<Queue>() {{
            add(new Queue(queueProperties, userList, hardwareResourcesManager));
        }});
    }

    @Test
    void match() throws JobToQueueMatchingException {
        Job job = new Job(0L, 0L, 10L, new ArrayList<RequestedResource>() {{
            add(new RequestedResource("NODE_S", 1L));
        }});

        Queue q = matcher.match(job);

        assertEquals("QUEUE", q.getQueueProperties().getName());
    }

    @Test
    void checkResourcesListConstraints() {
        ArrayList<RequestedResource> requestedResources = new ArrayList<RequestedResource>() {{
            add(new RequestedResource("NODE_S", 1L));
        }};

        boolean value = matcher.checkResourcesListConstraints(requestedResources, queueProperties.getConstraintResources());

        assertEquals(true, value);

        requestedResources.add(new RequestedResource("NODE_S", 1000L));

        boolean value2 = matcher.checkResourcesListConstraints(requestedResources, queueProperties.getConstraintResources());
        assertEquals(false, value2);
    }

    @Test
    void checkSingleResourceConstraint() {

        boolean value = matcher.checkSingleResourceConstraint(new RequestedResource("NODE_S", 1L), queueProperties.getConstraintResources());

        assertEquals(true, value);

        boolean value2 = matcher.checkSingleResourceConstraint(new RequestedResource("NODE_S", 100000L), queueProperties.getConstraintResources());
        assertEquals(false, value2);
    }

}
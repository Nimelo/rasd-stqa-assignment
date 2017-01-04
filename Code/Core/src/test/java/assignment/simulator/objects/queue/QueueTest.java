package assignment.simulator.objects.queue;

import assignment.configurations.simulation.objects.*;
import assignment.simulator.objects.*;
import assignment.simulator.objects.time.Timestamp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
class QueueTest {
    private static Queue queue;
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
        };
        ArrayList<User> userList = new ArrayList<User>() {{
            add(new User(0L, new BigDecimal(10L), 0.5, 0.5, null, 1L, 1L));
        }};

        HardwareResourcesManager hardwareResourcesManager = new HardwareResourcesManager(new HashMap<String, NodeResourceEntry>() {{
            put("NODE_S", new NodeResourceEntry("NODE_S", 1L, 16L, 0L));
        }});

        queue = new Queue(queueProperties, userList, hardwareResourcesManager);
    }

    @Test
    void iteration() {
        Job job = new Job(0L, 0L, 10L, new ArrayList<RequestedResource>() {{
            add(new RequestedResource("NODE_S", 1L));
        }});

        Job job2 = new Job(0L, 0L, 5L, new ArrayList<RequestedResource>() {{
            add(new RequestedResource("NODE_S", 1L));
        }});

        job2.setCalculatedPrice(new BigDecimal(0));

        queue.submitJob(job, new Timestamp(0L));
        queue.submitJob(job2, new Timestamp(0L));
        queue.iteration(new Timestamp((long) (DayOfWeek.WEDNESDAY.getValue() * 24 * 60 * 60)));

        assertEquals(JobStatus.EXECUTING, job.getJobStatus());
        assertEquals(JobStatus.EXECUTING, job2.getJobStatus());

        queue.iteration(new Timestamp((long) (DayOfWeek.WEDNESDAY.getValue() * 24 * 60 * 60) + 5));

        assertEquals(JobStatus.EXECUTING, job.getJobStatus());
        assertEquals(JobStatus.EXECUTED, job2.getJobStatus());
    }

    @Test
    void isCutOffTime() {
        boolean value = queue.isCutOffTime(new Timestamp((long) (DayOfWeek.FRIDAY.getValue() - 1) * 24 * 60 * 60  - 10));
        assertEquals(true, value);

        boolean value2 = queue.isCutOffTime(new Timestamp((long) (DayOfWeek.FRIDAY.getValue() - 1) * 24 * 60 * 60 - queueProperties.getMaximumExecutionTime()));
        assertEquals(false, value2);
    }

    @Test
    void isInWorkingTime() {
        boolean value = queue.isInWorkingTime(new Timestamp((long) (DayOfWeek.WEDNESDAY.getValue() * 24 * 60 * 60)));
        assertEquals(true, value);
    }

    @Test
    void submitJob() {
        Job job = new Job(0L, 0L, 10L, new ArrayList<RequestedResource>() {{
            add(new RequestedResource("NODE_S", 1L));
        }});

        queue.submitJob(job, new Timestamp(0L));

        assertEquals(JobStatus.WAITING, job.getJobStatus());
        assertEquals(1L, queue.getWaitingArea().size());
    }

    @Test
    void switchAreas() {
        Job job = new Job(0L, 0L, 10L, new ArrayList<RequestedResource>() {{
            add(new RequestedResource("NODE_S", 1L));
        }});

        queue.submitJob(job, new Timestamp(0L));
        queue.switchAreas(new Timestamp(1L));

        assertEquals(0L, queue.getWaitingArea().size());
        assertEquals(1L, queue.getExecutionArea().size());

        assertEquals(JobStatus.EXECUTING, job.getJobStatus());
    }

    @Test
    void swipeExecutedJobs() {
        Job job = new Job(0L, 0L, 10L, new ArrayList<RequestedResource>() {{
            add(new RequestedResource("NODE_S", 1L));
        }});

        job.setCalculatedPrice(new BigDecimal(1L));
        queue.submitJob(job, new Timestamp(0L));
        queue.switchAreas(new Timestamp(1L));
        queue.swipeExecutedJobs(new Timestamp(job.getExecutionTime() + 1L));

        assertEquals(0L, queue.getWaitingArea().size());
        assertEquals(0L, queue.getExecutionArea().size());
        assertEquals(1L, queue.getExecutedJobs().size());

        assertEquals(JobStatus.EXECUTED, job.getJobStatus());
        assertEquals(new BigDecimal(9L), queue.getUserJobAmountMap().get(job.getUserId()).getUser().getBudget());
    }

}
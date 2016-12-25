package assignment.queues;

import assignment.events.timing.arguments.MetronomeEventArgs;
import assignment.resources.monitors.ResourcesMonitor;
import assignment.tasks.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by mrnim on 25-Dec-16.
 */
class FCFSZoneTest {
    private FCFSZone fcfsZone;
    private Collection<Job> waitingQueue;
    private Collection<Job> executionArea;
    private ResourcesMonitor resourcesMonitor;
    @BeforeEach
    void setUp() {
        waitingQueue = new ArrayList<>();
        executionArea = new ArrayList<>();
        resourcesMonitor = new ResourcesMonitor(100L, 0L);
        fcfsZone = new FCFSZone("Short queue", waitingQueue, executionArea, resourcesMonitor);
    }

    @Test
    void submitJob() {
        Job job = new Job(0L, 0L, 0L, 10L, 100L);
        fcfsZone.submitJob(job);

        assertEquals(1, waitingQueue.size());
    }

    @Test
    void doIteration() {
        Job job = new Job(0L, 0L, 0L, 10L, 100L);
        fcfsZone.submitJob(job);

        fcfsZone.doIteration(new MetronomeEventArgs(0L));
        fcfsZone.doIteration(new MetronomeEventArgs(101L));

        assertEquals(0, waitingQueue.size());
        assertEquals(0, executionArea.size());
        assertEquals(new Long(0L), job.getJobQueueQuitTimestamp());
        assertEquals(new Long(100L), job.getEndOfExecutionTime());
    }

    @Test
    void moveWaitingJobsToExecutionArea() {
        Job job = new Job(0L, 0L, 0L, 10L, 100L);
        Job job2 = new Job(0L, 0L, 0L, 1000L, 100L);

        fcfsZone.submitJob(job);
        fcfsZone.submitJob(job2);
        fcfsZone.moveWaitingJobsToExecutionArea(new MetronomeEventArgs(0L));

        assertEquals(1, waitingQueue.size());
        assertEquals(1, executionArea.size());
    }

    @Test
    void removeJobsFromExecutionArea() {
        Job job = new Job(0L, 0L, 0L, 10L, 100L);
        job.setEndOfExecutionTime(10L);

        executionArea.add(job);

        fcfsZone.removeJobsFromExecutionArea(new MetronomeEventArgs(1000L));

        assertEquals(0, executionArea.size());
    }

}
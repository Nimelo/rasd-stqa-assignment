package assignment.queues;

import assignment.events.timing.arguments.MetronomeEventArgs;
import assignment.resources.hardware.HardwareResources;
import assignment.resources.monitors.ResourcesMonitor;
import assignment.resources.objects.RequestedHardware;
import assignment.resources.objects.RequestedTime;
import assignment.tasks.Job;
import assignment.tasks.objects.Identification;
import assignment.tasks.objects.Requests;
import assignment.tasks.objects.Timestamp;
import assignment.tasks.objects.Timestamps;
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
        HardwareResources hardwareResources = new HardwareResources(4L, 100L, 16L);
        resourcesMonitor = new ResourcesMonitor(hardwareResources, 0L);
        fcfsZone = new FCFSZone("Short queue", waitingQueue, executionArea, resourcesMonitor);
    }

    @Test
    void submitJob() {
        Job job = new Job(new Identification(0L, 0L),
                new Requests(new RequestedTime(100L), new RequestedHardware(10L, 0L, true)),
                new Timestamps());
        fcfsZone.submitJob(job);

        assertEquals(1, waitingQueue.size());
    }

    @Test
    void doIteration() {
        Job job = new Job(new Identification(0L, 0L),
                new Requests(new RequestedTime(100L), new RequestedHardware(10L, 0L, true)),
                new Timestamps());
        job.getTimestamps().setEnterQueueTimestamp(new Timestamp(0L));
        fcfsZone.submitJob(job);

        fcfsZone.doIteration(new MetronomeEventArgs(0L));
        fcfsZone.doIteration(new MetronomeEventArgs(101L));

        assertEquals(0, waitingQueue.size());
        assertEquals(0, executionArea.size());
        assertEquals(new Long(0L), job.getTimestamps().getQuitQueueTimestamp().getValue());
        assertEquals(new Long(100L), job.getTimestamps().getEndOfExecutionTimestamp().getValue());
    }

    @Test
    void moveWaitingJobsToExecutionArea() {
        Job job = new Job(new Identification(0L, 0L),
                new Requests(new RequestedTime(100L), new RequestedHardware(10L, 0L, true)),
                new Timestamps());
        job.getTimestamps().setEnterQueueTimestamp(new Timestamp(0L));

        Job job2 = new Job(new Identification(0L, 0L),
                new Requests(new RequestedTime(100L), new RequestedHardware(1000L, 0L, true)),
                new Timestamps());
        job2.getTimestamps().setEnterQueueTimestamp(new Timestamp(0L));

        fcfsZone.submitJob(job);
        fcfsZone.submitJob(job2);
        fcfsZone.moveWaitingJobsToExecutionArea(new MetronomeEventArgs(0L));

        assertEquals(1, waitingQueue.size());
        assertEquals(1, executionArea.size());
    }

    @Test
    void removeJobsFromExecutionArea() {
        Job job = new Job(new Identification(0L, 0L),
                new Requests(new RequestedTime(100L), new RequestedHardware(10L, 0L, true)),
                new Timestamps());
        job.getTimestamps().setEnterQueueTimestamp(new Timestamp(0L));
        job.getTimestamps().setEndOfExecutionTimestamp(new Timestamp(100L));

        executionArea.add(job);

        fcfsZone.removeJobsFromExecutionArea(new MetronomeEventArgs(1000L));

        assertEquals(0, executionArea.size());
    }

}
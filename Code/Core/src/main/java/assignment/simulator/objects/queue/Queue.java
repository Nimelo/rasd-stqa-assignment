package assignment.simulator.objects.queue;

import assignment.simulator.objects.Job;
import assignment.simulator.objects.queue.areas.JobArea;
import assignment.simulator.objects.time.Timestamp;
import assignment.simulator.objects.time.TimestampInterpretator;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class Queue {
    private String name;
    private JobArea waitingArea;
    private JobArea executionArea;
    private HardwareResourcesManager hardwareResourcesManager;

    private Long beginWorkTick;
    private Long endWorkTick;

    //Budgetanalytics xDDDDDD kurwa smieszne

    public Queue(String name, JobArea waitingArea, JobArea executionArea, HardwareResourcesManager hardwareResourcesManager, Long beginWorkTick, Long endWorkTick) {
        this.name = name;
        this.waitingArea = waitingArea;
        this.executionArea = executionArea;
        this.hardwareResourcesManager = hardwareResourcesManager;
        this.beginWorkTick = beginWorkTick;
        this.endWorkTick = endWorkTick;
    }

    public void iteration(Timestamp timestamp) {
        if (isInWorkingTime(timestamp)) {
            switchAreas(timestamp);
            swipeExecutedJobs(timestamp);
        }
        //TODO: should i remove jo bs from execution area?
    }

    private boolean isInWorkingTime(Timestamp timestamp) {
        Long tick = timestamp.getTick();
        if(tick >= beginWorkTick && tick <= endWorkTick) {
            return true;
        }
        return false;
    }

    public void submitJob(Job job, Timestamp timestamp) {
        job.getJobTimestamps().setWaitingAreaEnterTime(timestamp);
        waitingArea.getJobs().add(job);
    }

    public void switchAreas(Timestamp timestamp) {
        List<Job> jobs = waitingArea.getJobs();
        Iterator<Job> iterator = jobs.iterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            if (hardwareResourcesManager.tryAllocateResources(job.getRequestedResourceList())) {
                job.getJobTimestamps().setExecutionAreaQuitTime(timestamp);
                job.getJobTimestamps().setExecutionAreaEnterTime(timestamp);
                iterator.remove();
                executionArea.getJobs().add(job);
            } else {
                //TODO: Put strategy here
                break;
            }
        }
    }

    public void swipeExecutedJobs(Timestamp timestamp) {
        Iterator<Job> iterator = executionArea.getJobs().iterator();
        while(iterator.hasNext()) {
            Job job = iterator.next();
            Long expectedEndTimeTick = job.getJobTimestamps().getExecutionAreaEnterTime().getTick() + job.getExecutionTime();
            if (timestamp.getTick() >= expectedEndTimeTick) {
                hardwareResourcesManager.deallocateResources(job.getRequestedResourceList());
                job.getJobTimestamps().setExecutionAreaQuitTime(timestamp);
                iterator.remove();
            }
        }
    }

    public String getName() {
        return name;
    }
}

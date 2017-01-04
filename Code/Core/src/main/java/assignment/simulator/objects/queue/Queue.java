package assignment.simulator.objects.queue;

import assignment.configurations.simulation.objects.QueueProperties;
import assignment.simulator.objects.Job;
import assignment.simulator.objects.JobStatus;
import assignment.simulator.objects.User;
import assignment.simulator.objects.time.Timestamp;
import assignment.simulator.objects.time.Time;

import java.util.*;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class Queue {
    private QueueProperties queueProperties;
    private List<Job> waitingArea;
    private List<Job> executionArea;
    private List<Job> executedJobs;
    private Map<Long, UserJob> userJobAmountMap;

    private Long beginWorkTick;
    private Long endWorkTick;

    private HardwareResourcesManager hardwareResourcesManager;

    public Queue(QueueProperties queueProperties, List<User> users, HardwareResourcesManager hardwareResourcesManager) {
        this.queueProperties = queueProperties;
        this.waitingArea = new ArrayList<>();
        this.executionArea = new ArrayList<>();
        this.executedJobs = new ArrayList<>();
        this.userJobAmountMap = new HashMap<>();

        for (User user : users) {
            userJobAmountMap.put(user.getId(), new UserJob(user, 0L));
        }

        this.beginWorkTick = this.queueProperties.getAvailabilityTime().getBegin().toWeekTick();
        this.endWorkTick = this.queueProperties.getAvailabilityTime().getEnd().toWeekTick();

        this.hardwareResourcesManager = hardwareResourcesManager;
    }

    public QueueProperties getQueueProperties() {
        return queueProperties;
    }

    public void iteration(Timestamp timestamp) {
        if (isInWorkingTime(timestamp)) {
            if (!isCutOffTime(timestamp)) {
                switchAreas(timestamp);
            }
            swipeExecutedJobs(timestamp);
        }
    }

    public boolean isCutOffTime(Timestamp timestamp) {
        Long tick = timestamp.getTick() % Time.WEEK;
        if (tick + queueProperties.getMaximumExecutionTime() > endWorkTick) {
            return true;
        }
        return false;
    }

    public boolean isInWorkingTime(Timestamp timestamp) {
        Long tick = timestamp.getTick() % Time.WEEK;
        if (tick >= beginWorkTick && tick <= endWorkTick) {
            return true;
        }
        return false;
    }

    public void submitJob(Job job, Timestamp timestamp) {
        job.getJobTimestamps().setWaitingAreaEnterTime(timestamp);
        job.setJobStatus(JobStatus.WAITING);
        waitingArea.add(job);
    }

    public void switchAreas(Timestamp timestamp) {
        Iterator<Job> iterator = waitingArea.iterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            if (userJobAmountMap.get(job.getUserId()).getAmountOfExecutingJobs() < queueProperties.getMaxNumberOfConcurrentJobsPerUser()) {
                if (hardwareResourcesManager.tryAllocateResources(job.getRequestedResourceList())) {
                    job.getJobTimestamps().setWaitingAreaQuitTime(timestamp);
                    job.setJobStatus(JobStatus.EXECUTING);
                    job.getJobTimestamps().setExecutionAreaEnterTime(timestamp);
                    iterator.remove();
                    executionArea.add(job);
                    this.userJobAmountMap.get(job.getUserId()).increaseAmountOfExecutingJobs();
                } else {
                    //TODO: Put strategy here
                    break;
                }
            }
        }
    }

    public Map<Long, UserJob> getUserJobAmountMap() {
        return userJobAmountMap;
    }

    public void swipeExecutedJobs(Timestamp timestamp) {
        Iterator<Job> iterator = executionArea.iterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            Long expectedEndTimeTick = job.getJobTimestamps().getExecutionAreaEnterTime().getTick() + job.getExecutionTime();
            if (timestamp.getTick() >= expectedEndTimeTick) {
                hardwareResourcesManager.deallocateResources(job.getRequestedResourceList());
                job.getJobTimestamps().setExecutionAreaQuitTime(timestamp);
                job.setJobStatus(JobStatus.EXECUTED);
                iterator.remove();
                UserJob userJob = userJobAmountMap.get(job.getUserId());
                userJob.decreaseAmountOfExecutingJobs();
                userJob.getUser().decreaseBudget(job.getCalculatedPrice());
                executedJobs.add(job);
            }
        }
    }

    public List<Job> getWaitingArea() {
        return waitingArea;
    }

    public List<Job> getExecutionArea() {
        return executionArea;
    }

    public List<Job> getExecutedJobs() {
        return executedJobs;
    }
}

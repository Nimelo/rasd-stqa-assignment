package assignment.simulator.objects;

import java.util.List;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class Job {
    private Long id;
    private Long userId;
    private Long executionTime;
    private List<RequestedResource> requestedResourceList;
    private JobTimestamps jobTimestamps;
    private String queueName;

    public Job(Long id, Long userId, Long executionTime, List<RequestedResource> requestedResourceList, JobTimestamps jobTimestamps) {
        this.id = id;
        this.userId = userId;
        this.executionTime = executionTime;
        this.requestedResourceList = requestedResourceList;
        this.jobTimestamps = jobTimestamps;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public List<RequestedResource> getRequestedResourceList() {
        return requestedResourceList;
    }

    public JobTimestamps getJobTimestamps() {
        return jobTimestamps;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }
}

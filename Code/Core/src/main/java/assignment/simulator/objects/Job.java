package assignment.simulator.objects;

import assignment.simulator.objects.time.Timestamp;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class Job {
    private Long id;
    private JobStatus jobStatus;
    private Long userId;
    private Long executionTime;
    private List<RequestedResource> requestedResourceList;
    private JobTimestamps jobTimestamps;
    private String queueName;
    private BigDecimal calculatedPrice;

    public Job(Long id, Long userId, Long executionTime, List<RequestedResource> requestedResourceList) {
        this.id = id;
        this.jobStatus = jobStatus;
        this.userId = userId;
        this.executionTime = executionTime;
        this.requestedResourceList = requestedResourceList;
        this.jobTimestamps = new JobTimestamps();
        jobStatus = JobStatus.SPAWNED;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setCalculatedPrice(BigDecimal calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
    }

    public Long getId() {
        return id;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
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

    public String getQueueName() {
        return queueName;
    }

    public BigDecimal getCalculatedPrice() {
        return calculatedPrice;
    }
}

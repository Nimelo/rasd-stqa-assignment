package assignment.tasks;

/**
 * Created by mrnim on 23-Dec-16.
 */
public class Job {
    private Long id;
    private Long userId;
    private Long jobQueueEnterTimestamp;
    private Long jobQueueQuitTimestamp;
    private Long amountOfCPUs;
    private Long endOfExecutionTime;
    private Long executionTime;

    public Job(Long id, Long userId, Long jobQueueEnterTimestamp, Long amountOfCPUs, Long executionTime) {
        this.id = id;
        this.userId = userId;
        this.jobQueueEnterTimestamp = jobQueueEnterTimestamp;
        this.amountOfCPUs = amountOfCPUs;
        this.executionTime = executionTime;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setJobQueueEnterTimestamp(Long jobQueueEnterTimestamp) {
        this.jobQueueEnterTimestamp = jobQueueEnterTimestamp;
    }

    public void setJobQueueQuitTimestamp(Long jobQueueQuitTimestamp) {
        this.jobQueueQuitTimestamp = jobQueueQuitTimestamp;
    }

    public Long getJobQueueEnterTimestamp() {
        return jobQueueEnterTimestamp;
    }

    public Long getJobQueueQuitTimestamp() {
        return jobQueueQuitTimestamp;
    }

    public Long getAmountOfCPUs() {
        return amountOfCPUs;
    }

    public void setAmountOfCPUs(Long amountOfCPUs) {
        this.amountOfCPUs = amountOfCPUs;
    }

    public Long getEndOfExecutionTime() {
        return endOfExecutionTime;
    }

    public void setEndOfExecutionTime(Long endOfExecutionTime) {
        this.endOfExecutionTime = endOfExecutionTime;
    }

}

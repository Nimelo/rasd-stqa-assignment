package assignment.configurations.simulation.objects;

import java.util.List;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class JobType {
    private Long minExecutionTime;
    private Long maxExecutionTime;
    private String name;
    private Double probabilityOfJob;
    private List<JobTypeTuple> tuples;

    public Double getProbabilityOfJob() {
        return probabilityOfJob;
    }

    public void setProbabilityOfJob(Double probabilityOfJob) {
        this.probabilityOfJob = probabilityOfJob;
    }

    public List<JobTypeTuple> getTuples() {
        return tuples;
    }

    public void setTuples(List<JobTypeTuple> tuples) {
        this.tuples = tuples;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMinExecutionTime() {
        return minExecutionTime;
    }

    public void setMinExecutionTime(Long minExecutionTime) {
        this.minExecutionTime = minExecutionTime;
    }

    public Long getMaxExecutionTime() {
        return maxExecutionTime;
    }

    public void setMaxExecutionTime(Long maxExecutionTime) {
        this.maxExecutionTime = maxExecutionTime;
    }
}

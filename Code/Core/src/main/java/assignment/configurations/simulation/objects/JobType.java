package assignment.configurations.simulation.objects;

import java.util.List;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class JobType {
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
}

package assignment.configurations.simulation;

import assignment.configurations.simulation.objects.JobType;

import java.util.List;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class JobTypesConfiguration{
    private List<JobType> jobTypes;

    public List<JobType> getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(List<JobType> jobTypes) {
        this.jobTypes = jobTypes;
    }
}

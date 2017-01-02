package assignment.configurations.simulation;

import assignment.configurations.simulation.exceptions.ValidationException;
import assignment.configurations.simulation.interfaces.IValidate;
import assignment.configurations.simulation.objects.JobType;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class JobTypesConfiguration implements IValidate{
    private List<JobType> jobTypes;

    @Override
    public void validate() throws ValidationException {
        DoubleSummaryStatistics collect = jobTypes.stream().collect(Collectors.summarizingDouble(JobType::getProbabilityOfJob));
        double sum = collect.getSum();
        if (sum != 1L) {
            throw new ValidationException("Sum of probabilities of job types is not equal to 1.", JobType.class);
        }

        for (int i = 0; i < jobTypes.size(); i++) {
            for (int j = 0; j < jobTypes.size(); j++) {
                if (i != j) {
                    JobType current = jobTypes.get(i);
                    JobType toCheck = jobTypes.get(j);

                    if (current.getName().equals(toCheck.getName())) {
                        throw new ValidationException("Multiple occurences of job type names.", JobType.class);
                    }
                }
            }
        }
    }

    public List<JobType> getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(List<JobType> jobTypes) {
        this.jobTypes = jobTypes;
    }
}

package assignment.simulator.generation;

import assignment.configurations.simulation.objects.JobType;
import assignment.configurations.simulation.objects.JobTypeTuple;
import assignment.simulator.generation.distribution.ExponentialProbabilityDistribution;
import assignment.simulator.generation.randomization.RNGMechanism;
import assignment.simulator.objects.Job;
import assignment.simulator.objects.JobTimestamps;
import assignment.simulator.objects.RequestedResource;
import assignment.simulator.objects.User;
import assignment.simulator.objects.time.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class JobSpawner {
    private Long currentId;
    private List<JobType> jobTypes;
    private RNGMechanism rngMechanism;

    public JobSpawner(List<JobType> jobTypes, RNGMechanism rngMechanism) {
        this.jobTypes = jobTypes;
        this.rngMechanism = rngMechanism;
        currentId = 0L;
    }

    public Job spawnJobForUser(User user, Timestamp timestamp) {
        Map<String, Double> stringDoubleMap = jobTypes.stream().collect(Collectors.toMap(JobType::getName, JobType::getProbabilityOfJob));
        String name = rngMechanism.pickItemByItsProbability(stringDoubleMap);
        JobType jobType = jobTypes.stream().filter(x -> x.getName().equals(name)).findFirst().get();

        Long executionTime = rngMechanism.generateFromRange(jobType.getMinExecutionTime(), jobType.getMaxExecutionTime());

        List<RequestedResource> requestedResourceList = generateRequestedResource(user, timestamp, jobType);

        Job job = new Job(currentId++, user.getId(), executionTime, requestedResourceList);
        return job;
    }

    public List<RequestedResource> generateRequestedResource(User user, Timestamp timestamp, JobType jobType) {
        List<RequestedResource> requestedResources = new ArrayList<>();
        ExponentialProbabilityDistribution exponentialProbabilityDistribution = new ExponentialProbabilityDistribution(user.getRequestSizeLambda());

        for (JobTypeTuple tuple : jobType.getTuples()) {
            Double expRandom = exponentialProbabilityDistribution.get(rngMechanism.getRandom().nextDouble()) * user.getRequestSizeFactor();
            Long cores = (long)(double)expRandom + rngMechanism.getRandom().nextInt((int)(long)tuple.getMaximumAmountOfCores());
            Long amountOfCores = Math.max(cores % tuple.getMaximumAmountOfCores(), 1L);
            requestedResources.add(new RequestedResource(tuple.getNodeType(), amountOfCores));
        }

        return requestedResources;
    }
}

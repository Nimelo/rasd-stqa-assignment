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

    public JobSpawner(Long currentId, List<JobType> jobTypes, RNGMechanism rngMechanism) {
        this.currentId = currentId;
        this.jobTypes = jobTypes;
        this.rngMechanism = rngMechanism;
    }

    public JobSpawner(List<JobType> jobTypes, RNGMechanism rngMechanism) {
        this.jobTypes = jobTypes;
        this.rngMechanism = rngMechanism;
    }

    public Job spawnJobForUser(User user, Timestamp timestamp) {
        Map<String, Double> stringDoubleMap = jobTypes.stream().collect(Collectors.toMap(JobType::getName, JobType::getProbabilityOfJob));
        String name = rngMechanism.pickItemByItsProbability(stringDoubleMap);
        JobType jobType = jobTypes.stream().filter(x -> x.getName().equals(name)).findFirst().get();

        Long executionTime = rngMechanism.generateFromRange(jobType.getMinExecutionTime(), jobType.getMaxExecutionTime());

        List<RequestedResource> requestedResourceList = generateRequestedResource(user, timestamp, jobType);

        ExponentialProbabilityDistribution exponentialProbabilityDistribution = new ExponentialProbabilityDistribution(user.getJobDistributionLambda());

        Double spawnTick = exponentialProbabilityDistribution.get(Double.valueOf(timestamp.getTick()));
        Timestamp spawnTime = new Timestamp((long)(double)spawnTick);

        Job job = new Job(currentId++, user.getId(), executionTime, requestedResourceList, new JobTimestamps(spawnTime));
        return job;
    }

    public List<RequestedResource> generateRequestedResource(User user, Timestamp timestamp, JobType jobType) {
        List<RequestedResource> requestedResources = new ArrayList<>();
        ExponentialProbabilityDistribution exponentialProbabilityDistribution = new ExponentialProbabilityDistribution(user.getRequestSizeLambda());

        for (JobTypeTuple tuple : jobType.getTuples()) {
            Long amountOfNodes = Math.max(exponentialProbabilityDistribution.get(timestamp.getTick()) % tuple.getMaximumAmountOfCores(), 1L);
            Long amountOfCores = Math.max(exponentialProbabilityDistribution.get(timestamp.getTick()) % tuple.getMaximumAmountOfCores(), 1L);
            requestedResources.add(new RequestedResource(tuple.getNodeType(), amountOfNodes, amountOfCores));
        }

        return requestedResources;
    }
}

package assignment.simulator;

import assignment.configurations.simulation.Configuration;
import assignment.simulator.budget.BudgetAnalytics;
import assignment.simulator.calculator.PriceCalculator;
import assignment.simulator.generation.JobSpawner;
import assignment.simulator.generation.QueueSpawner;
import assignment.simulator.generation.UserSpawner;
import assignment.simulator.generation.randomization.RNGMechanism;
import assignment.simulator.matchers.JobToQueueMatcher;
import assignment.simulator.matchers.exceptions.JobToQueueMatchingException;
import assignment.simulator.objects.Job;
import assignment.simulator.objects.JobTimestamps;
import assignment.simulator.objects.User;
import assignment.simulator.objects.queue.Queue;
import assignment.simulator.objects.time.Timestamp;
import assignment.simulator.reporting.Report;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class Simulator {
    private Configuration configuration;
    private List<Queue> queues;
    private List<User> users;

    private JobSpawner jobSpawner;
    private JobToQueueMatcher jobToQueueMatcher;

    private PriceCalculator priceCalculator;

    public Simulator(Configuration configuration) {
        this.configuration = configuration;
        UserSpawner userSpawner = new UserSpawner(configuration.getUserGroupsConfiguration().getUserGroups());
        this.users = userSpawner.spawn();
        QueueSpawner queueSpawner = new QueueSpawner(configuration.getQueuesConfiguration().getQueues(), configuration.getSystemResources().getNodes(), this.users);
        this.queues = queueSpawner.spawnQueues();

        jobSpawner = new JobSpawner(configuration.getJobTypesConfiguration().getJobTypes(), new RNGMechanism(configuration.getRngSeed()));
        jobToQueueMatcher = new JobToQueueMatcher(queues);
        priceCalculator = new PriceCalculator(configuration.getSystemResources().getNodes());
    }

    public Report run() throws JobToQueueMatchingException {
        Long currentTick = 0L;
        Long amountOfTicks = configuration.getSimulationTime().getNumberOfWeeks() * 24 * 7 * 60 * 60;

        while (currentTick < amountOfTicks) {
            doIteration(new Timestamp(currentTick++));
        }

        return doReport();
    }

    private Report doReport() {
        Report report = new Report();

        HashMap<String, Long> throughput = new HashMap<>();
        for (Queue queue : queues) {
            throughput.put(queue.getQueueProperties().getName(), Long.valueOf(queue.getExecutedJobs().size()));
        }
        report.setThroughput(throughput);

        Long actualNumberOfMachineHoursConsumedByUserJobs = queues.stream()
                .flatMap(x -> x.getExecutedJobs().stream())
                .collect(Collectors.toList())
                .stream()
                .mapToLong(y -> {
                    JobTimestamps jobTimestamps = y.getJobTimestamps();
                    return jobTimestamps.getExecutionAreaQuitTime().getTick() - jobTimestamps.getExecutionAreaEnterTime().getTick();
                }).sum();
        report.setActualNumberOfMachineHoursConsumedByUserJobs(actualNumberOfMachineHoursConsumedByUserJobs);

        BigDecimal resultingPricePaidByTheUsers = new BigDecimal(queues.stream()
                .flatMap(x -> x.getExecutedJobs().stream())
                .collect(Collectors.toList())
                .stream()
                .mapToDouble(y -> {
                    return y.getCalculatedPrice().doubleValue();
                }).sum());
        report.setResultingPricePaidByUsers(resultingPricePaidByTheUsers);

        HashMap<String, Long> averageWaitTimeInEachQueue = new HashMap<>();
        for (Queue queue : queues) {
            Double waitTime = queue.getExecutedJobs().stream()
                    .mapToDouble(x -> {
                        JobTimestamps jobTimestamps = x.getJobTimestamps();
                        return new Double(jobTimestamps.getWaitingAreaQuitTime().getTick() - jobTimestamps.getWaitingAreaEnterTime().getTick());
                    }).average().getAsDouble();

            averageWaitTimeInEachQueue.put(queue.getQueueProperties().getName(), (long)(double)waitTime);
        }
        report.setAverageWaitTimeInEachQueue(averageWaitTimeInEachQueue);

        double turnAroundTimeRatio = queues.stream()
                .flatMap(x -> x.getExecutedJobs().stream())
                .collect(Collectors.toList())
                .stream()
                .mapToDouble(y -> {
                    JobTimestamps jobTimestamps = y.getJobTimestamps();
                    return new Double((jobTimestamps.getExecutionAreaQuitTime().getTick() - jobTimestamps.getWaitingAreaEnterTime().getTick()) / (jobTimestamps.getExecutionAreaQuitTime().getTick() - jobTimestamps.getExecutionAreaEnterTime().getTick()));
                }).average().getAsDouble();
        report.setTurnAroundTimeRatio((long)turnAroundTimeRatio);

        BigDecimal economicBalanceOfCentre = new BigDecimal(configuration.getSimulationTime().getNumberOfWeeks() * 7 * 24).multiply(configuration.getMachineOperationalCost()).subtract(resultingPricePaidByTheUsers);
        report.setEconomicBalanceOfTheCentre(economicBalanceOfCentre);

        return report;
    }


    private void doIteration(Timestamp timestamp) throws JobToQueueMatchingException {
        for (User user : users) {
            if (canSpawnNextJob(user, timestamp)) {
                Job job = jobSpawner.spawnJobForUser(user, timestamp);
                Queue queue = jobToQueueMatcher.match(job);
                BigDecimal price = priceCalculator.calculatePrice(job, queue.getQueueProperties().getPriceFactor());

                if (user.getBudget().subtract(price).compareTo(new BigDecimal(0)) >= 0) {
                    job.setCalculatedPrice(price);
                    job.setQueueName(queue.getQueueProperties().getName());
                    queue.submitJob(job, timestamp);
                }
            }
        }

        for (Queue queue : queues) {
            queue.iteration(timestamp);
        }
    }

    private boolean canSpawnNextJob(User user, Timestamp timestamp) {
        Timestamp nextJobSubmission = user.getNextJobSubmission();
        if (nextJobSubmission == null || nextJobSubmission.getTick() < timestamp.getTick()) {
            return true;
        }
        return false;
    }
}

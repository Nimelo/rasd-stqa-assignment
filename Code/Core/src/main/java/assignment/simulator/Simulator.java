package assignment.simulator;

import assignment.simulator.budget.BudgetAnalytics;
import assignment.simulator.generation.JobSpawner;
import assignment.simulator.matchers.JobToQueueMatcher;
import assignment.simulator.matchers.exceptions.JobToQueueMatchingException;
import assignment.simulator.objects.Job;
import assignment.simulator.objects.JobTimestamps;
import assignment.simulator.objects.User;
import assignment.simulator.objects.queue.Queue;
import assignment.simulator.objects.time.Timestamp;
import assignment.simulator.reporting.Report;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class Simulator {
    private List<Queue> queues;
    private List<User> users;
    private List<Job> jobs;

    private JobSpawner jobSpawner;
    private JobToQueueMatcher jobToQueueMatcher;

    private BudgetAnalytics budgetAnalytics;

    public Simulator(List<Queue> queues, List<User> users, List<Job> jobs, JobSpawner jobSpawner, JobToQueueMatcher jobToQueueMatcher, BudgetAnalytics budgetAnalytics) {
        this.queues = queues;
        this.users = users;
        this.jobs = jobs;
        this.jobSpawner = jobSpawner;
        this.jobToQueueMatcher = jobToQueueMatcher;
        this.budgetAnalytics = budgetAnalytics;
    }

    public Report run(Timestamp from, Long tickCount) throws JobToQueueMatchingException {
        Long currentTick = from.getTick();
        Long amountOfTicks = 0L;
        while(amountOfTicks < tickCount) {
            doIteration(new Timestamp(currentTick++));
            amountOfTicks++;
        }

        return doReport(tickCount);
    }

    private Report doReport(Long tickCount) {
        Report report = new Report();
        Map<String, Long> throughput = queues.stream().collect(Collectors.toMap(Queue::getName, Queue::getJobExecuted));
        report.setThroughput(throughput);

        long sum = jobs.stream().mapToLong(x -> {
            JobTimestamps jobTimestamps = x.getJobTimestamps();
            long l = jobTimestamps.getExecutionAreaQuitTime().getTick() - jobTimestamps.getSpawnTime().getTick();
            return l;
        }).sum();
        report.setActualNumberOfMachineHoursConsumedBYUserJobs(sum);

        BigDecimal all = new BigDecimal(0);
        for (Job job : jobs) {
            all = all.add(budgetAnalytics.calculatePrice(job, job.getQueueName()));
        }
        report.setResultingPricePaidByUsers(all);

        double turnAround = jobs.stream().mapToLong(x -> {
            JobTimestamps jobTimestamps = x.getJobTimestamps();
            long placeCompletion = jobTimestamps.getExecutionAreaQuitTime().getTick() - jobTimestamps.getWaitingAreaEnterTime().getTick();
            long runtime = jobTimestamps.getExecutionAreaQuitTime().getTick() - jobTimestamps.getExecutionAreaEnterTime().getTick();

            return placeCompletion / runtime;
        }).average().getAsDouble();
        report.setTurnAround((long) turnAround);

        return report;
    }

    private void doIteration(Timestamp timestamp) throws JobToQueueMatchingException {
        for (User user : users) {
            if(canSpawnNextJob(user, timestamp)) {
                Job job = jobSpawner.spawnJobForUser(user, timestamp);
                String queueName = jobToQueueMatcher.match(job);

                BigDecimal price = budgetAnalytics.calculatePrice(job, queueName);

                if (user.getBudget().subtract(price).compareTo(new BigDecimal(0)) >= 0) {
                    job.setQueueName(queueName);
                    jobs.add(job);
                    submitToQueue(queueName, job, timestamp);
                }
            }
        }

        for (Queue queue : queues) {
            queue.iteration(timestamp);
        }
    }

    private void submitToQueue(String queueName, Job job, Timestamp timestamp) {
        for (Queue queue : queues) {
            if (queue.getName().equals(queueName)) {
                queue.submitJob(job, timestamp);
            }
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

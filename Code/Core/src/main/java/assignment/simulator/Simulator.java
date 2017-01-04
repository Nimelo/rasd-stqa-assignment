package assignment.simulator;

import assignment.simulator.generation.JobSpawner;
import assignment.simulator.generation.UserSpawner;
import assignment.simulator.generation.randomization.RNGMechanism;
import assignment.simulator.matchers.JobToQueueMatcher;
import assignment.simulator.matchers.exceptions.JobToQueueMatchingException;
import assignment.simulator.objects.Job;
import assignment.simulator.objects.User;
import assignment.simulator.objects.queue.Queue;
import assignment.simulator.objects.time.Timestamp;

import java.util.List;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class Simulator {
    private List<Queue> queues;
    private List<User> users;
    private List<Job> jobs;

    private JobSpawner jobSpawner;
    private JobToQueueMatcher jobToQueueMatcher;

    public Simulator(List<Queue> queues, List<User> users, List<Job> jobs, JobSpawner jobSpawner, JobToQueueMatcher jobToQueueMatcher) {
        this.queues = queues;
        this.users = users;
        this.jobs = jobs;
        this.jobSpawner = jobSpawner;
        this.jobToQueueMatcher = jobToQueueMatcher;
    }

    public void run(Timestamp from, Long tickCount) throws JobToQueueMatchingException {
        Long currentTick = from.getTick();
        Long amountOfTicks = 0L;
        while(amountOfTicks < tickCount) {
            doIteration(new Timestamp(currentTick++));
            amountOfTicks++;
        }
    }

    private void doIteration(Timestamp timestamp) throws JobToQueueMatchingException {
        for (User user : users) {
            if(canSpawnJob(user, timestamp)) {
                Job job = jobSpawner.spawnJobForUser(user, timestamp);
                jobs.add(job);
                String queueName = jobToQueueMatcher.match(job);
                submitToQueue(queueName, job, timestamp);
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

    private boolean canSpawnJob(User user, Timestamp timestamp) {
        Timestamp nextJobSubmission = user.getNextJobSubmission();
        if (nextJobSubmission == null || nextJobSubmission.getTick() < timestamp.getTick()) {
            return true;
        }
        return false;
    }
}

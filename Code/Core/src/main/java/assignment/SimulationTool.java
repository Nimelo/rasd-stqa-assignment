package assignment;

import assignment.configurations.simulation.Configuration;
import assignment.io.readers.configurations.simulation.ConfigurationReader;
import assignment.simulator.Simulator;
import assignment.simulator.budget.BudgetAnalytics;
import assignment.simulator.generation.JobSpawner;
import assignment.simulator.generation.QueueSpawner;
import assignment.simulator.generation.UserSpawner;
import assignment.simulator.generation.randomization.RNGMechanism;
import assignment.simulator.matchers.JobToQueueMatcher;
import assignment.simulator.matchers.exceptions.JobToQueueMatchingException;
import assignment.simulator.objects.Job;
import assignment.simulator.objects.User;
import assignment.simulator.objects.queue.Queue;
import assignment.simulator.objects.time.Timestamp;
import assignment.simulator.objects.time.TimestampInterpretator;
import assignment.validators.ValidationException;
import assignment.validators.configurations.simulation.ConfigurationValidator;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class SimulationTool {
    public static void main(String[] args) throws FileNotFoundException, ValidationException, JobToQueueMatchingException {
        ConfigurationReader configurationReader = new ConfigurationReader();
        Configuration configuration = configurationReader.readFromPath(args[0]);
        ConfigurationValidator configurationValidator = new ConfigurationValidator();
        configurationValidator.validate(configuration);

        RNGMechanism rngMechanism = new RNGMechanism(configuration.getRngSeed());
        TimestampInterpretator timestampInterpretator = new TimestampInterpretator(configuration.getSimulationTime().getBegin(), configuration.getSimulationTime().getEnd());

        UserSpawner userSpawner = new UserSpawner(0L, configuration.getUserGroupsConfiguration().getUserGroups(), rngMechanism);
        List<User> users = userSpawner.spawn();
        BudgetAnalytics budgetAnalytics = new BudgetAnalytics(users, configuration);
        QueueSpawner queueSpawner = new QueueSpawner(configuration.getQueuesConfiguration(), configuration.getSystemResources(), timestampInterpretator, budgetAnalytics);
        
        List<Queue> queues  = queueSpawner.spawnQueues();

        List<Job> jobs = new ArrayList<>();
        JobSpawner jobSpawner = new JobSpawner(0L, configuration.getJobTypesConfiguration().getJobTypes(), rngMechanism);
        JobToQueueMatcher jobToQueueMatcher = new JobToQueueMatcher(configuration.getQueuesConfiguration());
        Simulator simulator = new Simulator(queues, users, jobs, jobSpawner, jobToQueueMatcher);


        Timestamp from = new Timestamp(timestampInterpretator.getBeginTick());
        Long tickCount = timestampInterpretator.getAmountOfTicks();
        simulator.run(from, tickCount);
    }
}

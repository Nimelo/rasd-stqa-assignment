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
import assignment.simulator.reporting.Report;
import assignment.validators.ValidationException;
import assignment.validators.configurations.simulation.ConfigurationValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
class SimulationToolTest {
    private final String TEST_CONFIGURATION_NAME = "test-simulation.json";
    private static ConfigurationReader configurationReader;

    @BeforeAll
    static void setUp() {
        configurationReader = new ConfigurationReader();
    }
    @Test
    void fullTest() throws ValidationException, JobToQueueMatchingException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String testConfigurationFilePath = classLoader.getResource(TEST_CONFIGURATION_NAME).getPath();

        String json = new String(Files.readAllBytes(Paths.get(testConfigurationFilePath.substring(1))));

        Configuration configuration = configurationReader.readFromJson(json);
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
        Simulator simulator = new Simulator(queues, users, jobs, jobSpawner, jobToQueueMatcher, budgetAnalytics);


        Timestamp from = new Timestamp(timestampInterpretator.getBeginTick());
        Long tickCount = timestampInterpretator.getAmountOfTicks();
        Report report = simulator.run(from, tickCount);

    }
}
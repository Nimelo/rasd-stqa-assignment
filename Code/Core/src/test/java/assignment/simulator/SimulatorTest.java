package assignment.simulator;

import assignment.configurations.simulation.Configuration;
import assignment.io.readers.configurations.simulation.ConfigurationReader;
import assignment.simulator.matchers.exceptions.JobToQueueMatchingException;
import assignment.simulator.reporting.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
class SimulatorTest {
    private static final String CONFIGURATION_FILE_NAME = "test-simulation.json";
    private Configuration configuration;
    @BeforeEach
    void setUp() throws FileNotFoundException {
        String path = getClass().getClassLoader().getResource(CONFIGURATION_FILE_NAME).getPath();
        ConfigurationReader configurationReader = new ConfigurationReader();
        configuration = configurationReader.readFromPath(path);
    }

    @Test
    void run() throws JobToQueueMatchingException {
        Simulator simulator = new Simulator(configuration);
        Report report = simulator.run();
    }

}
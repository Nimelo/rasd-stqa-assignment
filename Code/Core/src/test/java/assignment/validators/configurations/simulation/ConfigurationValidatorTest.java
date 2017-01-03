package assignment.validators.configurations.simulation;

import assignment.configurations.simulation.Configuration;
import assignment.io.readers.configurations.simulation.ConfigurationReader;
import assignment.validators.ValidationException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
class ConfigurationValidatorTest {
    private static ConfigurationValidator configurationValidator = new ConfigurationValidator();
    @Test
    void validate() throws FileNotFoundException, ValidationException {
        String path = getClass().getClassLoader().getResource("test-configuration.json").getPath();
        ConfigurationReader configurationReader = new ConfigurationReader();
        Configuration configuration = configurationReader.readFromPath(path);

        configurationValidator.validate(configuration);
    }

    @Test
    void validateReservationInQueues() {

    }

    @Test
    void validateSingleNode() {

    }

    @Test
    void validateNodesInQueues() {

    }

    @Test
    void validateNodesInJobTypes() {

    }

    @Test
    void validateJobDistribution() {

    }

    @Test
    void validateUserGroupsConfiguration() {

    }

    @Test
    void validateQueuesConfiguration() {

    }

    @Test
    void validateReservedResources() {

    }

    @Test
    void validateMachineOperationalCost() {

    }

    @Test
    void validateSystemResources() {

    }

    @Test
    void validateSimulationTime() {

    }

    @Test
    void validateJobTypesConfiguration() {

    }

    @Test
    void validateRNGSeed() {

    }

}
package assignment;

import assignment.configurations.simulation.Configuration;
import assignment.io.readers.configurations.simulation.ConfigurationReader;
import assignment.simulator.generation.randomization.RNGMechanism;
import assignment.validators.ValidationException;
import assignment.validators.configurations.simulation.ConfigurationValidator;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class SimulationTool {
    public static void main(String[] args) throws FileNotFoundException, ValidationException {
        ConfigurationReader configurationReader = new ConfigurationReader();
        Configuration configuration = configurationReader.readFromPath(args[0]);
        ConfigurationValidator configurationValidator = new ConfigurationValidator();
        configurationValidator.validate(configuration);

        /*
        * Setup RNG mechanism
        * Setup Queues
        * Setup Users
        * Setup JobSpawner
        * Connect to timer
        *
        * */
        RNGMechanism rngMechanism = new RNGMechanism(configuration.getRngSeed());



    }
}

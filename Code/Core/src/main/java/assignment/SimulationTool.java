package assignment;

import assignment.configurations.simulation.Configuration;
import com.google.gson.Gson;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class SimulationTool {
    public static void main(String[] args) {
        long seed = 100L;
        ThreadLocalRandom.current().setSeed(seed);
    }
}

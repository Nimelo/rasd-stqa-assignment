package assignment.io.readers.configurations.simulation;

import assignment.configurations.simulation.Configuration;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class ConfigurationReader {
    public Configuration read(String path) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        Gson gson = new Gson();
        gson.fromJson("dsfsd", Configuration.class);
        return gson.fromJson(br, Configuration.class);
    }
}

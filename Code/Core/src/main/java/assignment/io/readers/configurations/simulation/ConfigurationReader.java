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
    public Configuration readFromJson(String json)  {
        Gson gson = new Gson();
        return gson.fromJson(json, Configuration.class);
    }

    public Configuration readFromPath(String path) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        Gson gson = new Gson();
        return gson.fromJson(br, Configuration.class);
    }
}

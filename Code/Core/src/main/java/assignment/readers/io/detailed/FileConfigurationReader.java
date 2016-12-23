package assignment.readers.io.detailed;

import assignment.readers.io.exceptions.ConfigurationReadException;
import assignment.readers.io.interfaces.IConfigurationReader;
import assignment.configurations.simulation.Configuration;

import java.io.FileInputStream;

/**
 * Created by mrnim on 23-Dec-16.
 */
public class FileConfigurationReader implements IConfigurationReader {
    private final FileInputStream fileInputStream;

    @Override
    public Configuration read() throws ConfigurationReadException {
        return null;
    }

    public FileConfigurationReader(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }
}

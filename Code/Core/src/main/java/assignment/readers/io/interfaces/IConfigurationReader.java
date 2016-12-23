package assignment.readers.io.interfaces;

import assignment.configurations.simulation.Configuration;
import assignment.readers.io.exceptions.ConfigurationReadException;

/**
 * Created by mrnim on 23-Dec-16.
 */
public interface IConfigurationReader {
    Configuration read() throws ConfigurationReadException;
}

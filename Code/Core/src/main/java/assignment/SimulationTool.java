package assignment;

import assignment.configurations.simulation.Configuration;
import assignment.io.readers.configurations.simulation.ConfigurationReader;
import assignment.simulator.Simulator;
import assignment.simulator.matchers.exceptions.JobToQueueMatchingException;
import assignment.simulator.reporting.Report;
import com.google.gson.Gson;
import org.apache.commons.cli.*;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class SimulationTool {
    private static final String TEMPLATE_RESOURCES_FILE = "template.json";
    private static final String REPORT_PATH_ARGUMENT = "report";
    private static final String CONFIGURATION_PATH_ARGUMENT = "configuration";
    private static final String HELP_ARGUMENT = "help";
    private static final String TEMPLATE = "template";

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(REPORT_PATH_ARGUMENT, true, "Specifies path to generated report.");
        options.addOption(CONFIGURATION_PATH_ARGUMENT, true, "Specifies path to configuration file.");
        options.addOption(HELP_ARGUMENT, false, "Displays help menu.");
        options.addOption(TEMPLATE, true, "Generates template at given path.");

        CommandLineParser parser = new BasicParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            if (commandLine.hasOption(REPORT_PATH_ARGUMENT) && commandLine.hasOption(CONFIGURATION_PATH_ARGUMENT)) {
                String configurationPath = commandLine.getOptionValue(CONFIGURATION_PATH_ARGUMENT);
                String reportPath = commandLine.getOptionValue(REPORT_PATH_ARGUMENT);
                if (Files.exists(Paths.get(configurationPath))) {
                    if (Files.isDirectory(Paths.get(reportPath))) {
                        Configuration configuration = new ConfigurationReader().readFromPath(configurationPath);
                        Simulator simulator = new Simulator(configuration);
                        Report report = simulator.run();
                        Files.write(Paths.get(reportPath, "report.json"), new Gson().toJson(report).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                        System.out.print(String.format("Successfully saved report to the file: %s", Paths.get(reportPath, "report.json")));
                    } else {
                        System.out.println("Incorrect path to report directory.");
                    }
                } else {
                    System.out.println("Incorrect path to configuration file.");
                }
            } else if (commandLine.hasOption(TEMPLATE)) {
                String templatePath = commandLine.getOptionValue(TEMPLATE);
                if (Files.isDirectory(Paths.get(templatePath))) {
                    byte[] bytes = IOUtils.toByteArray(SimulationTool.class.getClassLoader().getResourceAsStream(TEMPLATE_RESOURCES_FILE));
                    Files.write(Paths.get(templatePath, "template.json"), bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                    System.out.print(String.format("Successfully saved report to the file: %s", Paths.get(templatePath, "template.json")));
                } else {
                    System.out.println("Incorrect path to template directory.");
                }
            } else {
                printHelp(options);
            }
        } catch (ParseException e) {
            printHelp(options);
        } catch (FileNotFoundException e) {
            System.out.println("Incorrect path to configuration file.");
        } catch (JobToQueueMatchingException e) {
            System.out.println("Job created in simulation could not be matched with queues. Please check configuration.");
        } catch (IOException e) {
           System.out.println("Error occurred while writing to file.");
        }
    }

    public static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "simulator", options);
    }



}

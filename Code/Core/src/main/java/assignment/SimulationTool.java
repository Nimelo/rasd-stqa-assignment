package assignment;

import assignment.configurations.simulation.Configuration;
import com.google.gson.Gson;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class SimulationTool {
    public static void main(String[] args) {
        Gson gson = new Gson();
        Configuration configuration = gson.fromJson("{\n" +
                "\t\"rngSeed\" : 1,\n" +
                "\t\"jobDistribution\" : 0.5,\n" +
                "\t\"machineOperationalCost\" : 400.34,\n" +
                "\t\"simulationTime\" : {\n" +
                "\t\t\"begin\" : \"2016-11-17\",\n" +
                "\t\t\"end\" : \"2016-11-31\"\n" +
                "\t},\n" +
                "\t\"systemResources\" : {\n" +
                "\t\t\"nodes\" : [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"name\" : \"NODE_S\",\n" +
                "\t\t\t\t\"amount\" : 8,\n" +
                "\t\t\t\t\"amountOfCores\" : 32,\n" +
                "\t\t\t\t\"price\" : 12.4\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t\"jobTypesConfiguration\" : {\n" +
                "\t\t\"jobTypes\" : [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"name\" : \"SmallJob\",\n" +
                "\t\t\t\t\"probabilityOfJob\" : 0.5,\n" +
                "\t\t\t\t\"tuples\" : [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"nodeType\" : \"NODE_S\",\n" +
                "\t\t\t\t\t\t\"probabilityOfOccurrence\" : 0.7,\n" +
                "\t\t\t\t\t\t\"maximumAmountOfNodes\" : 3\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t]\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t\"queuesConfiguration\" : {\n" +
                "\t\t\"queues\" : [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"name\" : \"FastQueue\",\n" +
                "\t\t\t\t\"description\" : \"Queue for very fast jobs.\",\n" +
                "\t\t\t\t\"maximumExecutionTime\" : 60,\n" +
                "\t\t\t\t\"priceFactor\" : 1.0,\n" +
                "\t\t\t\t\"reservedResources\" : [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"nodeType\" : \"NODE_S\",\n" +
                "\t\t\t\t\t\t\"amount\" : 4\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"availabilityTime\" : {\n" +
                "\t\t\t\t\t\"begin\" : {\n" +
                "\t\t\t\t\t\t\"dayOfWeek\" : \"MONDAY\",\n" +
                "\t\t\t\t\t\t\"hours\" : 7,\n" +
                "\t\t\t\t\t\t\"minutes\" : 0\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"end\" : {\n" +
                "\t\t\t\t\t\t\"dayOfWeek\" : \"FRIDAY\",\n" +
                "\t\t\t\t\t\t\"hours\" : 7,\n" +
                "\t\t\t\t\t\t\"minutes\" : 0\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t\"userGroupsConfiguration\" : {\n" +
                "\t\t\"userGroups\" : [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"amountOfMembers\" : 10,\n" +
                "\t\t\t\t\"minBudget\" : 3.14,\n" +
                "\t\t\t\t\"maxBudget\" : 754.43,\n" +
                "\t\t\t\t\"maxNumberOfConcurrentJobsPerUser\" : 2,\n" +
                "\t\t\t\t\"maxUtilizedCoresPerUser\" : 10\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}", Configuration.class);
    }
}

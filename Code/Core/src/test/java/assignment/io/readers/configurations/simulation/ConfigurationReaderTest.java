package assignment.io.readers.configurations.simulation;

import assignment.configurations.simulation.*;
import assignment.configurations.simulation.objects.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
class ConfigurationReaderTest {
    private final String TEST_CONFIGURATION_NAME = "test-configuration.json";
    private static ConfigurationReader configurationReader;

    @BeforeAll
    static void setUp() {
        configurationReader = new ConfigurationReader();
    }

    @Test
    void readFromJson() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String testConfigurationFilePath = classLoader.getResource(TEST_CONFIGURATION_NAME).getPath();

        String json = new String(Files.readAllBytes(Paths.get(testConfigurationFilePath.substring(1))));

        Configuration configuration = configurationReader.readFromJson(json);
        validateConfiguration(configuration);
    }

    @Test
    void readFromPath() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        String testConfigurationFilePath = classLoader.getResource(TEST_CONFIGURATION_NAME).getPath();
        Configuration configuration = configurationReader.readFromPath(testConfigurationFilePath);
        validateConfiguration(configuration);
    }

    private void validateConfiguration(Configuration configuration){
        assertEquals(new Long(1L), configuration.getRngSeed());
        assertEquals(new BigDecimal(400.01).doubleValue(), configuration.getMachineOperationalCost().doubleValue(), 0.01);
        assertEquals(new Long(1L), configuration.getSimulationTime().getNumberOfWeeks());
        assertSystemResources(configuration.getSystemResources());
        assertJobTypesConfiguration(configuration.getJobTypesConfiguration());
        assertQueuesConfiguration(configuration.getQueuesConfiguration());
        assertUserGroupsConfiguration(configuration.getUserGroupsConfiguration());
    }

    private void assertUserGroupsConfiguration(UserGroupsConfiguration userGroupsConfiguration) {
        assertEquals(1, userGroupsConfiguration.getUserGroups().size());
        UserGroup userGroup = userGroupsConfiguration.getUserGroups().get(0);
        assertEquals(new Long(10), userGroup.getAmountOfMembers());
        assertEquals(new BigDecimal(3.14).doubleValue(), userGroup.getBudget().doubleValue(), 0.01);
        assertEquals(new Double(0.5), userGroup.getJobDistributionLambda());
        assertEquals(new Double(0.5), userGroup.getRequestSizeDistributionLambda());
    }

    private void assertQueuesConfiguration(QueuesConfiguration queuesConfiguration) {
        assertEquals(1, queuesConfiguration.getQueues().size());
        QueueProperties queueProperties = queuesConfiguration.getQueues().get(0);
        assertEquals("FastQueue", queueProperties.getName());
        assertEquals(new Long(60), queueProperties.getMaximumExecutionTime());
        assertEquals(new Double(1.0), queueProperties.getPriceFactor(), 0.01);
        assertEquals(1, queueProperties.getReservedResources().size());
        ReservedResource reservedResource = queueProperties.getReservedResources().get(0);
        assertEquals("NODE_S", reservedResource.getNodeType());
        assertEquals(new Long(4), reservedResource.getAmount());
        assertDayOfWeekTime(DayOfWeek.MONDAY, 7L, 0L, queueProperties.getAvailabilityTime().getBegin());
        assertDayOfWeekTime(DayOfWeek.FRIDAY, 7L, 0L, queueProperties.getAvailabilityTime().getEnd());

        ConstraintResource constraintResources = queueProperties.getConstraintResources().get(0);

        assertEquals("NODE_S", constraintResources.getNodeType());
        assertEquals(new Long(4L), constraintResources.getAmountOfCores());
    }

    private void assertDayOfWeekTime(DayOfWeek dayOfWeek, Long hours, Long minutes, ShiftTime time) {
        assertEquals(dayOfWeek, time.getDayOfWeek());
        assertEquals(hours, time.getHours());
        assertEquals(minutes, time.getMinutes());
    }

    private void assertJobTypesConfiguration(JobTypesConfiguration jobTypesConfiguration) {
        assertEquals(1, jobTypesConfiguration.getJobTypes().size());
        JobType jobType = jobTypesConfiguration.getJobTypes().get(0);
        assertEquals(new Long(45L), jobType.getMinExecutionTime());
        assertEquals(new Long(120L), jobType.getMaxExecutionTime());
        assertEquals("SmallJob", jobType.getName());
        assertEquals(new Double(1), jobType.getProbabilityOfJob(), 0.01);
        assertEquals(1, jobType.getTuples().size());
        JobTypeTuple jobTypeTuple = jobType.getTuples().get(0);
        assertEquals("NODE_S", jobTypeTuple.getNodeType());
        assertEquals(new Double(0.7), jobTypeTuple.getProbabilityOfOccurrence(), 0.01);
        assertEquals(new Long(3), jobTypeTuple.getMaximumAmountOfCores());
    }

    private void assertSystemResources(SystemResources systemResources) {
        assertEquals(1, systemResources.getNodes().size());
        Node node = systemResources.getNodes().get(0);
        assertEquals("NODE_S", node.getName());
        assertEquals(new Long(8), node.getAmount());
        assertEquals(new Long(32), node.getAmountOfCores());
        assertEquals(new BigDecimal(12.4).doubleValue(), node.getPrice().doubleValue(), 0.01);
    }

    private void assertDate(int year, int month, int dayOfMonth, Date begin) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(begin);

        assertEquals(year, instance.get(Calendar.YEAR));
        assertEquals(month - 1, instance.get(Calendar.MONTH));
        assertEquals(dayOfMonth, instance.get(Calendar.DAY_OF_MONTH));
    }
}
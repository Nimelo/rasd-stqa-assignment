package assignment.configurations.simulation;

import assignment.configurations.simulation.exceptions.ValidationException;
import assignment.configurations.simulation.interfaces.IValidate;
import assignment.configurations.simulation.objects.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class Configuration{
    private Long rngSeed;
    private JobTypesConfiguration jobTypesConfiguration;
    private SimulationTime simulationTime;
    private SystemResources systemResources;
    private BigDecimal machineOperationalCost;
    private QueuesConfiguration queuesConfiguration;
    private UserGroupsConfiguration userGroupsConfiguration;
    private Double jobDistribution;

    public Long getRngSeed() {
        return rngSeed;
    }

    public JobTypesConfiguration getJobTypesConfiguration() {
        return jobTypesConfiguration;
    }

    public SimulationTime getSimulationTime() {
        return simulationTime;
    }

    public SystemResources getSystemResources() {
        return systemResources;
    }

    public BigDecimal getMachineOperationalCost() {
        return machineOperationalCost;
    }

    public QueuesConfiguration getQueuesConfiguration() {
        return queuesConfiguration;
    }

    public UserGroupsConfiguration getUserGroupsConfiguration() {
        return userGroupsConfiguration;
    }

    public Double getJobDistribution() {
        return jobDistribution;
    }

}

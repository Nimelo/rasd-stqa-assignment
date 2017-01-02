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
public class Configuration implements IValidate{
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

    @Override
    public void validate() throws ValidationException {
        jobTypesConfiguration.validate();
        simulationTime.validate();
        systemResources.validate();
        queuesConfiguration.validate();
        userGroupsConfiguration.validate();
        if (machineOperationalCost.doubleValue() < 0L)
            throw new ValidationException("Operational cost of machine is negative.", BigDecimal.class);

        this.validateNodesInJobTypes();
        this.validateNodesInQueues();

    }

    private void validateNodesInQueues() throws ValidationException {
        for (QueueProperties queueProperties : queuesConfiguration.getQueues()) {
            List<String> types = queueProperties.getReservedResources().stream()
                    .map(ReservedResources::getNodeType).collect(Collectors.toList());
            checkIfNodesExist(types);
        }

        //TODO: What if there is not sufficient amount of nodes?
    }

    private void validateNodesInJobTypes() throws ValidationException {
        for (JobType jobType : jobTypesConfiguration.getJobTypes()) {
            List<String> types = jobType.getTuples().stream().map(JobTypeTuple::getType).collect(Collectors.toList());
            checkIfNodesExist(types);
        }
    }

    private void checkIfNodesExist(List<String> types) throws ValidationException {
        for (String type : types) {
            boolean anyMatch = this.systemResources.getNodes().stream()
                    .map(Node::getName)
                    .anyMatch(x -> x.equals(type));
            if (!anyMatch)
                throw new ValidationException("Not matching node type.", String.class);
        }

    }
}

package assignment.validators.configurations.simulation;

import assignment.configurations.simulation.*;
import assignment.configurations.simulation.objects.*;
import assignment.validators.BasicValidator;
import assignment.validators.ValidationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class ConfigurationValidator {
    public void validate(Configuration configuration) throws ValidationException {
        this.validateRNGSeed(configuration.getRngSeed());
        this.validateJobTypesConfiguration(configuration.getJobTypesConfiguration());
        this.validateSimulationTime(configuration.getSimulationTime());
        this.validateSystemResources(configuration.getSystemResources());
        this.validateMachineOperationalCost(configuration.getMachineOperationalCost());
        this.validateQueuesConfiguration(configuration.getQueuesConfiguration());
        this.validateUserGroupsConfiguration(configuration.getUserGroupsConfiguration());
        this.validateJobDistribution(configuration.getJobDistribution());

        //TODO: Additional validations
        /*
        * JobTypes -> Nodes
        * Queues -> Nodes
        * Resources -> Queues
        * */
    }

    public void validateJobDistribution(Double jobDistribution) throws ValidationException {
        String context = "jobDistribution";
        BasicValidator.shouldNotBeNull(jobDistribution, context);
    }

    public void validateUserGroupsConfiguration(UserGroupsConfiguration userGroupsConfiguration) throws ValidationException {
        String context = "userGroupsConfiguration.userGroups";
        List<UserGroup> userGroups = userGroupsConfiguration.getUserGroups();

        BasicValidator.shouldNotBeNull(userGroups, context);

        for (int i = 0; i < userGroups.size(); i++) {
            String currentContext = String.format("%s.[%d].", context, i);
            UserGroup userGroup = userGroups.get(i);

            BasicValidator.shouldBeGreaterThan(0L, userGroup.getAmountOfMembers(), currentContext + "amountOfMembers");
            BasicValidator.shouldBeGreaterThan(new BigDecimal(0L), userGroup.getMinBudget(), currentContext + "minBudget");
            BasicValidator.shouldBeGreaterThan(userGroup.getMinBudget(), userGroup.getMaxBudget(), currentContext + "maxBudget");

            //TODO Should last two fields be implemented? TBD
        }

    }

    public void validateQueuesConfiguration(QueuesConfiguration queuesConfiguration) throws ValidationException {
        String context = "queuesConfiguration.queues";
        List<QueueProperties> queues = queuesConfiguration.getQueues();
        BasicValidator.shouldContainElements(queues, context);
        BasicValidator.shouldBeUnique(queues.stream().map(x -> x.getName()).collect(Collectors.toList()), "Name of queues should be unique.");

        for (int i = 0; i < queues.size(); i++) {
            String currentContext = String.format("%s.[%d].", context, i);
            QueueProperties queueProperties = queues.get(i);

            BasicValidator.shouldBeGreaterThan(0L, queueProperties.getMaximumExecutionTime(), currentContext + "maximumExecutionTime");
            BasicValidator.shouldBeGreaterThan(0D, queueProperties.getPriceFactor(), currentContext + "priceFactor");
            this.validateReservedResources(queueProperties.getReservedResources(), currentContext + "reservedResources");

            BasicValidator.shouldBeInRange(1L, 24L, queueProperties.getAvailabilityTime().getBegin().getHours(), currentContext + "availabilityTime.begin.hours");
            BasicValidator.shouldBeInRange(0L, 60L, queueProperties.getAvailabilityTime().getBegin().getHours(), currentContext + "availabilityTime.begin.minutes");
            BasicValidator.shouldBeInRange(1L, 24L, queueProperties.getAvailabilityTime().getBegin().getHours(), currentContext + "availabilityTime.end.hours");
            BasicValidator.shouldBeInRange(0L, 60L, queueProperties.getAvailabilityTime().getBegin().getHours(), currentContext + "availabilityTime.end.minutes");
        }
    }

    public void validateReservedResources(List<ReservedResource> reservedResources, String context) throws ValidationException {
        BasicValidator.shouldContainElements(reservedResources, context);

        for (int i = 0; i < reservedResources.size(); i++) {
            String currentContext = String.format("%s.[%d].", context, i);
            ReservedResource reservedResource = reservedResources.get(i);
            BasicValidator.shouldNotBeNull(reservedResource.getNodeType(), currentContext + ".nodeType");
            BasicValidator.shouldBeInRange(0L, Long.MAX_VALUE, reservedResource.getAmount(), currentContext + ".amount");
        }
    }

    public void validateMachineOperationalCost(BigDecimal machineOperationalCost) throws ValidationException {
        String context = "machineOperationalCost";
        BasicValidator.shouldBeGreaterThan(new BigDecimal(0L), machineOperationalCost, context);
    }

    public void validateSystemResources(SystemResources systemResources) throws ValidationException {
        String context = "systemResources.nodes";
        List<Node> nodes = systemResources.getNodes();
        BasicValidator.shouldContainElements(nodes, context);
        BasicValidator.shouldBeUnique(nodes.stream().map(x -> x.getName()).collect(Collectors.toList()), "Name of nodes should be unique.");

        for (int i = 0; i < nodes.size(); i++) {
            String currentContext = String.format("%s.[%d].", context, i);
            Node node = nodes.get(i);
            BasicValidator.shouldNotBeNull(node.getName(), currentContext + "name");
            BasicValidator.shouldBeGreaterThan(0L, node.getAmount(), currentContext + "amount");
            BasicValidator.shouldBeGreaterThan(0L, node.getAmountOfCores(), currentContext + "amountOfCores");
            BasicValidator.shouldBeGreaterThan(new BigDecimal(0L), node.getPrice(), currentContext + "price");
        }
    }

    public void validateSimulationTime(SimulationTime simulationTime) throws ValidationException {
        String context = "simulationTime.";
        BasicValidator.shouldNotBeNull(simulationTime.getBegin(), context + "begin");
        BasicValidator.shouldNotBeNull(simulationTime.getEnd(), context + "end");
        BasicValidator.shouldBeTrue(simulationTime.getEnd().after(simulationTime.getBegin()), "End of simulation should be after beginning.");
    }

    public void validateJobTypesConfiguration(JobTypesConfiguration jobTypesConfiguration) throws ValidationException {
        String context = "jobTypesConfiguration.jobTypes";
        List<JobType> jobTypes = jobTypesConfiguration.getJobTypes();
        BasicValidator.shouldContainElements(jobTypes, context);
        BasicValidator.shouldBeUnique(jobTypes.stream().map(x -> x.getName()).collect(Collectors.toList()), "Name of jobs should be unique.");
        BasicValidator.shouldBeEqual(jobTypes.stream().mapToDouble(x -> x.getProbabilityOfJob()).sum(), 1.0, 0.01, "Probability of all jobs should be equal 1.");

        for (int i = 0; i < jobTypes.size(); i++) {
            List<JobTypeTuple> jobTypeTuples = jobTypes.get(i).getTuples();
            String currentContext = String.format("%s.[%d].", context, i);
            BasicValidator.shouldContainElements(jobTypeTuples, currentContext);

            for (int j = 0; j < jobTypeTuples.size(); j++) {
                String ctx = String.format("%s.jobTypeTuples.[%d].", currentContext, j);
                JobTypeTuple jobTypeTuple = jobTypeTuples.get(j);
                BasicValidator.shouldNotBeNull(jobTypeTuple.getNodeType(), ctx + "getNodeType");
                BasicValidator.shouldBeInRange(0.0, 1.0, jobTypeTuple.getProbabilityOfOccurrence(), ctx + "getProbabilityOfOccurrence");
                BasicValidator.shouldBeInRange(0L, Long.MAX_VALUE, jobTypeTuple.getMaximumAmountOfNodes(), ctx + "getMaximumAmountOfNodes");
            }
        }

    }

    public void validateRNGSeed(Long rngSeed) throws ValidationException {
        String context = "rngSeed";
        BasicValidator.shouldBeInRange(Long.MIN_VALUE, Long.MAX_VALUE, rngSeed, context);
    }
}

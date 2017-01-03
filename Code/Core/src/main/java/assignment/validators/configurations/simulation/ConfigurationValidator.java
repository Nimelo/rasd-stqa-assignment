package assignment.validators.configurations.simulation;

import assignment.configurations.simulation.*;
import assignment.configurations.simulation.objects.*;
import assignment.validators.BasicValidator;
import assignment.validators.ValidationException;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

        this.validateNodesInJobTypes(configuration.getJobTypesConfiguration(), configuration.getSystemResources());
        this.validateNodesInQueues(configuration.getQueuesConfiguration(), configuration.getSystemResources());
        this.validateReservationInQueues(configuration.getQueuesConfiguration(), configuration.getSystemResources());

    }

    public void validateReservationInQueues(QueuesConfiguration queuesConfiguration, SystemResources systemResources) throws ValidationException {
        List<QueueProperties> queues = queuesConfiguration.getQueues();
        List<ReservedResource> reservationList = queues.stream().flatMap(l -> l.getReservedResources().stream()).collect(Collectors.toList());
        Map<String, List<ReservedResource>> reservationMap = reservationList.stream().collect(Collectors.groupingBy(x -> x.getNodeType()));

        Iterator<Map.Entry<String, List<ReservedResource>>> iterator = reservationMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<ReservedResource>> next = iterator.next();
            String key = next.getKey();
            List<ReservedResource> value = next.getValue();
            this.validateSingleNode(key, value.stream().mapToLong(x -> x.getAmount()).sum(), systemResources.getNodes());
        }
    }

    public void validateSingleNode(String name, Long amount, List<Node> nodes) throws ValidationException {
        for (Node node : nodes) {
            if (node.getName().equals(name)) {
                if (node.getAmount() < amount) {
                    throw new ValidationException(
                            String.format("Not sufficient amount (%d) of node %s in system resources - %d.", amount, name, node.getAmount())
                            , "Queues requires more resources than system contains.");
                }
            }
        }
    }

    public void validateNodesInQueues(QueuesConfiguration queuesConfiguration, SystemResources systemResources) throws ValidationException {
        String context = "queuesConfiguration.queues";
        List<QueueProperties> queues = queuesConfiguration.getQueues();
        List<String> names = systemResources.getNodes().stream().map(x -> x.getName()).collect(Collectors.toList());

        for (int i = 0; i < queues.size(); i++) {
            QueueProperties queueProperties = queues.get(i);
            List<ReservedResource> reservedResources = queueProperties.getReservedResources();

            for (int j = 0; j < reservedResources.size(); j++) {
                String currentContext = String.format("%s.[%d].reservedResources[%d].nodeType", context, i, j);
                ReservedResource reservedResource = reservedResources.get(j);

                BasicValidator.shouldBeOneOf(names, reservedResource.getNodeType(), context);
            }

            List<ConstraintResource> constraintResources = queueProperties.getConstraintResources();

            for (int j = 0; j < constraintResources.size(); j++) {
                String currentContext = String.format("%s.[%d].constraintResources[%d].nodeType", context, i, j);
                ConstraintResource constraintResource = constraintResources.get(j);

                BasicValidator.shouldBeOneOf(names, constraintResource.getNodeType(), context);
            }
        }
    }

    public void validateNodesInJobTypes(JobTypesConfiguration jobTypesConfiguration, SystemResources systemResources) throws ValidationException {
        String context = "jobTypesConfiguration.jobTypes";
        List<String> names = systemResources.getNodes().stream().map(x -> x.getName()).collect(Collectors.toList());
        List<JobType> jobTypes = jobTypesConfiguration.getJobTypes();

        for (int i = 0; i < jobTypes.size(); i++) {
            JobType jobType = jobTypes.get(i);
            List<String> nodeNames = jobType.getTuples().stream().map(x -> x.getNodeType()).collect(Collectors.toList());

            for (int j = 0; j < nodeNames.size(); j++) {
                String currentContext = String.format("%s.[%d].tuples[%d].nodeType", context, i, j);
                String nodeName = nodeNames.get(j);

                BasicValidator.shouldBeOneOf(names, nodeName, currentContext);
            }
        }
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
            BasicValidator.shouldNotBeNull(userGroup.getMaxBudget(), currentContext + "maxBudget");
            if (userGroup.getMaxBudget().compareTo(userGroup.getMinBudget()) != 0)
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
            this.validateConstraintResources(queueProperties.getConstraintResources(), currentContext + "constraintResources");

            BasicValidator.shouldBeInRange(1L, 24L, queueProperties.getAvailabilityTime().getBegin().getHours(), currentContext + "availabilityTime.begin.hours");
            BasicValidator.shouldBeInRange(0L, 60L, queueProperties.getAvailabilityTime().getBegin().getHours(), currentContext + "availabilityTime.begin.minutes");
            BasicValidator.shouldBeInRange(1L, 24L, queueProperties.getAvailabilityTime().getBegin().getHours(), currentContext + "availabilityTime.end.hours");
            BasicValidator.shouldBeInRange(0L, 60L, queueProperties.getAvailabilityTime().getBegin().getHours(), currentContext + "availabilityTime.end.minutes");
        }
    }

    public void validateConstraintResources(List<ConstraintResource> constraintResources, String context) throws ValidationException {
        BasicValidator.shouldNotBeNull(constraintResources, context);

        for (int i = 0; i < constraintResources.size(); i++) {
            String currentContext = String.format("%s.[%d]", context, i);
            ConstraintResource constraintResource = constraintResources.get(i);
            BasicValidator.shouldNotBeNull(constraintResource.getNodeType(), currentContext + ".nodeType");
            BasicValidator.shouldBeInRange(0L, Long.MAX_VALUE, constraintResource.getAmount(), currentContext + ".amount");
            BasicValidator.shouldBeInRange(0L, Long.MAX_VALUE, constraintResource.getAmountOfCores(), currentContext + ".amountOfCores");
        }
    }

    public void validateReservedResources(List<ReservedResource> reservedResources, String context) throws ValidationException {
        BasicValidator.shouldContainElements(reservedResources, context);

        for (int i = 0; i < reservedResources.size(); i++) {
            String currentContext = String.format("%s.[%d]", context, i);
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

        for (int i = 0; i < jobTypes.size(); i++) {
            String currentContext = String.format("%s[%d].", context, i);
            JobType jobType = jobTypes.get(i);
            BasicValidator.shouldBeGreaterThan(0L, jobType.getMinExecutionTime(), currentContext + "minExecutionTime");
            BasicValidator.shouldNotBeNull(jobType.getMaxExecutionTime(), currentContext + "maxExecutionTime");
            if (jobType.getMaxExecutionTime().compareTo(jobType.getMinExecutionTime()) != 0)
                BasicValidator.shouldBeGreaterThan(jobType.getMinExecutionTime(), jobType.getMaxExecutionTime(), currentContext + "maxExecutionTime");
            BasicValidator.shouldBeGreaterThan(0D, jobType.getProbabilityOfJob(), currentContext + "probabilityOfJob");

            List<JobTypeTuple> jobTypeTuples = jobTypes.get(i).getTuples();
            BasicValidator.shouldContainElements(jobTypeTuples, currentContext + "tuples");
            for (int j = 0; j < jobTypeTuples.size(); j++) {
                String ctx = String.format("%sjobTypeTuples.[%d].", currentContext, j);
                JobTypeTuple jobTypeTuple = jobTypeTuples.get(j);
                BasicValidator.shouldNotBeNull(jobTypeTuple.getNodeType(), ctx + "getNodeType");
                BasicValidator.shouldBeInRange(0.0, 1.0, jobTypeTuple.getProbabilityOfOccurrence(), ctx + "getProbabilityOfOccurrence");
                BasicValidator.shouldBeInRange(0L, Long.MAX_VALUE, jobTypeTuple.getMaximumAmountOfNodes(), ctx + "getMaximumAmountOfNodes");
            }
        }
        BasicValidator.shouldBeUnique(jobTypes.stream().map(x -> x.getName()).collect(Collectors.toList()), "Name of jobs should be unique.");
        BasicValidator.shouldBeEqual(jobTypes.stream().mapToDouble(x -> x.getProbabilityOfJob()).sum(), 1.0, 0.01, "Probability of all jobs should be equal 1.");
    }

    public void validateRNGSeed(Long rngSeed) throws ValidationException {
        String context = "rngSeed";
        BasicValidator.shouldBeInRange(Long.MIN_VALUE, Long.MAX_VALUE, rngSeed, context);
    }
}

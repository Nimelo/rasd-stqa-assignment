package assignment.simulation;

import assignment.simulation.objects.JobClass;
import assignment.simulation.objects.User;

import java.math.BigDecimal;

/**
 * Created by mrnim on 23-Dec-16.
 */
public class Configuration {
    private Integer numberOfNodes;
    private Integer numberOfCoresPerNode;
    private BigDecimal operationalCostForCorePerHour;
    private User[] users;
    private JobClass[] jobClasses;

    public Configuration(Integer numberOfNodes, Integer numberOfCoresPerNode, BigDecimal operationalCostForCorePerHour, User[] users, JobClass[] jobClasses) {
        this.numberOfNodes = numberOfNodes;
        this.numberOfCoresPerNode = numberOfCoresPerNode;
        this.operationalCostForCorePerHour = operationalCostForCorePerHour;
        this.users = users;
        this.jobClasses = jobClasses;
    }

    public Integer getNumberOfNodes() {
        return numberOfNodes;
    }

    public Integer getNumberOfCoresPerNode() {
        return numberOfCoresPerNode;
    }

    public BigDecimal getOperationalCostForCorePerHour() {
        return operationalCostForCorePerHour;
    }

    public User[] getUsers() {
        return users;
    }

    public JobClass[] getJobClasses() {
        return jobClasses;
    }
}

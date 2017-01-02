package assignment.configurations.simulation.objects;

import java.math.BigDecimal;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class UserGroup{
    private Long amountOfMembers;
    private BigDecimal minBudget;
    private BigDecimal maxBudget;
    private Long maxNumberOfConcurrentJobsPerUser;
    private Long maxUtilizedCoresPerUser;

    public Long getAmountOfMembers() {
        return amountOfMembers;
    }

    public void setAmountOfMembers(Long amountOfMembers) {
        this.amountOfMembers = amountOfMembers;
    }

    public BigDecimal getMinBudget() {
        return minBudget;
    }

    public void setMinBudget(BigDecimal minBudget) {
        this.minBudget = minBudget;
    }

    public BigDecimal getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(BigDecimal maxBudget) {
        this.maxBudget = maxBudget;
    }

    public Long getMaxNumberOfConcurrentJobsPerUser() {
        return maxNumberOfConcurrentJobsPerUser;
    }

    public void setMaxNumberOfConcurrentJobsPerUser(Long maxNumberOfConcurrentJobsPerUser) {
        this.maxNumberOfConcurrentJobsPerUser = maxNumberOfConcurrentJobsPerUser;
    }

    public Long getMaxUtilizedCoresPerUser() {
        return maxUtilizedCoresPerUser;
    }

    public void setMaxUtilizedCoresPerUser(Long maxUtilizedCoresPerUser) {
        this.maxUtilizedCoresPerUser = maxUtilizedCoresPerUser;
    }
}

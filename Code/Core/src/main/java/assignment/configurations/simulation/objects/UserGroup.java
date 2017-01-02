package assignment.configurations.simulation.objects;

import assignment.configurations.simulation.exceptions.ValidationException;
import assignment.configurations.simulation.interfaces.IValidate;

import java.math.BigDecimal;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class UserGroup implements IValidate{
    private Long amountOfMembers;
    private BigDecimal minBudget;
    private BigDecimal maxBudget;
    private Long maxNumberOfConcurrentJobsPerUser;
    private Long maxUtilizedCoresPerUser;

    @Override
    public void validate() throws ValidationException {
        if (amountOfMembers < 0L)
            throw new ValidationException("Negative amount of members.", UserGroup.class);
        if (minBudget.doubleValue() < 0L)
            throw new ValidationException("Negative min budget.", UserGroup.class);
        if (maxBudget.doubleValue() < 0L)
            throw new ValidationException("Negative max budget.", UserGroup.class);
        //TODO: Shall I implement this?
        /*
        if (maxNumberOfConcurrentJobsPerUser < 0L)
            throw new ValidationException("Negative amount of members.", UserGroup.class);
        if (amountOfMembers < 0L)
            throw new ValidationException("Negative amount of members.", UserGroup.class);
         */
    }

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

package assignment.configurations.simulation.objects;

import java.math.BigDecimal;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class UserGroup{
    private Long amountOfMembers;
    private BigDecimal budget;
    private Double jobDistributionLambda;
    private Double requestSizeDistributionLambda;

    public Long getAmountOfMembers() {
        return amountOfMembers;
    }

    public void setAmountOfMembers(Long amountOfMembers) {
        this.amountOfMembers = amountOfMembers;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Double getJobDistributionLambda() {
        return jobDistributionLambda;
    }

    public void setJobDistributionLambda(Double jobDistributionLambda) {
        this.jobDistributionLambda = jobDistributionLambda;
    }

    public Double getRequestSizeDistributionLambda() {
        return requestSizeDistributionLambda;
    }

    public void setRequestSizeDistributionLambda(Double requestSizeDistributionLambda) {
        this.requestSizeDistributionLambda = requestSizeDistributionLambda;
    }
}

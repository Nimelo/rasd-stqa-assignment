package assignment.simulator.objects;

import java.math.BigDecimal;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class User {
    private Long id;
    private BigDecimal budget;
    private Double jobDistributionLambda;
    private Double requestSizeLambda;

    public User(Long id, BigDecimal budget, Double jobDistributionLambda, Double requestSizeLambda) {
        this.id = id;
        this.budget = budget;
        this.jobDistributionLambda = jobDistributionLambda;
        this.requestSizeLambda = requestSizeLambda;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public Double getJobDistributionLambda() {
        return jobDistributionLambda;
    }

    public Double getRequestSizeLambda() {
        return requestSizeLambda;
    }
}

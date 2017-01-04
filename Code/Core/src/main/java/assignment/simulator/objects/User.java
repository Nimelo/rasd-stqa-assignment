package assignment.simulator.objects;

import assignment.simulator.objects.time.Timestamp;

import java.math.BigDecimal;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class User {
    private Long id;
    private BigDecimal budget;
    private Double jobDistributionLambda;
    private Double requestSizeLambda;
    private Timestamp nextJobSubmission;

    public User(Long id, BigDecimal budget, Double jobDistributionLambda, Double requestSizeLambda, Timestamp nextJobSubmission) {
        this.id = id;
        this.budget = budget;
        this.jobDistributionLambda = jobDistributionLambda;
        this.requestSizeLambda = requestSizeLambda;
        this.nextJobSubmission = nextJobSubmission;
    }

    public void decreaseBudget(BigDecimal amount) {
        this.budget = this.budget.subtract(amount);
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

    public Timestamp getNextJobSubmission() {
        return nextJobSubmission;
    }

    public void setNextJobSubmission(Timestamp nextJobSubmission) {
        this.nextJobSubmission = nextJobSubmission;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}

package assignment.simulator.generation.distribution;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class ExponentialProbabilityDistribution {
    public Double lambda;

    public ExponentialProbabilityDistribution(Double lambda) {
        this.lambda = lambda;
    }

    public Double get(Double x) {
        return lambda * Math.exp(-lambda * x);
    }

    public Long get(Long x) {
        return (long)(double)get((double) x);
    }
}

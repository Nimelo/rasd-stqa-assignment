package assignment.configurations.simulation.objects;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class JobTypeTuple {
    private String nodeType;
    private Double probabilityOfOccurrence;
    private Long maximumAmountOfCores;

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Double getProbabilityOfOccurrence() {
        return probabilityOfOccurrence;
    }

    public void setProbabilityOfOccurrence(Double probabilityOfOccurrence) {
        this.probabilityOfOccurrence = probabilityOfOccurrence;
    }

    public Long getMaximumAmountOfCores() {
        return maximumAmountOfCores;
    }

    public void setMaximumAmountOfCores(Long maximumAmountOfCores) {
        this.maximumAmountOfCores = maximumAmountOfCores;
    }
}

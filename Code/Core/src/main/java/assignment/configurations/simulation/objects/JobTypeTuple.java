package assignment.configurations.simulation.objects;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class JobTypeTuple {
    private String nodeType;
    private Double probabilityOfOccurrence;
    private Long maximumAmountOfNodes;

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

    public Long getMaximumAmountOfNodes() {
        return maximumAmountOfNodes;
    }

    public void setMaximumAmountOfNodes(Long maximumAmountOfNodes) {
        this.maximumAmountOfNodes = maximumAmountOfNodes;
    }
}

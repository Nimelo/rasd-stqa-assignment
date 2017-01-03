package assignment.simulator.objects;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class RequestedResource {
    private String nodeType;
    private Long amountOfNodes;
    private Long amountOfCores;

    public RequestedResource(String nodeType, Long amountOfNodes, Long amountOfCores) {
        this.nodeType = nodeType;
        this.amountOfNodes = amountOfNodes;
        this.amountOfCores = amountOfCores;
    }

    public String getNodeType() {
        return nodeType;
    }

    public Long getAmountOfNodes() {
        return amountOfNodes;
    }

    public Long getAmountOfCores() {
        return amountOfCores;
    }
}

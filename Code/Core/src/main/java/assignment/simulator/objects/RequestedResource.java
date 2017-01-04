package assignment.simulator.objects;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class RequestedResource {
    private String nodeType;
    private Long amountOfCores;

    public RequestedResource(String nodeType, Long amountOfCores) {
        this.nodeType = nodeType;
        this.amountOfCores = amountOfCores;
    }

    public String getNodeType() {
        return nodeType;
    }

    public Long getAmountOfCores() {
        return amountOfCores;
    }
}

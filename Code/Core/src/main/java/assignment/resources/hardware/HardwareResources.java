package assignment.resources.hardware;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class HardwareResources {
    private final Long amountOfNodes;
    private final Long amountOfCPUs;
    private final Long cpusPerNode;

    public HardwareResources(Long amountOfNodes, Long amountOfCPUs, Long cpusPerNode) {
        this.amountOfNodes = amountOfNodes;
        this.amountOfCPUs = amountOfCPUs;
        this.cpusPerNode = cpusPerNode;
    }

    public Long getCoresPerNode() {
        return cpusPerNode;
    }

    public Long getAmountOfNodes() {
        return amountOfNodes;
    }

    public Long getAmountOfCores() {
        return amountOfCPUs;
    }
}

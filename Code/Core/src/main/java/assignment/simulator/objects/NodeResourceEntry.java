package assignment.simulator.objects;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class NodeResourceEntry {
    private String nodeType;
    private Long nodeAmount;
    private Long coresPerNode;
    private Long usedCores;

    public NodeResourceEntry(String nodeType, Long nodeAmount, Long coresPerNode, Long usedCores) {
        this.nodeType = nodeType;
        this.nodeAmount = nodeAmount;
        this.coresPerNode = coresPerNode;
        this.usedCores = usedCores;
    }

    public String getNodeType() {
        return nodeType;
    }

    public Long getNodeAmount() {
        return nodeAmount;
    }

    public Long getCoresPerNode() {
        return coresPerNode;
    }

    public Long getUsedCores() {
        return usedCores;
    }

    public Long getUnusedCores() {
        return nodeAmount * coresPerNode - getUsedCores();
    }

    public void allocate(Long numberOfCores) {
        usedCores += numberOfCores;
    }

    public void deallocate(Long numberOfCores) {
        usedCores -= numberOfCores;
    }
}

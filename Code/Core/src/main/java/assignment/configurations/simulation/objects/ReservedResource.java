package assignment.configurations.simulation.objects;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class ReservedResource {
    private String nodeType;
    private Long amount;

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}

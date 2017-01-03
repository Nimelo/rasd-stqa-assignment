package assignment.configurations.simulation.objects;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class ConstraintResource {
    private String nodeType;
    private Long amount;
    private Long amountOfCores;

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

    public Long getAmountOfCores() {
        return amountOfCores;
    }

    public void setAmountOfCores(Long amountOfCores) {
        this.amountOfCores = amountOfCores;
    }
}

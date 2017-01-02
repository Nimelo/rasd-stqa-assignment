package assignment.configurations.simulation.objects;

import java.math.BigDecimal;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class Node {
    private String name;
    private Long amount;
    private Long amountOfCores;
    //TODO: Connect price with time. (Assumption has been made - seconds.)
    private BigDecimal price;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAmountOfCores() {
        return amountOfCores;
    }

    public void setAmountOfCores(Long amountOfCores) {
        this.amountOfCores = amountOfCores;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

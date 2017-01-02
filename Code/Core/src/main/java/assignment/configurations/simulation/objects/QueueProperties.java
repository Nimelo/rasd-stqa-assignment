package assignment.configurations.simulation.objects;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class QueueProperties{
    private String name;
    private String description;
    private Long maximumExecutionTime;
    private BigDecimal price;
    private List<ReservedResources> reservedResources;
    private AvailabilityTime availabilityTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMaximumExecutionTime() {
        return maximumExecutionTime;
    }

    public void setMaximumExecutionTime(Long maximumExecutionTime) {
        this.maximumExecutionTime = maximumExecutionTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ReservedResources> getReservedResources() {
        return reservedResources;
    }

    public void setReservedResources(List<ReservedResources> reservedResources) {
        this.reservedResources = reservedResources;
    }

    public AvailabilityTime getAvailabilityTime() {
        return availabilityTime;
    }

    public void setAvailabilityTime(AvailabilityTime availabilityTime) {
        this.availabilityTime = availabilityTime;
    }

}

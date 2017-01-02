package assignment.configurations.simulation.objects;

import assignment.configurations.simulation.exceptions.ValidationException;
import assignment.configurations.simulation.interfaces.IValidate;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class QueueProperties implements IValidate{
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

    @Override
    public void validate() throws ValidationException {
        if (maximumExecutionTime < 0L)
            throw new ValidationException("Maximum execution time is less then zero.", QueueProperties.class);
        if (price.doubleValue() < 0L)
            throw new ValidationException("Price cannot be less than zero.", QueueProperties.class);
        availabilityTime.validate();

        for (ReservedResources reservedResource : reservedResources) {
            if (reservedResource.getAmount() < 0)
                throw new ValidationException("Cannot reserve negative amount of nodes.", ReservedResources.class);
        }
    }
}

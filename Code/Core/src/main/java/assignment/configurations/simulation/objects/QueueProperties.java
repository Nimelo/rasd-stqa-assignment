package assignment.configurations.simulation.objects;

import java.util.List;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class QueueProperties{
    private String name;
    private String description;
    private Long maximumExecutionTime;
    private Double priceFactor;
    private List<ReservedResource> reservedResources;
    private List<ConstraintResource> constraintResources;
    private AvailabilityTime availabilityTime;

    public List<ConstraintResource> getConstraintResources() {
        return constraintResources;
    }

    public void setConstraintResources(List<ConstraintResource> constraintResources) {
        this.constraintResources = constraintResources;
    }

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

    public Double getPriceFactor() {
        return priceFactor;
    }

    public void setPriceFactor(Double priceFactor) {
        this.priceFactor = priceFactor;
    }

    public List<ReservedResource> getReservedResources() {
        return reservedResources;
    }

    public void setReservedResources(List<ReservedResource> reservedResources) {
        this.reservedResources = reservedResources;
    }

    public AvailabilityTime getAvailabilityTime() {
        return availabilityTime;
    }

    public void setAvailabilityTime(AvailabilityTime availabilityTime) {
        this.availabilityTime = availabilityTime;
    }

}

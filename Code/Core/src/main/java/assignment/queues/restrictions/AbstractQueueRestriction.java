package assignment.queues.restrictions;

import assignment.resources.monitors.ResourcesMonitor;

/**
 * Created by mrnim on 25-Dec-16.
 */
public abstract class AbstractQueueRestriction {
    protected Long maxCoresPerSingleJob;
    protected Long maxExecutionTime;
    protected Double reservationRatio;

    public AbstractQueueRestriction(ResourcesMonitor resourcesMonitor) {
        this.setUp(resourcesMonitor);
    }

    protected void setUp(ResourcesMonitor resourcesMonitor){
        this.setUpMaxTime(resourcesMonitor);
        this.setUpRatio(resourcesMonitor);
        this.setUpMaxCores(resourcesMonitor);
    }

    protected abstract void setUpRatio(ResourcesMonitor resourcesMonitor);

    protected abstract void setUpMaxTime(ResourcesMonitor resourcesMonitor);

    protected void setUpMaxCores(ResourcesMonitor resourcesMonitor){
        this.maxCoresPerSingleJob = new Long((long)Math.floor(this.reservationRatio * resourcesMonitor.getHardwareResources().getAmountOfCores()));
    }

    public Long getMaxCoresPerSingleJob() {
        return maxCoresPerSingleJob;
    }

    public Long getMaxExecutionTime() {
        return maxExecutionTime;
    }

    public Double getReservationRatio() {
        return reservationRatio;
    }
}

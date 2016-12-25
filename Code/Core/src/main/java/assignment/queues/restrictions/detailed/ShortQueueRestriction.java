package assignment.queues.restrictions.detailed;

import assignment.queues.restrictions.AbstractQueueRestriction;
import assignment.resources.monitors.ResourcesMonitor;

import java.util.Calendar;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class ShortQueueRestriction extends AbstractQueueRestriction {

    public ShortQueueRestriction(ResourcesMonitor resourcesMonitor) {
        super(resourcesMonitor);
    }

    @Override
    protected void setUpRatio(ResourcesMonitor resourcesMonitor) {
        this.reservationRatio = 0.1;
    }

    @Override
    protected void setUpMaxTime(ResourcesMonitor resourcesMonitor) {
        this.maxExecutionTime = new Long(1 * 60 * 60 * 1000);
    }

    @Override
    protected void setUpMaxCores(ResourcesMonitor resourcesMonitor) {
        this.maxCoresPerSingleJob = 2 * resourcesMonitor.getHardwareResources().getCoresPerNode();
    }
}

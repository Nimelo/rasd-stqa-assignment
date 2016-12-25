package assignment.queues.restrictions.detailed;

import assignment.queues.restrictions.AbstractQueueRestriction;
import assignment.resources.monitors.ResourcesMonitor;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class LargeQueueRestrictions extends AbstractQueueRestriction {

    public LargeQueueRestrictions(ResourcesMonitor resourcesMonitor) {
        super(resourcesMonitor);
    }

    @Override
    protected void setUpRatio(ResourcesMonitor resourcesMonitor) {
        this.reservationRatio = 0.5;
    }

    @Override
    protected void setUpMaxTime(ResourcesMonitor resourcesMonitor) {
        this.maxExecutionTime = new Long(16 * 60 * 60 * 1000);
    }
}

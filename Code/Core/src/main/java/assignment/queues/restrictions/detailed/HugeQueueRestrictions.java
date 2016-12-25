package assignment.queues.restrictions.detailed;

import assignment.queues.restrictions.AbstractQueueRestriction;
import assignment.resources.monitors.ResourcesMonitor;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class HugeQueueRestrictions extends AbstractQueueRestriction {

    public HugeQueueRestrictions(ResourcesMonitor resourcesMonitor) {
        super(resourcesMonitor);
    }

    @Override
    protected void setUpRatio(ResourcesMonitor resourcesMonitor) {
        this.reservationRatio = 1.0;
    }

    @Override
    protected void setUpMaxTime(ResourcesMonitor resourcesMonitor) {
        this.maxExecutionTime = new Long((7 + 24 + 9) * 60 * 60 * 1000);
    }
}

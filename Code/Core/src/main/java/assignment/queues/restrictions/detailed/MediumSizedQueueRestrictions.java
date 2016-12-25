package assignment.queues.restrictions.detailed;

import assignment.queues.restrictions.AbstractQueueRestriction;
import assignment.resources.monitors.ResourcesMonitor;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class MediumSizedQueueRestrictions extends AbstractQueueRestriction {

    public MediumSizedQueueRestrictions(ResourcesMonitor resourcesMonitor) {
        super(resourcesMonitor);
    }

    @Override
    protected void setUpRatio(ResourcesMonitor resourcesMonitor) {
        this.reservationRatio = 0.3;
    }

    @Override
    protected void setUpMaxTime(ResourcesMonitor resourcesMonitor) {
        this.maxExecutionTime = new Long(8 * 60 * 60 * 1000);
    }
}

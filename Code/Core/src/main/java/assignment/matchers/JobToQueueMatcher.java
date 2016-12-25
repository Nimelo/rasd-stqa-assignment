package assignment.matchers;

import assignment.matchers.interfaces.IJobToQueueMatcher;
import assignment.queues.enums.QueueEnum;
import assignment.queues.restrictions.QueueRestrictions;
import assignment.queues.restrictions.detailed.HugeQueueRestrictions;
import assignment.queues.restrictions.detailed.LargeQueueRestrictions;
import assignment.queues.restrictions.detailed.MediumSizedQueueRestrictions;
import assignment.queues.restrictions.detailed.ShortQueueRestriction;
import assignment.resources.objects.RequestedHardware;
import assignment.resources.objects.RequestedTime;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class JobToQueueMatcher implements IJobToQueueMatcher {
    private QueueRestrictions queueRestrictions;

    public JobToQueueMatcher(QueueRestrictions queueRestrictions) {
        this.queueRestrictions = queueRestrictions;
    }

    @Override
    public QueueEnum match(RequestedHardware requestedHardware, RequestedTime requestedTime) {
        if(requestedHardware.getRequestedCores() <= this.getShortQueueRestriction().getMaxCoresPerSingleJob())
        {
            if(requestedTime.getRequestedTime() <= this.getShortQueueRestriction().getMaxExecutionTime()){
                return QueueEnum.SHORT;
            }
        }

        if(requestedHardware.getRequestedCores() <= this.getMediumSizedQueueRestrictions().getMaxCoresPerSingleJob())
        {
            if(requestedTime.getRequestedTime() <= this.getMediumSizedQueueRestrictions().getMaxExecutionTime()){
                return QueueEnum.MEDIUM_SIZED;
            }
        }

        if(requestedHardware.getRequestedCores() <= this.getLargeQueueRestrictions().getMaxCoresPerSingleJob())
        {
            if(requestedTime.getRequestedTime() <= this.getLargeQueueRestrictions().getMaxExecutionTime()){
                return QueueEnum.LARGE;
            }
        }

        return QueueEnum.HUGE;
    }

    public HugeQueueRestrictions getHugeQueueRestrictions() {
        return queueRestrictions.getHugeQueueRestrictions();
    }

    public LargeQueueRestrictions getLargeQueueRestrictions() {
        return queueRestrictions.getLargeQueueRestrictions();
    }

    public MediumSizedQueueRestrictions getMediumSizedQueueRestrictions() {
        return queueRestrictions.getMediumSizedQueueRestrictions();
    }

    public ShortQueueRestriction getShortQueueRestriction() {
        return queueRestrictions.getShortQueueRestriction();
    }
}

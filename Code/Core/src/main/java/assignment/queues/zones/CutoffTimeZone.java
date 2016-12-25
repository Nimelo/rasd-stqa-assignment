package assignment.queues.zones;

import assignment.events.timing.arguments.MetronomeEventArgs;
import assignment.events.timing.arguments.TickType;
import assignment.queues.restrictions.AbstractQueueRestriction;
import assignment.resources.monitors.ResourcesMonitor;
import assignment.tasks.Job;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class CutoffTimeZone extends FCFSZone {
    private AbstractQueueRestriction restriction;

    public CutoffTimeZone(Collection<Job> waitingQueue, Collection<Job> executingArea, ResourcesMonitor resourcesMonitor, AbstractQueueRestriction restriction) {
        super(waitingQueue, executingArea, resourcesMonitor);
        this.restriction = restriction;
    }

    @Override
    public void moveWaitingJobsToExecutionArea(MetronomeEventArgs arg) {
        if(TickType.WEEK == arg.getTickType()) {
            super.moveWaitingJobsToExecutionArea(arg);
        } else if (TickType.CUTOFF == arg.getTickType()){
            Long timeTillWeekend = arg.getCutoffEndTick() - arg.getCurrentTick();
            if(restriction.getMaxExecutionTime() <= timeTillWeekend){
                super.moveWaitingJobsToExecutionArea(arg);
            }
        }
    }

    @Override
    public void removeJobsFromExecutionArea(MetronomeEventArgs arg) {
        if(TickType.WEEKEND == arg.getTickType()){
            Iterator<Job> iterator = this.getExecutingArea().iterator();
            while(iterator.hasNext()){
                Job job = iterator.next();
                job.markAsInterrupted(arg.getCurrentTick());
                iterator.remove();
            }
        }
        else {
            super.removeJobsFromExecutionArea(arg);
        }
    }
}

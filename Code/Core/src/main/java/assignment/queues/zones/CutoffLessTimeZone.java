package assignment.queues.zones;

import assignment.events.timing.arguments.MetronomeEventArgs;
import assignment.events.timing.arguments.TickType;
import assignment.resources.monitors.ResourcesMonitor;
import assignment.tasks.Job;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class CutoffLessTimeZone extends FCFSZone {
    public CutoffLessTimeZone(Collection<Job> waitingQueue, Collection<Job> executingArea, ResourcesMonitor resourcesMonitor) {
        super(waitingQueue, executingArea, resourcesMonitor);
    }

    @Override
    public void moveWaitingJobsToExecutionArea(MetronomeEventArgs arg) {
        if(TickType.WEEKEND == arg.getTickType())
            super.moveWaitingJobsToExecutionArea(arg);
    }

    @Override
    public void removeJobsFromExecutionArea(MetronomeEventArgs arg) {
        if(TickType.WEEKEND == arg.getTickType()) {
            super.removeJobsFromExecutionArea(arg);
        } else {
            Iterator<Job> iterator = this.getExecutingArea().iterator();
            while(iterator.hasNext()){
                Job job = iterator.next();
                job.markAsInterrupted(arg.getCurrentTick());
                iterator.remove();
            }
        }
    }
}

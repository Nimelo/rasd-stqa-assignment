package assignment.queues;

import assignment.events.timing.arguments.MetronomeEventArgs;
import assignment.queues.interfaces.IFCFSZone;
import assignment.resources.monitors.ResourcesMonitor;
import assignment.resources.monitors.exceptions.NotSufficientAmountOfCPUs;
import assignment.tasks.Job;
import assignment.tasks.objects.Timestamp;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class FCFSZone implements IFCFSZone, Runnable{
    private String name;
    private Collection<Job> waitingQueue;
    private Collection<Job> executingArea;
    private ResourcesMonitor resourcesMonitor;

    public FCFSZone(String name, Collection<Job> waitingQueue, Collection<Job> executingArea, ResourcesMonitor resourcesMonitor) {
        this.name = name;
        this.waitingQueue = waitingQueue;
        this.executingArea = executingArea;
        this.resourcesMonitor = resourcesMonitor;
    }

    public FCFSZone(Collection<Job> waitingQueue, Collection<Job> executingArea, ResourcesMonitor resourcesMonitor) {
        this.waitingQueue = waitingQueue;
        this.executingArea = executingArea;
        this.resourcesMonitor = resourcesMonitor;
    }

    public void submitJob(Job job){
        this.waitingQueue.add(job);
    }

    public Collection<Job> getWaitingQueue() {
        return waitingQueue;
    }

    public Collection<Job> getExecutingArea() {
        return executingArea;
    }

    public void doIteration(MetronomeEventArgs arg){
        moveWaitingJobsToExecutionArea(arg);
        removeJobsFromExecutionArea(arg);
    }

    public void moveWaitingJobsToExecutionArea(MetronomeEventArgs arg) {
        Iterator<Job> iterator = waitingQueue.iterator();
        while(iterator.hasNext()) {
            Job job = iterator.next();
            Long requestedCores = job.getRequests().getRequestedHardware().getRequestedCores();
            if(resourcesMonitor.canReserveCores(requestedCores)){
                try {
                    resourcesMonitor.reserveCores(requestedCores);
                    this.executingArea.add(job);
                    job.getTimestamps().setQuitQueueTimestamp(new Timestamp(arg.getCurrentTick()));
                    job.getTimestamps().setEndOfExecutionTimestamp(new Timestamp(arg.getCurrentTick() + job.getRequests().getRequestedTime().getRequestedTime()));
                    iterator.remove();
                } catch (NotSufficientAmountOfCPUs notSufficientAmountOfCPUs) {

                }
            }
        }
    }

    public void removeJobsFromExecutionArea(MetronomeEventArgs arg) {
        Iterator<Job> iterator = executingArea.iterator();
        while(iterator.hasNext()) {
            Job job = iterator.next();
            if(job.getTimestamps().getEndOfExecutionTimestamp().getValue() <= arg.getCurrentTick()){
                this.resourcesMonitor.releaseCores(job.getRequests().getRequestedHardware().getRequestedCores());
                iterator.remove();
            }
        }
    }

    @Override
    public void run() {

    }
}

package assignment.queues.interfaces;

import assignment.events.timing.arguments.MetronomeEventArgs;
import assignment.tasks.Job;

/**
 * Created by mrnim on 25-Dec-16.
 */
public interface IFCFSZone {
    void submitJob(Job job);
    void doIteration(MetronomeEventArgs arg);
}

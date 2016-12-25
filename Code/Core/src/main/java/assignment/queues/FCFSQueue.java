package assignment.queues;

import assignment.tasks.Job;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Supplier;

/**
 * Created by mrnim on 23-Dec-16.
 */
public class FCFSQueue extends ArrayBlockingQueue<Job> {

    protected Supplier<Long> currentTimeSupplier;

    public FCFSQueue(int capacity, Supplier<Long> currentTimeSupplier) {
        super(capacity);
        this.currentTimeSupplier = currentTimeSupplier;
    }

    @Override
    public void put(Job job) throws InterruptedException {
        super.put(job);
        job.setJobQueueEnterTimestamp(currentTimeSupplier.get());
    }

    @Override
    public Job take() throws InterruptedException {
        Job job = super.take();
        job.setJobQueueQuitTimestamp(currentTimeSupplier.get());
        return job;
    }

}

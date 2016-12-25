package assignment.tasks;

import assignment.tasks.objects.Identification;
import assignment.tasks.objects.Requests;
import assignment.tasks.objects.Timestamps;

/**
 * Created by mrnim on 23-Dec-16.
 */
public class Job {
    private Identification identification;
    private Requests requests;
    private Timestamps timestamps;

    public Job(Identification identification, Requests requests, Timestamps timestamps) {
        this.identification = identification;
        this.requests = requests;
        this.timestamps = timestamps;
    }

    public Identification getIdentification() {
        return identification;
    }

    public Requests getRequests() {
        return requests;
    }

    public Timestamps getTimestamps() {
        return timestamps;
    }
}

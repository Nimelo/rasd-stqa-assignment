package assignment.simulator.objects.queue.areas;

import assignment.simulator.objects.Job;

import java.util.List;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class JobArea {
    private List<Job> jobs;

    public JobArea(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Job> getJobs() {
        return jobs;
    }
}

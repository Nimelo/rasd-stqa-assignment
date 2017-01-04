package assignment.simulator.objects.queue;

import assignment.simulator.objects.User;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
public class UserJob {
    private User user;
    private Long amountOfExecutingJobs;

    public UserJob(User user, Long amountOfExecutingJobs) {
        this.user = user;
        this.amountOfExecutingJobs = amountOfExecutingJobs;
    }

    public void setAmountOfExecutingJobs(Long amountOfExecutingJobs) {
        this.amountOfExecutingJobs = amountOfExecutingJobs;
    }

    public User getUser() {

        return user;
    }

    public Long getAmountOfExecutingJobs() {
        return amountOfExecutingJobs;
    }

    public void increaseAmountOfExecutingJobs() {
        this.amountOfExecutingJobs += 1;
    }

    public void decreaseAmountOfExecutingJobs() {
        this.amountOfExecutingJobs -= 1;
    }

}

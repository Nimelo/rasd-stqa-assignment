package assignment.matchers;

import assignment.matchers.interfaces.IJobToQueueMatcher;
import assignment.queues.enums.QueueEnum;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class JobToQueueMatcher implements IJobToQueueMatcher {
    private Long totalAmountOfCPUs;
    @Override
    public QueueEnum match(Long cpus, Long time) {
        //if(cpus <= )
    }
}

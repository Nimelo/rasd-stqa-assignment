package assignment.matchers.interfaces;

import assignment.queues.enums.QueueEnum;

/**
 * Created by mrnim on 25-Dec-16.
 */
public interface IJobToQueueMatcher {
    QueueEnum match(Long cpus, Long time);
}

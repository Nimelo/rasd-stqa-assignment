package assignment.matchers.interfaces;

import assignment.queues.enums.QueueEnum;
import assignment.resources.objects.RequestedResources;
import assignment.resources.objects.RequestedTime;

/**
 * Created by mrnim on 25-Dec-16.
 */
public interface IJobToQueueMatcher {
    QueueEnum match(Long cpus, Long time);
}

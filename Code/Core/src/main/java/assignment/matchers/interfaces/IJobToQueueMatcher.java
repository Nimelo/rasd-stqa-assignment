package assignment.matchers.interfaces;

import assignment.queues.enums.QueueEnum;
import assignment.resources.objects.RequestedHardware;
import assignment.resources.objects.RequestedTime;

/**
 * Created by mrnim on 25-Dec-16.
 */
public interface IJobToQueueMatcher {
    QueueEnum match(RequestedHardware requestedHardware, RequestedTime requestedTime);
}

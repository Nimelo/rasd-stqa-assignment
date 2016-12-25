package assignment.tasks.objects;

import assignment.resources.objects.RequestedHardware;
import assignment.resources.objects.RequestedTime;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class Requests {
    private RequestedTime requestedTime;
    private RequestedHardware requestedHardware;

    public Requests(RequestedTime requestedTime, RequestedHardware requestedHardware) {
        this.requestedTime = requestedTime;
        this.requestedHardware = requestedHardware;
    }

    public RequestedTime getRequestedTime() {
        return requestedTime;
    }

    public RequestedHardware getRequestedHardware() {
        return requestedHardware;
    }
}

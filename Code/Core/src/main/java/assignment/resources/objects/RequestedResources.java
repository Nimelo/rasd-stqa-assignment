package assignment.resources.objects;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class RequestedResources {
    private final Long requestedCPUs;

    public RequestedResources(Long requestedCPUs) {
        this.requestedCPUs = requestedCPUs;
    }

    public Long getRequestedCPUs() {
        return requestedCPUs;
    }
}

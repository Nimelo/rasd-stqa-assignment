package assignment.resources.objects;

import assignment.resources.hardware.HardwareResources;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class RequestedHardware {
    private Long requestedCores;
    private Long requestedNodes;
    private Boolean checkCores;

    public RequestedHardware(Long requestedCores, Long requestedNodes, Boolean checkCores) {
        this.requestedCores = requestedCores;
        this.requestedNodes = requestedNodes;
        this.checkCores = checkCores;
    }


    public Long getRequestedCores() {
        return requestedCores;
    }

    public Long getRequestedNodes() {
        return requestedNodes;
    }

    public Boolean getCheckCores() {
        return checkCores;
    }
}

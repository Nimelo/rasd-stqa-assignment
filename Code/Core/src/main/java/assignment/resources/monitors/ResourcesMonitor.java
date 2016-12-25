package assignment.resources.monitors;

import assignment.resources.monitors.exceptions.NotSufficientAmountOfCPUs;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class ResourcesMonitor {
    private Long totalCPUs;
    private Long usedCPUs;

    public ResourcesMonitor(Long totalCPUs, Long usedCPUs) {
        this.totalCPUs = totalCPUs;
        this.usedCPUs = usedCPUs;
    }

    public Long getFreeCPUs(){
        return this.getTotalCPUs() - this.getUsedCPUs();
    }

    public Long getTotalCPUs() {
        return totalCPUs;
    }

    public Long getUsedCPUs() {
        return usedCPUs;
    }

    public void reserveCPUs(Long toReserve) throws NotSufficientAmountOfCPUs {
        if(this.canReserveCPUs(toReserve))
            this.usedCPUs = toReserve;
        else
            throw new NotSufficientAmountOfCPUs();
    }

    public void releaseCPUs(Long toRelease){
        this.usedCPUs -= toRelease;
    }
    public boolean canReserveCPUs(Long toReserve) {
        return this.getFreeCPUs() >= toReserve;
    }
}

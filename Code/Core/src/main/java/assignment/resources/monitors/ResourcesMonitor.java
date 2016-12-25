package assignment.resources.monitors;

import assignment.resources.hardware.HardwareResources;
import assignment.resources.monitors.exceptions.NotSufficientAmountOfCPUs;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class ResourcesMonitor {
    private HardwareResources hardwareResources;
    private Long usedCores;

    public ResourcesMonitor(HardwareResources hardwareResources, Long usedCores) {
        this.hardwareResources = hardwareResources;
        this.usedCores = usedCores;
    }

    public HardwareResources getHardwareResources() {
        return hardwareResources;
    }

    public Long getFreeCores(){
        return this.getTotalCores() - this.getUsedCores();
    }

    public Long getTotalCores() {
        return this.hardwareResources.getAmountOfCores();
    }

    public Long getUsedCores() {
        return this.usedCores;
    }

    public void reserveCores(Long toReserve) throws NotSufficientAmountOfCPUs {
        if(this.canReserveCores(toReserve))
            this.usedCores += toReserve;
        else
            throw new NotSufficientAmountOfCPUs();
    }

    public void releaseCores(Long toRelease){
        this.usedCores -= toRelease;
    }

    public boolean canReserveCores(Long toReserve) {
        return this.getFreeCores() >= toReserve;
    }
}

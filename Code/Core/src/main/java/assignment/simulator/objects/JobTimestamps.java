package assignment.simulator.objects;

import assignment.simulator.objects.time.Timestamp;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class JobTimestamps {
    private Timestamp spawnTime;
    private Timestamp waitingAreaEnterTime;
    private Timestamp waitingAreaQuitTime;
    private Timestamp executionAreaEnterTime;
    private Timestamp executionAreaQuitTime;

    public JobTimestamps(Timestamp spawnTime) {
        this.spawnTime = spawnTime;
    }

    public Timestamp getSpawnTime() {
        return spawnTime;
    }

    public Timestamp getWaitingAreaEnterTime() {
        return waitingAreaEnterTime;
    }

    public Timestamp getWaitingAreaQuitTime() {
        return waitingAreaQuitTime;
    }

    public Timestamp getExecutionAreaEnterTime() {
        return executionAreaEnterTime;
    }

    public Timestamp getExecutionAreaQuitTime() {
        return executionAreaQuitTime;
    }

    public void setWaitingAreaEnterTime(Timestamp waitingAreaEnterTime) {
        this.waitingAreaEnterTime = waitingAreaEnterTime;
    }

    public void setWaitingAreaQuitTime(Timestamp waitingAreaQuitTime) {
        this.waitingAreaQuitTime = waitingAreaQuitTime;
    }

    public void setExecutionAreaEnterTime(Timestamp executionAreaEnterTime) {
        this.executionAreaEnterTime = executionAreaEnterTime;
    }

    public void setExecutionAreaQuitTime(Timestamp executionAreaQuitTime) {
        this.executionAreaQuitTime = executionAreaQuitTime;
    }
}

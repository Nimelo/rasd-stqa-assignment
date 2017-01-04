package assignment.simulator.reporting;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
public class Report {
    private Map<String, Long> throughput;
    private Long actualNumberOfMachineHoursConsumedBYUserJobs;
    private BigDecimal resultingPricePaidByUsers;
    private Map<String, Long> averageWaitTimeInEachQueue;
    private Long turnAround;
    private Long economicBalance;

    public Map<String, Long> getThroughput() {
        return throughput;
    }

    public void setThroughput(Map<String, Long> throughput) {
        this.throughput = throughput;
    }

    public Long getActualNumberOfMachineHoursConsumedBYUserJobs() {
        return actualNumberOfMachineHoursConsumedBYUserJobs;
    }

    public void setActualNumberOfMachineHoursConsumedBYUserJobs(Long actualNumberOfMachineHoursConsumedBYUserJobs) {
        this.actualNumberOfMachineHoursConsumedBYUserJobs = actualNumberOfMachineHoursConsumedBYUserJobs;
    }

    public BigDecimal getResultingPricePaidByUsers() {
        return resultingPricePaidByUsers;
    }

    public void setResultingPricePaidByUsers(BigDecimal resultingPricePaidByUsers) {
        this.resultingPricePaidByUsers = resultingPricePaidByUsers;
    }

    public Map<String, Long> getAverageWaitTimeInEachQueue() {
        return averageWaitTimeInEachQueue;
    }

    public void setAverageWaitTimeInEachQueue(Map<String, Long> averageWaitTimeInEachQueue) {
        this.averageWaitTimeInEachQueue = averageWaitTimeInEachQueue;
    }

    public Long getTurnAround() {
        return turnAround;
    }

    public void setTurnAround(Long turnAround) {
        this.turnAround = turnAround;
    }

    public Long getEconomicBalance() {
        return economicBalance;
    }

    public void setEconomicBalance(Long economicBalance) {
        this.economicBalance = economicBalance;
    }
}

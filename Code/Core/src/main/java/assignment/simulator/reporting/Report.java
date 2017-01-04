package assignment.simulator.reporting;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
public class Report {
    private Map<String, Long> throughput;
    private Long actualNumberOfMachineHoursConsumedByUserJobs;
    private BigDecimal resultingPricePaidByUsers;
    private Map<String, Long> averageWaitTimeInEachQueue;
    private Long turnAroundTimeRatio;
    private BigDecimal economicBalanceOfTheCentre;

    public Map<String, Long> getThroughput() {
        return throughput;
    }

    public void setThroughput(Map<String, Long> throughput) {
        this.throughput = throughput;
    }

    public Long getActualNumberOfMachineHoursConsumedByUserJobs() {
        return actualNumberOfMachineHoursConsumedByUserJobs;
    }

    public void setActualNumberOfMachineHoursConsumedByUserJobs(Long actualNumberOfMachineHoursConsumedByUserJobs) {
        this.actualNumberOfMachineHoursConsumedByUserJobs = actualNumberOfMachineHoursConsumedByUserJobs;
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

    public Long getTurnAroundTimeRatio() {
        return turnAroundTimeRatio;
    }

    public void setTurnAroundTimeRatio(Long turnAroundTimeRatio) {
        this.turnAroundTimeRatio = turnAroundTimeRatio;
    }

    public BigDecimal getEconomicBalanceOfTheCentre() {
        return economicBalanceOfTheCentre;
    }

    public void setEconomicBalanceOfTheCentre(BigDecimal economicBalanceOfTheCentre) {
        this.economicBalanceOfTheCentre = economicBalanceOfTheCentre;
    }
}

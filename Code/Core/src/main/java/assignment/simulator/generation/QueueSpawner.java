package assignment.simulator.generation;

import assignment.configurations.simulation.QueuesConfiguration;
import assignment.configurations.simulation.SystemResources;
import assignment.configurations.simulation.objects.Node;
import assignment.configurations.simulation.objects.QueueProperties;
import assignment.configurations.simulation.objects.ReservedResource;
import assignment.simulator.budget.BudgetAnalytics;
import assignment.simulator.objects.Job;
import assignment.simulator.objects.NodeResourceEntry;
import assignment.simulator.objects.queue.HardwareResourcesManager;
import assignment.simulator.objects.queue.Queue;
import assignment.simulator.objects.queue.areas.JobArea;
import assignment.simulator.objects.time.TimestampInterpretator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class QueueSpawner {
    private QueuesConfiguration queuesConfiguration;
    private SystemResources systemResources;
    private TimestampInterpretator timestampInterpretator;
    private BudgetAnalytics budgetAnalytics;

    public QueueSpawner(QueuesConfiguration queuesConfiguration, SystemResources systemResources, TimestampInterpretator timestampInterpretator, BudgetAnalytics budgetAnalytics) {
        this.queuesConfiguration = queuesConfiguration;
        this.systemResources = systemResources;
        this.timestampInterpretator = timestampInterpretator;
        this.budgetAnalytics = budgetAnalytics;
    }

    public List<Queue> spawnQueues() {
        List<Queue> queues = new ArrayList<>();

        for (QueueProperties queueProperties : queuesConfiguration.getQueues()) {
            String name = queueProperties.getName();
            JobArea waitingArea = new JobArea(new ArrayList<Job>());
            JobArea executionArea = new JobArea(new ArrayList<Job>());
            HardwareResourcesManager hardwareResourcesManager = createHardwareResourceManagerBasedOn(queueProperties.getReservedResources());
            Long beginTick = timestampInterpretator.getTickByShiftTime(queueProperties.getAvailabilityTime().getBegin());
            Long endTick = timestampInterpretator.getTickByShiftTime(queueProperties.getAvailabilityTime().getEnd());

            queues.add(new Queue(name, waitingArea, executionArea, hardwareResourcesManager, beginTick, endTick, queueProperties.getMaximumExecutionTime(), budgetAnalytics));
        }

        return queues;
    }

    public HardwareResourcesManager createHardwareResourceManagerBasedOn(List<ReservedResource> reservedResources) {
        Map<String, NodeResourceEntry> map = new HashMap<String, NodeResourceEntry>();

        for (ReservedResource reservedResource : reservedResources) {
            String name = reservedResource.getNodeType();
            Long nodeAmount = reservedResource.getAmount();
            Long coresPerNode = findCoresPerNode(name);
            Long usedCores = 0L;

            map.put(reservedResource.getNodeType(), new NodeResourceEntry(name, nodeAmount, coresPerNode, usedCores));
        }

        HardwareResourcesManager hardwareResourcesManager = new HardwareResourcesManager(map);
        return hardwareResourcesManager;
    }

    public Long findCoresPerNode(String name) {
        return systemResources.getNodes().stream().filter(x -> x.getName().equals(name)).map(x -> x.getAmountOfCores()).findFirst().get();
    }
}

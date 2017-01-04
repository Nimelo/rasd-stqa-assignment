package assignment.simulator.generation;

import assignment.configurations.simulation.objects.Node;
import assignment.configurations.simulation.objects.QueueProperties;
import assignment.configurations.simulation.objects.ReservedResource;
import assignment.simulator.objects.NodeResourceEntry;
import assignment.simulator.objects.User;
import assignment.simulator.objects.queue.HardwareResourcesManager;
import assignment.simulator.objects.queue.Queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class QueueSpawner {
    private List<QueueProperties> queueProperties;
    private List<Node> nodes;
    private List<User> users;

    public QueueSpawner(List<QueueProperties> queueProperties, List<Node> nodes, List<User> users) {
        this.queueProperties = queueProperties;
        this.nodes = nodes;
        this.users = users;
    }

    public List<Queue> spawnQueues() {
        List<Queue> queues = new ArrayList<>();

        for (QueueProperties queueProperties : queueProperties) {
            String name = queueProperties.getName();
            HardwareResourcesManager hardwareResourcesManager = createHardwareResourceManagerBasedOn(queueProperties.getReservedResources());
            queues.add(new Queue(queueProperties, users, hardwareResourcesManager));
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
        return nodes.stream().filter(x -> x.getName().equals(name)).map(x -> x.getAmountOfCores()).findFirst().get();
    }
}

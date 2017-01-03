package assignment.simulator.objects.queue;

import assignment.simulator.objects.NodeResourceEntry;
import assignment.simulator.objects.RequestedResource;

import java.util.List;
import java.util.Map;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class HardwareResourcesManager {
    private Map<String, NodeResourceEntry> nodeResourceEntryMap;

    public HardwareResourcesManager(Map<String, NodeResourceEntry> nodeResourceEntryMap) {
        this.nodeResourceEntryMap = nodeResourceEntryMap;
    }

    public boolean tryAllocateResources(List<RequestedResource> requestedResourceList) {
        for (RequestedResource requestedResource : requestedResourceList) {
            if (checkIfCanAllocate(requestedResource)) {
                return false;
            }
        }
        allocateResourceList(requestedResourceList);
        return true;
    }

    public boolean checkIfCanAllocate(RequestedResource requestedResource) {
        String nodeType = requestedResource.getNodeType();
        NodeResourceEntry value = nodeResourceEntryMap.get(nodeType);

        return value.getUnusedCores() >= requestedResource.getAmountOfCores();
    }

    public void allocateResourceList(List<RequestedResource> requestedResourceList) {
        for (RequestedResource requestedResource : requestedResourceList) {
            allocateResource(requestedResource);
        }
    }

    public void allocateResource(RequestedResource requestedResource) {
        String nodeType = requestedResource.getNodeType();
        NodeResourceEntry value = nodeResourceEntryMap.get(nodeType);

        value.allocate(requestedResource.getAmountOfCores());
    }

    public Map<String, NodeResourceEntry> getNodeResourceEntryMap() {
        return nodeResourceEntryMap;
    }

    public void deallocateResources(List<RequestedResource> requestedResourceList) {
        for (RequestedResource requestedResource : requestedResourceList) {
            deallocateResource(requestedResource);
        }
    }

    public void deallocateResource(RequestedResource requestedResource) {
        String nodeType = requestedResource.getNodeType();
        NodeResourceEntry value = nodeResourceEntryMap.get(nodeType);

        value.deallocate(requestedResource.getAmountOfCores());
    }
}

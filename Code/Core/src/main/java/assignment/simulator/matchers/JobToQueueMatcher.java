package assignment.simulator.matchers;

import assignment.configurations.simulation.QueuesConfiguration;
import assignment.configurations.simulation.objects.ConstraintResource;
import assignment.configurations.simulation.objects.QueueProperties;
import assignment.simulator.matchers.exceptions.JobToQueueMatchingException;
import assignment.simulator.objects.Job;
import assignment.simulator.objects.RequestedResource;
import assignment.simulator.objects.queue.Queue;

import java.util.List;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class JobToQueueMatcher {
    private List<Queue> queues;

    public JobToQueueMatcher(List<Queue> queues) {
        this.queues = queues;
    }

    public Queue match(Job job) throws JobToQueueMatchingException {
        Long executionTime = job.getExecutionTime();
        List<RequestedResource> requestedResourceList = job.getRequestedResourceList();

        for (Queue queue : queues) {
            QueueProperties queueProperties = queue.getQueueProperties();
            if (executionTime <= queueProperties.getMaximumExecutionTime()) {
                if (checkResourcesListConstraints(requestedResourceList, queueProperties.getConstraintResources())) {
                    return queue;
                }
            }
        }

        throw new JobToQueueMatchingException();
    }

    public boolean checkResourcesListConstraints(List<RequestedResource> requestedResourceList, List<ConstraintResource> constraintResources) {
        for (RequestedResource requestedResource : requestedResourceList) {
            if(!checkSingleResourceConstraint(requestedResource, constraintResources)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkSingleResourceConstraint(RequestedResource requestedResource, List<ConstraintResource> constraintResources) {
        for (ConstraintResource constraintResource : constraintResources) {
            if (requestedResource.getNodeType().equals(constraintResource.getNodeType())) {
                return requestedResource.getAmountOfCores() <= constraintResource.getAmountOfCores();
            }
        }
        return false;
    }
}

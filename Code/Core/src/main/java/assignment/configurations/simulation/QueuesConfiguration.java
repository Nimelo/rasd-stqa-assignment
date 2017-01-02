package assignment.configurations.simulation;

import assignment.configurations.simulation.exceptions.ValidationException;
import assignment.configurations.simulation.interfaces.IValidate;
import assignment.configurations.simulation.objects.QueueProperties;

import java.util.List;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class QueuesConfiguration {
    private List<QueueProperties> queues;

    public List<QueueProperties> getQueues() {
        return queues;
    }

    public void setQueues(List<QueueProperties> queues) {
        this.queues = queues;
    }
}

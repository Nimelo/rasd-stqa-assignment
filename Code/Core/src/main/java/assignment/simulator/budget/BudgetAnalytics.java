package assignment.simulator.budget;

import assignment.configurations.simulation.Configuration;
import assignment.configurations.simulation.SystemResources;
import assignment.configurations.simulation.objects.Node;
import assignment.configurations.simulation.objects.QueueProperties;
import assignment.simulator.objects.Job;
import assignment.simulator.objects.RequestedResource;
import assignment.simulator.objects.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
public class BudgetAnalytics {
    private List<User> users;
    private Configuration configuration;

    public BudgetAnalytics(List<User> users, Configuration configuration) {
        this.users = users;
        this.configuration = configuration;
    }

    public BigDecimal calculatePrice(Job job, String queueName) {
        BigDecimal price = new BigDecimal(0);
        List<RequestedResource> requestedResourceList = job.getRequestedResourceList();

        for (RequestedResource requestedResource : requestedResourceList) {
            price.add(calculatePriceResource(requestedResource, queueName, job.getExecutionTime()));
        }

        return price;
    }

    private BigDecimal calculatePriceResource(RequestedResource requestedResource, String queueName, Long executionTime) {
        BigDecimal bigDecimal = configuration.getSystemResources().getNodes().stream()
                .filter(x -> x.getName().equals(requestedResource.getNodeType()))
                .map(Node::getPrice)
                .findFirst()
                .get();

        Double priceFactor = configuration.getQueuesConfiguration().getQueues().stream()
                .filter(x -> x.getName().equals(queueName))
                .map(QueueProperties::getPriceFactor)
                .findFirst()
                .get();

        BigDecimal price = bigDecimal.multiply(new BigDecimal(priceFactor))
                .multiply(new BigDecimal(requestedResource.getAmountOfCores()))
                .multiply(new BigDecimal(executionTime));

        return price;
    }
}

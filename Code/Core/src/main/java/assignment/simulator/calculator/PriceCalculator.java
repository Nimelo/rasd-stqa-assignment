package assignment.simulator.calculator;

import assignment.configurations.simulation.objects.Node;
import assignment.simulator.objects.Job;
import assignment.simulator.objects.RequestedResource;
import assignment.simulator.objects.queue.Queue;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
public class PriceCalculator {
    private List<Node> nodes;

    public PriceCalculator(List<Node> nodes) {
        this.nodes = nodes;
    }

    public BigDecimal calculatePrice(Job job, Double priceFactor) {
        BigDecimal price = new BigDecimal(0);
        List<RequestedResource> requestedResourceList = job.getRequestedResourceList();

        for (RequestedResource requestedResource : requestedResourceList) {
            price = price.add(calculatePriceResource(requestedResource, priceFactor, job.getExecutionTime()));
        }

        return price;
    }

    public BigDecimal calculatePriceResource(RequestedResource requestedResource, Double priceFactor, Long executionTime) {
        BigDecimal bigDecimal = nodes.stream()
                .filter(x -> x.getName().equals(requestedResource.getNodeType()))
                .map(Node::getPrice)
                .findFirst()
                .get();

        BigDecimal price = bigDecimal.multiply(new BigDecimal(priceFactor))
                .multiply(new BigDecimal(requestedResource.getAmountOfCores()))
                .multiply(new BigDecimal(executionTime));

        return price;
    }
}

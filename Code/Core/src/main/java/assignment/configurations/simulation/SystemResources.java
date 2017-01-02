package assignment.configurations.simulation;

import assignment.configurations.simulation.exceptions.ValidationException;
import assignment.configurations.simulation.interfaces.IValidate;
import assignment.configurations.simulation.objects.Node;

import java.util.List;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class SystemResources implements IValidate{
    private List<Node> nodes;

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public void validate() throws ValidationException {
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                if (i != j) {
                    Node current = nodes.get(i);
                    Node toCheck = nodes.get(j);

                    if (current.getName().equals(toCheck.getName())) {
                        throw new ValidationException("Multiple occurences of node names.", Node.class);
                    }
                }
            }
        }
    }
}

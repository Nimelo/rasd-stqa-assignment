package assignment.events;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by mrnim on 23-Dec-16.
 */
public class GenericCustomerEvent<EventArgumentsType> implements IGenericEvent<Consumer<EventArgumentsType>, EventArgumentsType> {
    private final Set<Consumer<EventArgumentsType>> listeners;

    public GenericCustomerEvent(Set<Consumer<EventArgumentsType>> listeners) {
        this.listeners = listeners;
    }

    @Override
    public void addListener(Consumer<EventArgumentsType> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void broadcast(EventArgumentsType args) {
        this.listeners.forEach(x -> x.accept(args));
    }
}

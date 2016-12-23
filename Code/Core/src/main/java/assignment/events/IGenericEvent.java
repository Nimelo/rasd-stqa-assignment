package assignment.events;

/**
 * Created by mrnim on 23-Dec-16.
 */
public interface IGenericEvent<ListenerType, EventArgumentsType> {
    void addListener(ListenerType listener);

    void broadcast(EventArgumentsType args);
}

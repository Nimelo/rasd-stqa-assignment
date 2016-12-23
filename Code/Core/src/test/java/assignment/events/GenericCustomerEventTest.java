package assignment.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by mrnim on 23-Dec-16.
 */
class GenericCustomerEventTest {
    private Set<Consumer<Integer>> listeners;
    private GenericCustomerEvent<Integer> event;
    @BeforeEach
    void setUp() {
         listeners = new HashSet<>();
         event = new GenericCustomerEvent<>(listeners);
    }

    @Test
    void addListener() {
        event.addListener(x -> {});

        assertEquals(1, listeners.size());
    }

    @Test
    void broadcast() {
        Collection<Integer> collection = new ArrayList<>();

        event.addListener(x -> collection.add(x));

        event.broadcast(12);
        event.broadcast(45);

        assertArrayEquals(new Integer[]{12, 45}, collection.toArray());
    }

}
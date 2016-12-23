package assignment.events.timing;

import assignment.events.GenericCustomerEvent;
import assignment.events.timing.arguments.MetronomeEventArgs;
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
class MetronomeTest {
    private Set<Consumer<MetronomeEventArgs>> listeners;
    private Metronome metronome;
    Collection<Long> collection;

    private static Long counter;
    private static Long limit;
    private static Long getNextNumber(){
        if(counter > limit)
            return counter;
        return ++counter;
    }

    @BeforeEach
    void setUp() {
        counter = 0L;
        listeners = new HashSet<>();
        metronome = new Metronome(listeners, new Integer(1), 0L, 0L, MetronomeTest::getNextNumber);
        collection = new ArrayList<>();
        metronome.addListener(x -> collection.add(x.getCurrentTick()));
    }

    @Test
    void start() throws InterruptedException {
        limit = 10L;

        metronome.start();
        while(counter < limit) ;
        metronome.stop();

        assertEquals((int)(long)limit, collection.size());
    }

    @Test
    void stop() {
        limit = 5L;

        metronome.start();
        while(counter < limit) ;
        metronome.stop();
        limit = 100L;

        assertEquals(5, collection.size());
    }
}
package assignment.events.timing.ranged;

import assignment.events.timing.Metronome;
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
class RangedMetronomeTest {
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
        metronome = new RangedMetronome(listeners, new Integer(1), 0L, 0L, RangedMetronomeTest::getNextNumber, 50L);
        collection = new ArrayList<>();
        metronome.addListener(x -> collection.add(x.getCurrentTick()));
    }

    @Test
    void run() throws InterruptedException {
        limit = 1000L;
        metronome.start();
        Thread.sleep(1000L);
        metronome.stop();

        assertEquals(50, collection.size());
    }

}
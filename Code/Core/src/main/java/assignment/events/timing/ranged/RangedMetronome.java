package assignment.events.timing.ranged;

import assignment.events.timing.Metronome;
import assignment.events.timing.arguments.MetronomeEventArgs;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by mrnim on 23-Dec-16.
 */
public class RangedMetronome extends Metronome {
    private Long tickCap;

    public RangedMetronome(Set<Consumer<MetronomeEventArgs>> listeners, Integer interval, Long currentTick, Long lastTimeMillis, Supplier<Long> currentTimeSupplier, Long tickCap) {
        super(listeners, interval, currentTick, lastTimeMillis, currentTimeSupplier);
        this.tickCap = tickCap;
    }

    @Override
    public void run() {
        while(currentTick < tickCap){
            this.performSingleIteration();
        }
    }
}

package assignment.events.timing;

import assignment.context.LifeCycle;
import assignment.events.GenericCustomerEvent;
import assignment.events.timing.arguments.MetronomeEventArgs;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by mrnim on 23-Dec-16.
 */
public class Metronome extends GenericCustomerEvent<MetronomeEventArgs> implements LifeCycle, Runnable {
    protected Integer interval;
    protected Long currentTick;

    protected Thread thread;
    protected Long lastTimeMillis;

    protected Supplier<Long> currentTimeSupplier;

    public Metronome(Set<Consumer<MetronomeEventArgs>> listeners, Integer interval, Long currentTick, Long lastTimeMillis, Supplier<Long> currentTimeSupplier) {
        super(listeners);
        this.interval = interval;
        this.currentTick = currentTick;
        this.lastTimeMillis = lastTimeMillis;
        this.currentTimeSupplier = currentTimeSupplier;
        this.thread = new Thread(this);
    }

    @Override
    public void start() {
        this.lastTimeMillis = currentTimeSupplier.get();
        this.thread.start();
    }

    @Override
    public void stop() {
        this.thread.stop();
    }


    @Override
    public void run() {
        while(true){
            this.performSingleIteration();
        }
    }

    protected void performSingleIteration() {
        Long currentTimeInMilliSeconds = currentTimeSupplier.get();
        if(currentTimeInMilliSeconds - this.lastTimeMillis >= this.interval){
            this.lastTimeMillis = currentTimeInMilliSeconds;
            this.broadcast(new MetronomeEventArgs(++this.currentTick));
        }
    }
}

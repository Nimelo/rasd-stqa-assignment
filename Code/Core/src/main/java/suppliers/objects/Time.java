package suppliers.objects;

import assignment.events.timing.arguments.TickType;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class Time {
    private Long tick;
    private TickType tickType;
    private Long endOfCutOffTick;

    public Time(Long tick, TickType tickType, Long endOfCutOffTick) {
        this.tick = tick;
        this.tickType = tickType;
        this.endOfCutOffTick = endOfCutOffTick;
    }

    public Long getTick() {
        return tick;
    }

    public TickType getTickType() {
        return tickType;
    }

    public Long getEndOfCutOffTick() {
        return endOfCutOffTick;
    }
}

package assignment.events.timing.arguments;

/**
 * Created by mrnim on 23-Dec-16.
 */
public class MetronomeEventArgs {
    private Long currentTick;
    private TickType tickType;
    private Long cutoffEndTick;

    public MetronomeEventArgs(Long currentTick, TickType tickType) {
        this.currentTick = currentTick;
        this.tickType = tickType;
    }

    public MetronomeEventArgs(Long currentTick, TickType tickType, Long cutoffEndTick) {
        this.currentTick = currentTick;
        this.tickType = tickType;
        this.cutoffEndTick = cutoffEndTick;
    }

    public Long getCurrentTick() {
        return currentTick;
    }

    public Boolean isCutoffTime() {
        return TickType.CUTOFF == tickType;
    }

    public TickType getTickType() {
        return tickType;
    }

    public Long getCutoffEndTick() {
        return cutoffEndTick;
    }
}

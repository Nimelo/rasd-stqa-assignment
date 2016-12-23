package assignment.events.timing.arguments;

/**
 * Created by mrnim on 23-Dec-16.
 */
public class MetronomeEventArgs {
    private Long currentTick;

    public MetronomeEventArgs(Long currentTick) {
        this.currentTick = currentTick;
    }

    public Long getCurrentTick() {
        return currentTick;
    }
}

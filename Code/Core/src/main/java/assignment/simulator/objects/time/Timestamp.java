package assignment.simulator.objects.time;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class Timestamp {
    private Long tick;

    public Timestamp(Long tick) {
        this.tick = tick;
    }

    public Long getTick() {
        return tick;
    }
}

package suppliers;

import suppliers.interfaces.ITimeSupplier;
import suppliers.objects.Time;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class TimeSupplier implements ITimeSupplier{
    private Long cutoffStartTimeStamp;
    private Long curoffEndTimeStamp;
    private Long currentTick;
    private Long interval;

    public TimeSupplier(Long cutoffStartTimeStamp, Long curoffEndTimeStamp, Long currentTick, Long interval) {
        this.cutoffStartTimeStamp = cutoffStartTimeStamp;
        this.curoffEndTimeStamp = curoffEndTimeStamp;
        this.currentTick = currentTick;
        this.interval = interval;
    }

    @Override
    public Time get() {
        Long nextTick = this.getNextTick();
        //if(nextTick % cu)
        return null;
    }

    public Long getNextTick(){
        return this.currentTick += interval;
    }
}

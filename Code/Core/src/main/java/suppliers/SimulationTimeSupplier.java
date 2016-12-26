package suppliers;

import assignment.events.timing.arguments.TickType;
import suppliers.interfaces.ITimeSupplier;
import suppliers.objects.Time;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class SimulationTimeSupplier implements ITimeSupplier{
    private Long weekendStartTime;
    private Long weekendEndTime;
    private Long currentTick;
    private Long interval;
    private Long maxExecutionTime;
    private Long rangeOfDays;

    public SimulationTimeSupplier(Long weekendStartTime, Long weekendEndTime, Long currentTick, Long interval, Long maxExecutionTime, Long rangeOfDays) {
        this.weekendStartTime = weekendStartTime;
        this.weekendEndTime = weekendEndTime;
        this.currentTick = currentTick;
        this.interval = interval;
        this.maxExecutionTime = maxExecutionTime;
        this.rangeOfDays = rangeOfDays;
    }

    @Override
    public Time get() {
        Long currentTick = this.getNextTick();
        if(isWeekend(currentTick)){
            return new Time(currentTick, TickType.WEEKEND, null);
        }else{
            Long tmp = findNextStartOfWeekendTick();
            Long timeToWeekend = tmp - currentTick;
            if(timeToWeekend < maxExecutionTime){
                return new Time(currentTick, TickType.CUTOFF, timeToWeekend);
            }else{
                return new Time(currentTick, TickType.WEEK, null);
            }
        }
    }

    private boolean isWeekend(Long currentTick) {
        Long tmp = currentTick % rangeOfDays;
        if(tmp >= weekendStartTime
                && tmp <= weekendEndTime){
            return true;
        }
        return false;
    }

    public Long findNextStartOfWeekendTick() {
        Long divisor = currentTick / rangeOfDays;
        if(divisor == 0){
            return new Long(weekendStartTime);
        }else{
            if(isWeekend(currentTick)){
                return new Long(weekendStartTime + rangeOfDays * (divisor + 1));
            }else{
                Long tmp = currentTick % rangeOfDays;
                if(tmp > weekendEndTime){
                    return new Long(weekendStartTime + rangeOfDays * (divisor + 1));
                }else{
                    return new Long(weekendStartTime + rangeOfDays * divisor);
                }
            }
        }
    }

    public Long getNextTick(){
        return this.currentTick += interval;
    }
}

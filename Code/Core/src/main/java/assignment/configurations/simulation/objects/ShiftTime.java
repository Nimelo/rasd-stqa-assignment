package assignment.configurations.simulation.objects;

import java.time.DayOfWeek;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class ShiftTime{
    private DayOfWeek dayOfWeek;
    private Long hours;
    private Long minutes;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Long getMinutes() {
        return minutes;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    public Long toWeekTick() {
        return dayOfWeek.getValue() * 24 * 60 * 60 + hours * 60 * 60 + minutes * 60;
    }
}

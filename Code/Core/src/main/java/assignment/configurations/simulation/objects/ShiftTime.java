package assignment.configurations.simulation.objects;

import assignment.configurations.simulation.exceptions.ValidationException;
import assignment.configurations.simulation.interfaces.IValidate;

import java.time.DayOfWeek;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class ShiftTime implements IValidate{
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

    @Override
    public void validate() throws ValidationException {
        if (hours < 0L || hours > 24)
            new ValidationException("Incorrect hour.", AvailabilityTime.class);
        if (minutes < 0L || minutes > 60L)
            new ValidationException("Incorrect minutes.", AvailabilityTime.class);
    }
}

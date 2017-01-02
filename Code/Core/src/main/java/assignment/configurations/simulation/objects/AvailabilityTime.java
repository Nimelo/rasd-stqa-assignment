package assignment.configurations.simulation.objects;

import assignment.configurations.simulation.exceptions.ValidationException;
import assignment.configurations.simulation.interfaces.IValidate;

import java.time.DayOfWeek;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class AvailabilityTime implements IValidate{
    private ShiftTime begin;
    private ShiftTime end;

    @Override
    public void validate() throws ValidationException {
        begin.validate();
        end.validate();
    }

    public ShiftTime getBegin() {
        return begin;
    }

    public void setBegin(ShiftTime begin) {
        this.begin = begin;
    }

    public ShiftTime getEnd() {
        return end;
    }

    public void setEnd(ShiftTime end) {
        this.end = end;
    }
}

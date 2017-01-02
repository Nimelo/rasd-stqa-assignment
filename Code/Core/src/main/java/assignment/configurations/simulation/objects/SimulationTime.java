package assignment.configurations.simulation.objects;

import assignment.configurations.simulation.exceptions.ValidationException;
import assignment.configurations.simulation.interfaces.IValidate;

import java.util.Date;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class SimulationTime implements IValidate{
    private Date begin;
    private Date end;

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public void validate() throws ValidationException {
        if (!end.after(begin))
            throw new ValidationException("End of simulation is before start", SimulationTime.class);
    }
}

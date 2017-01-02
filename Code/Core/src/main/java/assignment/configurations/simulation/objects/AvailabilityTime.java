package assignment.configurations.simulation.objects;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class AvailabilityTime{
    private ShiftTime begin;
    private ShiftTime end;

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

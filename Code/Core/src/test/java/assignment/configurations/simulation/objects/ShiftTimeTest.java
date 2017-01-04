package assignment.configurations.simulation.objects;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
class ShiftTimeTest {
    @Test
    void toWeekTick() {
        ShiftTime shiftTime = new ShiftTime();
        shiftTime.setHours(1L);
        shiftTime.setMinutes(0L);
        shiftTime.setDayOfWeek(DayOfWeek.FRIDAY);

        assertEquals(new Long(4 * 24 * 60 * 60 + 1 * 60 * 60), shiftTime.toWeekTick());
    }

}
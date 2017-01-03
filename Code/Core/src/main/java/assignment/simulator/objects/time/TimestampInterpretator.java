package assignment.simulator.objects.time;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class TimestampInterpretator {
    public static final Long MILLISECOND = 1000L;
    public static final Long SECOND = 1L;
    public static final Long MINUTE = 60 * SECOND;
    public static final Long HOUR = 60 * MINUTE;
    public static final Long DAY = 24 * HOUR;
    public static final Long WEEK = 7 * DAY;

    private Date begin;
    private Date end;

    private Long totalBeginSeconds;
    private Long totalEndSeconds;

    private DayOfWeek beginDayOfWeek;

    public TimestampInterpretator(Date begin, Date end) {
        this.begin = begin;
        this.end = end;
        this.totalBeginSeconds = begin.getTime() / MILLISECOND;
        this.totalEndSeconds = end.getTime() / MILLISECOND;
        this.beginDayOfWeek = getDayOfWeekFromDate(begin);
    }

    public static DayOfWeek getDayOfWeekFromDate(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return DayOfWeek.of(instance.get(Calendar.DAY_OF_WEEK));
    }

    public static DayOfWeek getDayOfWeekFromTick(Long tick) {
        long weekTick = tick % WEEK;
        long dayOfWeek = weekTick / DAY;

        return DayOfWeek.of((int) dayOfWeek);
    }

    public static Long getHoursFromTick(Long tick) {
        long weekTick = tick % WEEK;
        long dayOfWeek = weekTick / DAY;

        return (weekTick - dayOfWeek * DAY) / HOUR;
    }

    public static Long getMinutesFromTick(Long tick) {
        long weekTick = tick % WEEK;
        long dayOfWeek = weekTick / DAY;
        long hours = (weekTick - dayOfWeek * DAY) / HOUR;

        return ((weekTick - dayOfWeek * DAY) - hours * HOUR) / MINUTE;
    }

}

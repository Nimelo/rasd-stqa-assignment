package suppliers.objects;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class DayTime {
    private Long dayOfWeek;
    private Long month;
    private Long year;
    private Long hour;
    private Long minutes;

    public DayTime(Long dayOfWeek, Long month, Long year, Long hour, Long minutes) {
        this.dayOfWeek = dayOfWeek;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minutes = minutes;
    }

    public DayTime(Long dayOfWeek, Long month, Long year) {
        this.dayOfWeek = dayOfWeek;
        this.month = month;
        this.year = year;
        this.hour = 24L;
        this.minutes = 0L;
    }
}

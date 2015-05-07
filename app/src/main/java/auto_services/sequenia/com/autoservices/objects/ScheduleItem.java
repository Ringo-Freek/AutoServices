package auto_services.sequenia.com.autoservices.objects;

/**
 * Created by chybakut2004 on 07.05.15.
 */
public class ScheduleItem {
    private int id;
    private String from;
    private String to;
    private String day_of_week;
    private boolean is_reserved;

    public boolean isIs_reserved() {
        return is_reserved;
    }

    public void setIs_reserved(boolean is_reserved) {
        this.is_reserved = is_reserved;
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package auto_services.sequenia.com.autoservices.objects;

/**
 * Created by chybakut2004 on 07.05.15.
 */
public class Reservation {
    private int id;
    private int box_number;
    private int user_id;
    private int reservation_time_id;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getReservation_time_id() {
        return reservation_time_id;
    }

    public void setReservation_time_id(int reservation_time_id) {
        this.reservation_time_id = reservation_time_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBox_number() {
        return box_number;
    }

    public void setBox_number(int box_number) {
        this.box_number = box_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

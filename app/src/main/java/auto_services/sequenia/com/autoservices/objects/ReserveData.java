package auto_services.sequenia.com.autoservices.objects;

/**
 * Created by chybakut2004 on 07.05.15.
 */
public class ReserveData {
    private String auth_token;
    private String date;
    private int reservation_time_id;

    public int getReservation_time_id() {
        return reservation_time_id;
    }

    public void setReservation_time_id(int reservation_time_id) {
        this.reservation_time_id = reservation_time_id;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

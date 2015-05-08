package auto_services.sequenia.com.autoservices.objects;

/**
 * Created by chybakut2004 on 07.05.15.
 */
public class ReserveData {
    private String auth_token;
    private String date;
    private int reservation_time_id;
    private String name;
    private String body_type;
    private String registration_number;
    private Integer car_mark_id;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBody_type() {
        return body_type;
    }

    public void setBody_type(String body_type) {
        this.body_type = body_type;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public int getCar_mark_id() {
        return car_mark_id;
    }

    public void setCar_mark_id(Integer car_mark_id) {
        this.car_mark_id = car_mark_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

package auto_services.sequenia.com.autoservices.objects;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public class MyCarCreationData {
    private String auth_token;
    private Integer car_mark_id;
    private String registration_number;
    private String body_type;

    public MyCarCreationData(String auth_token, Integer car_mark_id, String registration_number, String body_type) {
        this.auth_token = auth_token;
        this.car_mark_id = car_mark_id;
        this.registration_number = registration_number;
        this.body_type = body_type;
    }

    public String getBody_type() {
        return body_type;
    }

    public void setBody_type(String body_type) {
        this.body_type = body_type;
    }

    public int getCar_mark_id() {
        return car_mark_id;
    }

    public void setCar_mark_id(int car_mark_id) {
        this.car_mark_id = car_mark_id;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }
}

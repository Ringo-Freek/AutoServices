package auto_services.sequenia.com.autoservices.objects;

/**
 * Created by chybakut2004 on 03.05.15.
 */
public class Car {
    public static final String MINI = "mini";
    public static final String SEDAN = "sedan";
    public static final String SUV = "suv";
    public static final String MINIBUS = "minibus";

    private int id;
    private String body_type;
    private String registration_number;
    private int car_mark_id;
    private String car_mark_name;
    private boolean isCurrent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getCar_mark_name() {
        return car_mark_name;
    }

    public void setCar_mark_name(String car_mark_name) {
        this.car_mark_name = car_mark_name;
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

    public void setCar_mark_id(int car_mark_id) {
        this.car_mark_id = car_mark_id;
    }
}

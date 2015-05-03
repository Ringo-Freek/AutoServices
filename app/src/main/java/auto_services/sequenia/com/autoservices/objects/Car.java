package auto_services.sequenia.com.autoservices.objects;

/**
 * Created by chybakut2004 on 03.05.15.
 */
public class Car {
    public static final int MINI = 0;
    public static final int SEDAN = 1;
    public static final int SUV = 2;
    public static final int MINIBUS = 3;

    private int id;
    private int body_type;
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

    public int getBody_type() {
        return body_type;
    }

    public void setBody_type(int body_type) {
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

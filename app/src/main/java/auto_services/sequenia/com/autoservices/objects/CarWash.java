package auto_services.sequenia.com.autoservices.objects;

import java.util.ArrayList;

/**
 * Created by Ringo on 28.04.2015.
 * Формат мойки (данные с сервера)
 */
public class CarWash {
    private Integer id;
    private String name;
    private String item_type;
    private Float latitude;
    private Float longitude;
    private Integer boxes_count;
    private String address;
    private String phone;
    private String time_from;
    private String time_to;
    private Float rating;
    private String image;
    private ArrayList<Services> services;
    private String[] features;
    private Integer reviews_count;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getItem_type() {
        return item_type;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Integer getBoxes_count() {
        return boxes_count;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getTime_from() {
        return time_from;
    }

    public String getTime_to() {
        return time_to;
    }

    public Float getRating() {
        return rating;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<Services> getServices() {
        return services;
    }

    public String[] getFeatures() {
        return features;
    }

    public Integer getReviews_count() {
        return reviews_count;
    }
}

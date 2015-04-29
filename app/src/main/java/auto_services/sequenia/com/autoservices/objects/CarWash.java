package auto_services.sequenia.com.autoservices.objects;

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
}

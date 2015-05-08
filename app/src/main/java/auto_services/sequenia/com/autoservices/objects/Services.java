package auto_services.sequenia.com.autoservices.objects;

/**
 * Created by Ringo on 03.05.2015.
 * Класс содержит информацию об услугах
 */
public class Services{
    private Integer id;
    private String name;
    private Float price;
    private String description;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}

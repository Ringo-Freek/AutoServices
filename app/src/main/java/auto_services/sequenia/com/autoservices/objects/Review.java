package auto_services.sequenia.com.autoservices.objects;

/**
 * Created by Ringo on 03.05.2015.
 * Структура превью отзыва
 */
public class Review {
    private Integer mark;
    private Integer id;
    private Integer user_id;
    private String text;
    private String created_at;
    private String user_name;

    public Integer getMark() {
        return mark;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public String getText() {
        return text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUser_name() {
        return user_name;
    }
}

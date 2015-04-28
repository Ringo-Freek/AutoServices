package auto_services.sequenia.com.autoservices;

import java.util.ArrayList;

/**
 * Created by Ringo on 28.04.2015.
 * Формат ответа от сервера
 */
public class JsonResponse<T> {
    private Boolean success;
    private String info;
    private ArrayList<T> data;

    public Boolean getSuccess() {
        return success;
    }

    public String getInfo() {
        return info;
    }

    public ArrayList<T> getData() {
        return data;
    }
}

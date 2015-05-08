package com.sequenia.autoservices.responses;

/**
 * Created by Ringo on 28.04.2015.
 * Формат ответа от сервера
 */
public class JsonResponse<T> {
    private Boolean success;
    private String info;
    private T data;

    public Boolean getSuccess() {
        return success;
    }

    public String getInfo() {
        return info;
    }

    public T getData() {
        return data;
    }
}

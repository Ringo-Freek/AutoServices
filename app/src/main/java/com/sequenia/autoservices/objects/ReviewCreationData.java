package com.sequenia.autoservices.objects;

/**
 * Created by chybakut2004 on 11.05.15.
 */
public class ReviewCreationData {
    private String auth_token;
    private int mark;
    private String name;
    private int item_id;
    private String text;

    public ReviewCreationData(String auth_token, int mark, String name, int item_id, String text) {
        this.auth_token = auth_token;
        this.mark = mark;
        this.name = name;
        this.item_id = item_id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

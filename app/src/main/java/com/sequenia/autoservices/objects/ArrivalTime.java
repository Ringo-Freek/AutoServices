package com.sequenia.autoservices.objects;

/**
 * Created by chybakut2004 on 13.05.15.
 */
public class ArrivalTime {
    private String value;
    private String text;
    private String time_zone;

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

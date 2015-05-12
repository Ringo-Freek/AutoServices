package com.sequenia.autoservices.objects;

import io.realm.RealmObject;

/**
 * Created by chybakut2004 on 12.05.15.
 */
public class HistoryCarWash extends RealmObject {

    private int carWashId;
    private String address;
    private String name;
    private String time_from;
    private String time_to;
    private long date;
    private float latitude;
    private float longitude;
    private String time;
    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getCarWashId() {
        return carWashId;
    }

    public void setCarWashId(int carWashId) {
        this.carWashId = carWashId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime_from() {
        return time_from;
    }

    public void setTime_from(String time_from) {
        this.time_from = time_from;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getTime_to() {
        return time_to;
    }

    public void setTime_to(String time_to) {
        this.time_to = time_to;
    }
}

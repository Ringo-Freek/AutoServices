package com.sequenia.autoservices.objects;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 13.05.15.
 */
public class Leg {

    private ArrayList<Step> steps;
    private Distance distance;
    private Duration duration;
    private ArrivalTime arrival_time;
    private DirectionLocation start_location;
    private DirectionLocation end_location;
    private String start_address;

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public DirectionLocation getEnd_location() {
        return end_location;
    }

    public void setEnd_location(DirectionLocation end_location) {
        this.end_location = end_location;
    }

    public DirectionLocation getStart_location() {
        return start_location;
    }

    public void setStart_location(DirectionLocation start_location) {
        this.start_location = start_location;
    }

    public ArrivalTime getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(ArrivalTime arrival_time) {
        this.arrival_time = arrival_time;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    private String end_address;
}

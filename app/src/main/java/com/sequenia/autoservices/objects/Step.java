package com.sequenia.autoservices.objects;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 13.05.15.
 */
public class Step {
    private String html_instructions;
    private Distance distance;
    private Duration duration;
    private DirectionLocation start_location;
    private DirectionLocation end_location;

    public ArrayList<Step> getSub_steps() {
        return sub_steps;
    }

    public void setSub_steps(ArrayList<Step> sub_steps) {
        this.sub_steps = sub_steps;
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

    public String getHtml_instructions() {
        return html_instructions;
    }

    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }

    private ArrayList<Step> sub_steps;
}

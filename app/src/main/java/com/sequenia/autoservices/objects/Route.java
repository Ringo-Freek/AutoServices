package com.sequenia.autoservices.objects;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 13.05.15.
 */
public class Route {
    private String summary;
    private ArrayList<Leg> legs;
    private ArrayList<Integer> waypoint_order;
    private OverviewPolyline overview_polyline;
    private String copyrights;
    private Bounds bounds;

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(ArrayList<String> warnings) {
        this.warnings = warnings;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public OverviewPolyline getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(OverviewPolyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public ArrayList<Integer> getWaypoint_order() {
        return waypoint_order;
    }

    public void setWaypoint_order(ArrayList<Integer> waypoint_order) {
        this.waypoint_order = waypoint_order;
    }

    public ArrayList<Leg> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<Leg> legs) {
        this.legs = legs;
    }

    private ArrayList<String> warnings;
}

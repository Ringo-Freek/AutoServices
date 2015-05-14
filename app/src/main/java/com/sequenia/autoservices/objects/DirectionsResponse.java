package com.sequenia.autoservices.objects;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 13.05.15.
 */
public class DirectionsResponse {
    private String status;
    private ArrayList<Route> routes;

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

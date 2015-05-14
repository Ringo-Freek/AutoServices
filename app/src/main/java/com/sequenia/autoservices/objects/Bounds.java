package com.sequenia.autoservices.objects;

/**
 * Created by chybakut2004 on 13.05.15.
 */
public class Bounds {

    private DirectionLocation southwest;
    private DirectionLocation northeast;

    public DirectionLocation getNortheast() {
        return northeast;
    }

    public void setNortheast(DirectionLocation northeast) {
        this.northeast = northeast;
    }

    public DirectionLocation getSouthwest() {
        return southwest;
    }

    public void setSouthwest(DirectionLocation southwest) {
        this.southwest = southwest;
    }
}

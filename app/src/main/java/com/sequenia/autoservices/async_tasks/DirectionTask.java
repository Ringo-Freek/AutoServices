package com.sequenia.autoservices.async_tasks;

import com.google.gson.Gson;
import com.sequenia.autoservices.objects.DirectionsResponse;
import com.sequenia.autoservices.static_classes.Global;

import java.util.Locale;

/**
 * Created by chybakut2004 on 13.05.15.
 */
public abstract class DirectionTask extends AsyncTaskGet {

    public DirectionTask(double positionLat, double positionLng, double destLat, double destLng) {
        super("");
        setUrl(String.format(Locale.ENGLISH, "%s?origin=%f,%f&destination=%f,%f&sensor=true&mode=driving",
                Global.DIRECTIONS_API_PATH, positionLat, positionLng,
                destLat, destLng));
    }

    @Override
    public void initNotConnection() {

    }

    @Override
    public void initRuntimeError() {

    }

    @Override
    public void initErrorLoadData() {

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s != null) {
            DirectionsResponse response = new Gson().fromJson(s, DirectionsResponse.class);
            onSuccess(response);
        } else {
            onError();
        }
    }

    public abstract void onSuccess(DirectionsResponse response);
    public abstract void onError();
}

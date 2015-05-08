package com.sequenia.autoservices.async_tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.sequenia.autoservices.objects.Reservation;
import com.sequenia.autoservices.objects.ReserveData;
import com.sequenia.autoservices.responses.JsonResponse;

/**
 * Created by chybakut2004 on 07.05.15.
 */
public abstract class ReserveTask extends AsyncTaskPost {
    public ReserveTask(ReserveData data) {
        super("reservations/reserve.json", new Gson().toJson(data));
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
        if(s != null){
            JsonResponse<Reservation> response = new Gson().fromJson(s, new TypeToken<JsonResponse<Reservation>>(){}.getType());
            if(response.getSuccess()){
                onSuccess(response.getData());
            } else {
                onError();
            }
        }
    }

    public abstract void onSuccess(Reservation reservation);
    public abstract void onError();
}

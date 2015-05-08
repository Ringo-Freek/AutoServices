package com.sequenia.autoservices.async_tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import com.sequenia.autoservices.Global;
import com.sequenia.autoservices.objects.CarWash;
import com.sequenia.autoservices.responses.JsonResponse;

/**
 * Created by chybakut2004 on 29.04.15.
 * Запрос на сервер - возвращает все мойки вблизи
 */
public abstract class NearCarWashesTask extends AsyncTaskGet {

    public NearCarWashesTask(float latitude, float longitude, int radius) {
        super("items/nearest.json?auth_token=123&latitude="
                + latitude
                + "&longitude="
                + longitude
                + "&radius="
                + radius
                + "&auth_token="
                + Global.testToken);
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
            JsonResponse<ArrayList<CarWash>> carWash = new Gson().fromJson(s, new TypeToken<JsonResponse<ArrayList<CarWash>>>(){}.getType());
            if(carWash.getSuccess()){
                onSuccess(carWash.getData());
            }
        }
    }

    public abstract void onSuccess(ArrayList<CarWash> carWashes);
}

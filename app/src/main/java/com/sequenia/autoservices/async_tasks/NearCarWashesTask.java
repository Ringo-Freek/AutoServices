package com.sequenia.autoservices.async_tasks;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.sequenia.autoservices.Global;
import com.sequenia.autoservices.objects.CarWash;
import com.sequenia.autoservices.responses.JsonResponse;

/**
 * Created by chybakut2004 on 29.04.15.
 * Запрос на сервер - возвращает все мойки вблизи
 */
public abstract class NearCarWashesTask extends AsyncTaskGet {

    public NearCarWashesTask(Context context, float latitude, float longitude) {

        super(String.format(Locale.ENGLISH, "items/nearest.json?auth_token=%s&latitude=%f&longitude=%f&radius=%d&date=%d&rating=%d" +
                "&has_cafe=%b&has_tea=%b&has_wifi=%b&has_bank_transfer=%b&has_actions=%b&only_online_reservation=%b",
                Global.testToken, latitude, longitude, Global.getRadius(context), new Date().getTime(),
                Global.getRating(context), Global.getFilter(context, 0), Global.getFilter(context, 1),
                Global.getFilter(context, 2), Global.getFilter(context, 3), Global.getFilter(context, 4),
                Global.getFilter(context, 5)));
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

package com.sequenia.autoservices.async_tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.sequenia.autoservices.objects.Car;
import com.sequenia.autoservices.responses.JsonResponse;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public abstract class MyCarDeleteTask extends AsyncTaskDelete {

    public MyCarDeleteTask(int id, String token) {
        super("user_cars/" + id + ".json?auth_token=" + token);
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
            JsonResponse<Car> carJsonResponse = new Gson().fromJson(s, new TypeToken<JsonResponse<Car>>(){}.getType());
            if(carJsonResponse.getSuccess()){
                onSuccess(carJsonResponse.getData());
            }
        }
    }

    public abstract void onSuccess(Car car);
}

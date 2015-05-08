package com.sequenia.autoservices.async_tasks;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import com.sequenia.autoservices.Global;
import com.sequenia.autoservices.objects.CarMark;
import com.sequenia.autoservices.responses.JsonResponse;
import com.sequenia.autoservices.static_classes.RealmHelper;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public abstract class CarMarksTask extends AsyncTaskGet {

    public CarMarksTask() {
        super("car_marks.json?auth_token=" + Global.testToken);
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
            JsonResponse<ArrayList<CarMark>> response = new GsonBuilder()
                .setExclusionStrategies(RealmHelper.getExclusionStrategy()).create()
                .fromJson(s, new TypeToken<JsonResponse<ArrayList<CarMark>>>() {
                }.getType());
            if(response.getSuccess()){
                onSuccess(response.getData());
            }
        } else {

        }
    }

    public abstract void onSuccess(ArrayList<CarMark> carMarks);
    public abstract void onError();
}

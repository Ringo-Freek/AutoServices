package com.sequenia.autoservices.async_tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import com.sequenia.autoservices.static_classes.Global;
import com.sequenia.autoservices.objects.ScheduleItem;
import com.sequenia.autoservices.responses.JsonResponse;

/**
 * Created by chybakut2004 on 07.05.15.
 */
public abstract class ScheduleTask extends AsyncTaskGet {

    public ScheduleTask(int itemId, long date) {
        super(String.format("items/reservation_schedule.json?auth_token=%s&date=%d&id=%d",
                Global.testToken, date, itemId));
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
            JsonResponse<ArrayList<ScheduleItem>> scheduleResponse = new Gson().fromJson(s, new TypeToken<JsonResponse<ArrayList<ScheduleItem>>>(){}.getType());
            if(scheduleResponse.getSuccess()){
                onSuccess(scheduleResponse.getData());
            }
        }
    }

    public abstract void onSuccess(ArrayList<ScheduleItem> schedule);
}

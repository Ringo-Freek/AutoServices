package com.sequenia.autoservices.async_tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sequenia.autoservices.objects.Car;
import com.sequenia.autoservices.objects.Review;
import com.sequenia.autoservices.objects.ReviewCreationData;
import com.sequenia.autoservices.responses.JsonResponse;

/**
 * Created by chybakut2004 on 11.05.15.
 */
public abstract  class ReviewCreationTask extends AsyncTaskPost {

    public ReviewCreationTask(ReviewCreationData data) {
        super("reviews.json", new Gson().toJson(data));
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
            JsonResponse<Review> response = new Gson().fromJson(s, new TypeToken<JsonResponse<Review>>(){}.getType());
            if(response.getSuccess()){
                onSuccess(response.getData());
            }
        }
    }

    public abstract void onSuccess(Review review);
    public abstract void onError();
}

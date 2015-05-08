package com.sequenia.autoservices.async_tasks;

/**
 * Created by Ringo on 03.05.2015.
 */
public class CarWashTask extends AsyncTaskGet{

    public CarWashTask(String url) {
        super(url);
        System.out.println(url);
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
}

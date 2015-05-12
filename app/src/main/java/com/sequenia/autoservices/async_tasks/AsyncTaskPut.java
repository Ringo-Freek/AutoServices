package com.sequenia.autoservices.async_tasks;

import com.sequenia.autoservices.static_classes.Global;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public abstract class AsyncTaskPut extends AsyncTaskRequest {

    String jsonData;

    public AsyncTaskPut(String url, String jsonData){
        super(url);
        this.jsonData = jsonData;
        System.out.println(jsonData);
    }

    public String doRequest() throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPut put = new HttpPut(url);

        StringEntity se = new StringEntity(Global.toUTF8(jsonData));
        put.setEntity(se);

        put.setHeader("Accept", "application/json");
        put.setHeader("Content-Type", "application/json");

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        return client.execute(put, responseHandler);
    }
}

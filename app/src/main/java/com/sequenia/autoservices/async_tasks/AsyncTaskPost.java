package com.sequenia.autoservices.async_tasks;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public abstract class AsyncTaskPost extends AsyncTaskRequest {

    String jsonData;

    public AsyncTaskPost(String url, String jsonData){
        super(url);
        this.jsonData = jsonData;
        System.out.println(jsonData);
    }

    public String doRequest() throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        StringEntity se = new StringEntity(jsonData);
        post.setEntity(se);

        post.setHeader("Accept", "application/json");
        post.setHeader("Content-Type", "application/json");

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        return client.execute(post, responseHandler);
    }
}

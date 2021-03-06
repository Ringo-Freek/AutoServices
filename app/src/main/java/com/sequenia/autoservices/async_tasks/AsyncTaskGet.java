package com.sequenia.autoservices.async_tasks;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ringo on 28.04.2015.
 * Гетовский запрос с отслеживанием ошибок
 */
public abstract class AsyncTaskGet extends AsyncTaskRequest{

    public AsyncTaskGet(String url){
        super(url);
    }

    public String doRequest() throws IOException {
        HttpGet request = new HttpGet(url);

        HttpClient httpclient = new DefaultHttpClient();

        HttpResponse httpResponse = httpclient.execute(request);
        InputStream inputStream = httpResponse.getEntity().getContent();
        StringBuffer buffer = new StringBuffer();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        return buffer.toString();
    }
}

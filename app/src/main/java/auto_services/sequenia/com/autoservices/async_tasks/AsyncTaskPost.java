package auto_services.sequenia.com.autoservices.async_tasks;

import android.os.AsyncTask;

import com.google.gson.JsonSyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import auto_services.sequenia.com.autoservices.Global;

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

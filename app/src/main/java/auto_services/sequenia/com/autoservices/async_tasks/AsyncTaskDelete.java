package auto_services.sequenia.com.autoservices.async_tasks;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public abstract class AsyncTaskDelete extends AsyncTaskRequest {

    public AsyncTaskDelete(String url){
        super(url);
    }

    public String doRequest() throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete(url);

        delete.setHeader("Accept", "application/json");
        delete.setHeader("Content-Type", "application/json");

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        return client.execute(delete, responseHandler);
    }
}

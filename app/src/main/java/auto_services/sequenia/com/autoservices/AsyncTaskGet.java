package auto_services.sequenia.com.autoservices;

import android.os.AsyncTask;

import com.google.gson.JsonSyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Ringo on 28.04.2015.
 * Гетовский запрос с отслеживанием ошибок
 */
public abstract class AsyncTaskGet extends AsyncTask<Void, Void, String>{

    String url = Global.host;
    int typeError;

    public AsyncTaskGet(String url){
        this.url += url;
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

    public String initRequest(){
        String str = null;
        try{
            str = doRequest();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            typeError = 2;
            publishProgress();
            return null;
        }catch (ConnectTimeoutException e){
            e.printStackTrace();
            typeError = 2;
            publishProgress();
            return null;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            typeError = 1;
            publishProgress();
            return null;
            // Нет подключения к интернету
        } catch (UnknownHostException e) {
            e.printStackTrace();
            typeError = 0;
            publishProgress();
            return null;
            // Не может считать поток
        } catch (IOException e) {
            e.printStackTrace();
            typeError = 1;
            publishProgress();
            return null;
            // Время ожидания от сервера истекло
        } catch (RuntimeException e) {
            e.printStackTrace();
            typeError = 2;
            publishProgress();
            return null;
            // Остальные ошибки
        } catch (Exception e) {
            e.printStackTrace();
            typeError = 1;
            publishProgress();
            return null;
        } finally {
            if(typeError > -1){
                return str;
            }
        }

        return str;
    }

    @Override
    protected String doInBackground(Void... params) {
        return initRequest();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        switch (typeError){
            case 0:
                initNotConnection();
                break;
            case 2:
                initRuntimeError();
                break;
            case 1:
                initErrorLoadData();
                break;
        }
    }

    public abstract void initNotConnection();
    public abstract void initRuntimeError();
    public abstract void initErrorLoadData();
}

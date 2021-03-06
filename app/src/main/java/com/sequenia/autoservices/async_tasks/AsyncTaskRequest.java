package com.sequenia.autoservices.async_tasks;

import android.os.AsyncTask;

import com.google.gson.JsonSyntaxException;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import com.sequenia.autoservices.static_classes.Global;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public abstract class AsyncTaskRequest extends AsyncTask<Void, Void, String> {

    String url = Global.host;
    int typeError;

    public AsyncTaskRequest(String url){
        this.url += url;
        System.out.println(this.url);
    }

    public abstract String doRequest() throws IOException;

    public String initRequest(){
        String str = null;
        try{
            str = doRequest();
            System.out.println(str);
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

    public void setUrl(String url) {
        this.url = url;
        System.out.println(this.url);
    }
}

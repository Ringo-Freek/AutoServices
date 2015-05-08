package auto_services.sequenia.com.autoservices.async_tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import auto_services.sequenia.com.autoservices.objects.Car;
import auto_services.sequenia.com.autoservices.responses.JsonResponse;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public abstract class MyCarUpdateTask extends AsyncTaskPut {
    public MyCarUpdateTask(int id, String jsonData) {
        super("user_cars/" + id + ".json", jsonData);
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
            JsonResponse<Car> carJsonResponse = new Gson().fromJson(s, new TypeToken<JsonResponse<Car>>(){}.getType());
            if(carJsonResponse.getSuccess()){
                onSuccess(carJsonResponse.getData());
            }
        }
    }

    public abstract void onSuccess(Car car);
}

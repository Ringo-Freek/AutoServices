package auto_services.sequenia.com.autoservices.async_tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import auto_services.sequenia.com.autoservices.Global;
import auto_services.sequenia.com.autoservices.objects.Car;
import auto_services.sequenia.com.autoservices.responses.JsonResponse;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public abstract class MyCarsTask extends AsyncTaskGet {

    public MyCarsTask() {
        super("user_cars.json?auth_token=" + Global.testToken);
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
            JsonResponse<ArrayList<Car>> carsJsonResponse = new Gson().fromJson(s, new TypeToken<JsonResponse<ArrayList<Car>>>(){}.getType());
            if(carsJsonResponse.getSuccess()){
                onSuccess(carsJsonResponse.getData());
            }
        }
    }

    public abstract void onSuccess(ArrayList<Car> cars);
}

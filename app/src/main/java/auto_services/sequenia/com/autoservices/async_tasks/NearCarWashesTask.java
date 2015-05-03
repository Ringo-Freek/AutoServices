package auto_services.sequenia.com.autoservices.async_tasks;

import auto_services.sequenia.com.autoservices.Global;

/**
 * Created by chybakut2004 on 29.04.15.
 * Запрос на сервер - возвращает все мойки вблизи
 */
public abstract class NearCarWashesTask extends AsyncTaskGet {

    public NearCarWashesTask(float latitude, float longitude, int radius) {
        super("items/nearest.json?auth_token=123&latitude="
                + latitude
                + "&longitude="
                + longitude
                + "&radius="
                + radius
                + "&auth_token="
                + Global.testToken);
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
        super.onPostExecute(s);
    }
}

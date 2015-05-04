package auto_services.sequenia.com.autoservices.async_tasks;

/**
 * Created by Ringo on 03.05.2015.
 */
public class ReviewsTask extends AsyncTaskGet{

    public ReviewsTask(String url) {
        super(url);
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
}

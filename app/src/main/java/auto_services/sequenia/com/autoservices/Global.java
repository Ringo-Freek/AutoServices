package auto_services.sequenia.com.autoservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import auto_services.sequenia.com.autoservices.async_tasks.CarMarksTask;
import auto_services.sequenia.com.autoservices.objects.CarMark;
import auto_services.sequenia.com.autoservices.static_classes.RealmHelper;

/**
 * Created by Ringo on 28.04.2015.
 * Глобальные значения
 */
public class Global {
    public static final String host = "http://188.226.140.45/api/v1/";
    public static final String testToken = "mfrhGMakKqqk11Wwx5N-";
    public static final Integer radius = 5000;

    public static final float startLatitude = 55.0167f;
    public static final float startLongitude = 82.9333f;
    public static final int startZoom = 10;
    public static final int myPositionZoom = 16;

    private static final String SHARED_PREF_NAME = "Preferences";
    private static final String PREF_CAR_MARKS_LOADED = "CarMarksLoaded";

    public static void loadCarMarksIfNeeds(final Context context) {
        if(!carMarksLoaded(context)) {
            final ProgressDialog pd = new ProgressDialog(context);
            pd.setMessage("Загрузка моделей");
            pd.setCancelable(false);
            pd.show();

            new CarMarksTask() {
                @Override
                public void onSuccess(ArrayList<CarMark> carMarks) {
                    RealmHelper.updateCarMarks(context, carMarks);
                    setCarMarksLoaded(context, true);
                    pd.dismiss();
                }

                @Override
                public void onError() {
                    pd.dismiss();
                }
            }.execute();
        }
    }

    private static boolean carMarksLoaded(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(PREF_CAR_MARKS_LOADED, false);
    }

    private static void setCarMarksLoaded(Context context, boolean loaded) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(PREF_CAR_MARKS_LOADED, loaded);
        editor.commit();
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }
}

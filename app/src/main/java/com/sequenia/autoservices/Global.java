package com.sequenia.autoservices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;

import java.util.ArrayList;

import com.sequenia.autoservices.async_tasks.CarMarksTask;
import com.sequenia.autoservices.objects.CarMark;
import com.sequenia.autoservices.static_classes.RealmHelper;

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
    private static final String PREF_NAME = "Name";
    private static final String PREF_PHONE = "Phone";

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

    public static void setName(Context context, String name) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PREF_NAME, name);
        editor.commit();
    }

    public static void setPhone(Context context, String phone) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PREF_PHONE, phone);
        editor.commit();
    }

    public static String getName(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(PREF_NAME, "");
    }

    public static String getPhone(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(PREF_PHONE, "");
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static ArrayList<String> getBodyTypes(Context context) {
        Resources resources = context.getResources();
        ArrayList<String> bodyTypes = new ArrayList<String>();

        bodyTypes.add(resources.getString(R.string.mini));
        bodyTypes.add(resources.getString(R.string.sedan));
        bodyTypes.add(resources.getString(R.string.suv));
        bodyTypes.add(resources.getString(R.string.minibus));

        return bodyTypes;
    }

    public static ArrayList<Integer> getBodyTypeIcons(Context context) {
        Resources resources = context.getResources();
        ArrayList<Integer> bodyTypes = new ArrayList<Integer>();

        bodyTypes.add(R.drawable.ic_mini);
        bodyTypes.add(R.drawable.ic_sedan);
        bodyTypes.add(R.drawable.ic_vnedorojnik);
        bodyTypes.add(R.drawable.ic_microavtobus);

        return bodyTypes;
    }

    public static LayoutInflater getInflaterForTheme(Context context, int theme) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, theme);
        return ((Activity) context).getLayoutInflater().cloneInContext(contextThemeWrapper);
    }
}

package com.sequenia.autoservices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sequenia.autoservices.async_tasks.CarMarksTask;
import com.sequenia.autoservices.listeners.OnLoadListener;
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
    private static final String PREF_REGISTERED = "Registered";

    private static final int MAX_NAME_LENGTH = 50;
    private static final String PHONE_FORMAT = "\\A\\+7\\d{10}\\z";

    // Строки ошибок
    private static String ERROR_NULL_RATING = "Поставьте оценку";
    private static String ERROR_RATING_FORMAT = "Оценка не может быть больше 5";
    private static String ERROR_PHONE_FORMAT = "Введите номер телефона в международном формате (+79123456780)";
    private static String ERROR_NULL_PHONE = "Введите номер телефона";
    private static String ERROR_NULL_NAME = "Введите имя";
    private static String ERROR_NAME_LENGTH = "Имя не должно быть длиннее " + MAX_NAME_LENGTH + " символов";

    public static void loadCarMarksIfNeeds(final Context context, final OnLoadListener onLoadListener) {
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
                    if(onLoadListener != null) {
                        onLoadListener.onLoad();
                    }
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

    public static boolean isRegistered(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(PREF_REGISTERED, false);
    }

    public static void setRegistered(Context context, boolean registered) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(PREF_REGISTERED, registered);
        editor.commit();
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

    public static LayoutInflater getInflaterForTheme(Context context, int theme) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, theme);
        return ((Activity) context).getLayoutInflater().cloneInContext(contextThemeWrapper);
    }

    private static boolean validate(Context context, ArrayList<String> errors) {
        if(errors.size() > 0) {
            showMessage(context, errors.get(0));
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateName(Context context, String name) {
        return validate(context, getNameErrors(name));
    }

    public static boolean validateNotNullName(Context context, String name) {
        return validate(context, getNotNullNameErrors(name));
    }

    public static boolean validateRating(Context context, int rating) {
        return validate(context, getRatingErrors(rating));
    }

    public static boolean validatePhone(Context context, String phone) {
        return validate(context, getPhoneErrors(phone));
    }

    public static ArrayList<String> getRatingErrors(int rating) {
        ArrayList<String> errors = new ArrayList<String>();

        if(rating < 1) {
            errors.add(ERROR_NULL_RATING);
        } else {
            if(rating > 5) {
                errors.add(ERROR_RATING_FORMAT);
            }
        }

        return errors;
    }

    public static ArrayList<String> getNameErrors(String name) {
        ArrayList<String> errors = new ArrayList<String>();

        if(name != null) {
            if (name.length() > MAX_NAME_LENGTH) {
                errors.add(ERROR_NAME_LENGTH);
            }
        }

        return errors;
    }

    public static ArrayList<String> getNotNullNameErrors(String name) {
        ArrayList<String> errors = new ArrayList<String>();

        if(name == null) {
            errors.add(ERROR_NULL_NAME);
        } else {
            if(name.length() == 0) {
                errors.add(ERROR_NULL_NAME);
            } else {
                if (name.length() > MAX_NAME_LENGTH) {
                    errors.add(ERROR_NAME_LENGTH);
                }
            }
        }

        return errors;
    }

    public static ArrayList<String> getPhoneErrors(String phone) {
        ArrayList<String> errors = new ArrayList<String>();

        if(phone == null) {
            errors.add(ERROR_NULL_PHONE);
        } else {
            if(phone.length() == 0) {
                errors.add(ERROR_NULL_PHONE);
            } else {
                Pattern pattern = Pattern.compile(PHONE_FORMAT);
                Matcher matcher = pattern.matcher(phone);
                if(!matcher.matches()) {
                    errors.add(ERROR_PHONE_FORMAT);
                }
            }
        }

        return errors;
    }


    public static void showMessage(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static String toUTF8(String s) throws UnsupportedEncodingException {
        return new String(s.getBytes("UTF-8"), "ISO-8859-1");
    }
}

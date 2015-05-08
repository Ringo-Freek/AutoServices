package com.sequenia.autoservices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final int MAX_NAME_LENGTH = 50;
    private static final int PHONE_LENGTH = 10;
    private static final String REGISTRATION_NUMBER_FORMAT = "\\A[a-zA-Zа-яА-Я]\\d\\d\\d[a-zA-Zа-яА-Я][a-zA-Zа-яА-Я]\\z";

    // Строки ошибок
    private static String ERROR_NULL_NAME = "Введите имя";
    private static String ERROR_NULL_PHONE = "Введите телефон";
    private static String ERROR_NULL_CAR_MARK = "Выберите марку автомобиля";
    private static String ERROR_NULL_BODY_TYPE = "Выберите тип кузова";
    private static String ERROR_NULL_REGISTRATION_NUMBER = "Введите регистрационный номер";
    private static String ERROR_PHONE_FORMAT = "Телефон введен неверно";
    private static String ERROR_REGISTRATION_NUMBER_FORMAT = "Регистрационный номер введен неверно";
    private static String ERROR_NAME_LENGTH = "Имя не должно быть длиннее " + MAX_NAME_LENGTH + " символов";

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

    public static boolean validatePhone(Context context, String phone) {
        return validate(context, getPhoneErrors(phone));
    }

    public static boolean validateCarMarkId(Context context, Integer carMarkId) {
        return validate(context, getCarMarkIdErrors(carMarkId));
    }

    public static boolean validateBodyType(Context context, String bodyType) {
        return validate(context, getBodyTypeErrors(bodyType));
    }

    public static boolean validateRegistrationNumber(Context context, String registrationNumber) {
        return validate(context, getRegistrationNumberErrors(registrationNumber));
    }

    public static ArrayList<String> getNameErrors(String name) {
        ArrayList<String> errors = new ArrayList<String>();

        if(name == null) {
            errors.add(ERROR_NAME_LENGTH);
        } else {
            if (name.length() > MAX_NAME_LENGTH) {
                errors.add(ERROR_NAME_LENGTH);
            }

            if (name.length() == 0) {
                errors.add(ERROR_NULL_NAME);
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
                if(phone.length() != PHONE_LENGTH) {
                    errors.add(ERROR_PHONE_FORMAT);
                }
            }
        }

        return errors;
    }

    public static ArrayList<String> getCarMarkIdErrors(Integer carMarkId) {
        ArrayList<String> errors = new ArrayList<String>();

        if(carMarkId == null) {
            errors.add(ERROR_NULL_CAR_MARK);
        }

        return errors;
    }

    public static ArrayList<String> getBodyTypeErrors(String bodyType) {
        ArrayList<String> errors = new ArrayList<String>();

        if(bodyType == null) {
            errors.add(ERROR_NULL_BODY_TYPE);
        }

        return errors;
    }

    public static ArrayList<String> getRegistrationNumberErrors(String registrationNumber) {
        ArrayList<String> errors = new ArrayList<String>();

        if(registrationNumber == null) {
            errors.add(ERROR_NULL_REGISTRATION_NUMBER);
        } else {
            if(registrationNumber.length() == 0) {
                errors.add(ERROR_NULL_REGISTRATION_NUMBER);
            } else {
                Pattern pattern = Pattern.compile(REGISTRATION_NUMBER_FORMAT);
                Matcher matcher = pattern.matcher(registrationNumber);
                if(!matcher.matches()) {
                    errors.add(ERROR_REGISTRATION_NUMBER_FORMAT);
                }
            }
        }

        return errors;
    }

    public static void showMessage(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}

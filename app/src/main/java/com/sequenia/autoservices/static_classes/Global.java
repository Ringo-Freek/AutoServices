package com.sequenia.autoservices.static_classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sequenia.autoservices.activities.MainActivity;
import com.sequenia.autoservices.async_tasks.CarMarksTask;
import com.sequenia.autoservices.drawer_fragments.PlaceholderFragment;
import com.sequenia.autoservices.fragments.CurrentReservationFragment;
import com.sequenia.autoservices.listeners.OnLoadListener;
import com.sequenia.autoservices.objects.CarMark;
import com.sequenia.autoservices.objects.HistoryCarWash;

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
    private static final String PREF_FILTERED = "Filtered";

    private static final String[] PREF_FILTERS = {
            "HasCafe", "HasTea", "HasWiFi", "HasBankTransfer", "HasActions", "OnlyOnlineReservation"
    };

    private static final String PREF_FILTER_RATING = "FilterRating";
    private static final String PREF_FILTER_COAST = "FilterCoast";
    private static final String PREF_FILTER_RADIUS = "FilterRadius";

    private static final int MAX_NAME_LENGTH = 50;
    private static final String PHONE_FORMAT = "\\A\\+7\\d{10}\\z";

    // Строки ошибок
    private static String ERROR_NULL_RATING = "Поставьте оценку";
    private static String ERROR_RATING_FORMAT = "Оценка не может быть больше 5";
    private static String ERROR_PHONE_FORMAT = "Введите номер телефона в международном формате (+79123456780)";
    private static String ERROR_NULL_PHONE = "Введите номер телефона";
    private static String ERROR_NULL_BODY_TYPE = "Выберите тип кузова";
    private static String ERROR_NULL_NAME = "Введите имя";
    private static String ERROR_NULL_CAR_MARK = "Выберите марку автомобиля";
    private static String ERROR_NULL_REGISTRATION_NUMBER = "Выберите регистрационный номер";
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

    public static boolean isFiltered(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(PREF_FILTERED, false);
    }

    public static void setRegistered(Context context, boolean registered) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(PREF_REGISTERED, registered);
        editor.commit();
    }

    public static void setFiltered(Context context, boolean filtered) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(PREF_FILTERED, filtered);
        editor.commit();
    }

    public static void setCarMarksLoaded(Context context, boolean loaded) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(PREF_CAR_MARKS_LOADED, loaded);
        editor.commit();
    }

    public static void setFilter(Context context, boolean enabled, int filterIndex) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(PREF_FILTERS[filterIndex], enabled);
        editor.commit();
    }

    public static void setRadius(Context context, int radius) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(PREF_FILTER_RADIUS, radius);
        editor.commit();
    }

    public static void setCoast(Context context, int coast) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(PREF_FILTER_COAST, coast);
        editor.commit();
    }

    public static void setRating(Context context, int rating) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(PREF_FILTER_RATING, rating);
        editor.commit();
    }

    public static boolean getFilter(Context context, int filterIndex) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(PREF_FILTERS[filterIndex], false);
    }

    public static int getCoast(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getInt(PREF_FILTER_COAST, 0);
    }

    public static int getRating(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getInt(PREF_FILTER_RATING, 0);
    }

    public static int getRadius(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getInt(PREF_FILTER_RADIUS, Global.radius);
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

    public static boolean validateBodyType(Context context, String bodyType) {
        return validate(context, getBodyTypeErrors(bodyType));
    }

    public static boolean validateRegistrationNumber(Context context, String registrationNumber) {
        return validate(context, getRegistrationNumberErrors(registrationNumber));
    }

    public static boolean validateCarMarkId(Context context, Integer carMarkId) {
        return validate(context, getCarMarkIdErrors(carMarkId));
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

    public static ArrayList<String> getCarMarkIdErrors(Integer carMarkId) {
        ArrayList<String> errors = new ArrayList<String>();

        if(carMarkId == null) {
            errors.add(ERROR_NULL_CAR_MARK);
        } else {
            if(carMarkId == 0) {
                errors.add(ERROR_NULL_CAR_MARK);
            }
        }

        return errors;
    }

    public static ArrayList<String> getBodyTypeErrors(String bodyType) {
        ArrayList<String> errors = new ArrayList<String>();

        if(bodyType == null) {
            errors.add(ERROR_NULL_BODY_TYPE);
        } else {
            if(bodyType.length() == 0) {
                errors.add(ERROR_NULL_BODY_TYPE);
            }
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

    public static boolean isDaysEqual(Calendar dateNow, Calendar dateReview){
        if(dateNow.get(Calendar.MONTH) == dateReview.get(Calendar.MONTH)
                && dateNow.get(Calendar.DAY_OF_MONTH) == dateReview.get(Calendar.DAY_OF_MONTH)
                && dateNow.get(Calendar.YEAR) == dateReview.get(Calendar.YEAR)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * true, если прошел час после переданной даты
     *
     * @param date
     * @return
     */
    public static boolean dateExpired(Calendar date) {
        Calendar now = Calendar.getInstance();
        Calendar d = Calendar.getInstance();
        d.setTimeInMillis(date.getTimeInMillis());
        d.add(Calendar.HOUR_OF_DAY, 1);

        return now.getTimeInMillis() > d.getTimeInMillis();
    }

    public static String getSchedule(String timeFrom, String timeTo){
        if(timeFrom != null && timeTo != null){
            if(timeFrom.equals("00:00")&&timeTo.equals("23:59")){
                return "круглосуточно";
            }else{
                return timeFrom + " - " + timeTo;
            }
        }else{
            return "сейчас не работает";
        }
    }

    /**
     * Возвращает текущую запись. Null, если сейчас никуда не записаны.
     * @param context
     * @return
     */
    public static HistoryCarWash getCurrentReservation(Context context) {
        HistoryCarWash currentReservation = RealmHelper.getLastHistoryRecord(context);

        if(currentReservation != null) {
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(currentReservation.getDate());
            if(dateExpired(date)) {
                currentReservation = null;
            }
        }

        return currentReservation;
    }

    public static void showCurrentReservationFragment(PlaceholderFragment f) {
        CurrentReservationFragment fragment = (CurrentReservationFragment) PlaceholderFragment.newInstance(PlaceholderFragment.CURRENT_RESERVATION_SECTION);
        ((MainActivity) f.getActivity()).addSubFragment(fragment);
    }
}

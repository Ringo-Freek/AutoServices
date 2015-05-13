package com.sequenia.autoservices.static_classes;

import android.content.Context;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.util.ArrayList;

import com.sequenia.autoservices.objects.Car;
import com.sequenia.autoservices.objects.CarMark;
import com.sequenia.autoservices.objects.HistoryCarWash;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public class RealmHelper {

    private static final String REALM_NAME = "AutoServicesDataBase";

    public static Realm initRealm(Context context) {
        return Realm.getInstance(context, REALM_NAME);
    }

    public static ExclusionStrategy getExclusionStrategy() {
        return new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };
    }

    public static void updateCarMarks(Context context, ArrayList<CarMark> carMarks) {
        Realm realm = initRealm(context);

        realm.beginTransaction();

        realm.copyToRealmOrUpdate(carMarks);

        realm.commitTransaction();
    }

    public static RealmResults<CarMark> getCarMarks(Context context) {
        return initRealm(context).where(CarMark.class).findAllSorted("name", true);
    }

    public static RealmResults<Car> getCars(Context context) {
        Realm realm = initRealm(context);
        return realm.where(Car.class).findAllSorted("id", true);
    }

    public static CarMark getCarMarkById(Context context, int id) {
        return initRealm(context).where(CarMark.class).equalTo("id", id).findFirst();
    }

    public static Car getCarById(Context context, int carId) {
        return initRealm(context).where(Car.class).equalTo("id", carId).findFirst();
    }

    public static void updateOrCreateCar(Context context, Car car) {
        Realm realm = initRealm(context);

        realm.beginTransaction();

        realm.copyToRealmOrUpdate(car);

        realm.commitTransaction();
    }

    public static int getNextCarIndex(Context context) {
        RealmResults<Car> cars = getCars(context);

        if(cars.size() == 0) {
            return 1;
        } else {
            return cars.get(cars.size() - 1).getId() + 1;
        }
    }

    public static void deleteCar(Context context, int carId) {
        Realm realm = initRealm(context);

        realm.beginTransaction();

        Car car = realm.where(Car.class).equalTo("id", carId).findFirst();
        if(car != null) {
            car.removeFromRealm();
        }

        realm.commitTransaction();
    }

    public static int getCarsCount(Context context) {
        return initRealm(context).where(Car.class).findAll().size();
    }

    public static Car getCurrentCar(Context context) {
        return initRealm(context).where(Car.class).equalTo("isCurrent", true).findFirst();
    }

    public static void saveCarWash(Context context, HistoryCarWash carWash) {
        Realm realm = initRealm(context);

        realm.beginTransaction();

        realm.copyToRealm(carWash);

        realm.commitTransaction();
    }

    public static RealmResults<HistoryCarWash> getHistory(Context context) {
        return initRealm(context).where(HistoryCarWash.class).findAllSorted("date", false);
    }

    public static HistoryCarWash getLastHistoryRecord(Context context) {
        return initRealm(context).where(HistoryCarWash.class).findAllSorted("date", false).first();
    }

    public static void removeReservation(Context context, HistoryCarWash reservation) {
        Realm realm = initRealm(context);

        realm.beginTransaction();

        reservation.removeFromRealm();

        realm.commitTransaction();
    }
}

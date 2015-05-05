package auto_services.sequenia.com.autoservices.static_classes;

import android.content.Context;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.util.ArrayList;

import auto_services.sequenia.com.autoservices.objects.CarMark;
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
}
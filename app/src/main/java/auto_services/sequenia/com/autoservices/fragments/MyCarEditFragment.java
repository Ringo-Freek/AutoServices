package auto_services.sequenia.com.autoservices.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import auto_services.sequenia.com.autoservices.Global;
import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.adapters.BodyTypeSpinnerAdapter;
import auto_services.sequenia.com.autoservices.adapters.CarMarksSpinnerAdapter;
import auto_services.sequenia.com.autoservices.async_tasks.MyCarCreationTask;
import auto_services.sequenia.com.autoservices.async_tasks.MyCarDeleteTask;
import auto_services.sequenia.com.autoservices.async_tasks.MyCarUpdateTask;
import auto_services.sequenia.com.autoservices.drawer_fragments.DetailFragment;
import auto_services.sequenia.com.autoservices.objects.Car;
import auto_services.sequenia.com.autoservices.objects.CarMark;
import auto_services.sequenia.com.autoservices.objects.MyCarCreationData;
import auto_services.sequenia.com.autoservices.static_classes.RealmHelper;
import auto_services.sequenia.com.autoservices.widgets.EditTextWithLabel;
import auto_services.sequenia.com.autoservices.widgets.SpinnerWithLabel;
import io.realm.RealmResults;

/**
 * Created by chybakut2004 on 03.05.15.
 */
public class MyCarEditFragment extends DetailFragment {

    private EditTextWithLabel nameInput;
    private EditTextWithLabel phoneNumberInput;
    private SpinnerWithLabel carMarkInput;
    private EditTextWithLabel registrationNumberInput;
    private Spinner bodyTypeInput;

    private int carMarkId = 0;
    private String registrationNumber;
    private String bodyType;

    RealmResults<CarMark> carMarks;

    public MyCarEditFragment() {
        setIsMain(false);
    }

    @Override
    public View createContent(LayoutInflater inflater, LinearLayout layout) {
        View view = inflater.inflate(R.layout.fragment_my_car_edit, layout, false);

        nameInput = (EditTextWithLabel) view.findViewById(R.id.name);
        phoneNumberInput = (EditTextWithLabel) view.findViewById(R.id.phone);
        carMarkInput = (SpinnerWithLabel) view.findViewById(R.id.car_mark);
        registrationNumberInput = (EditTextWithLabel) view.findViewById(R.id.car_registration_number);
        bodyTypeInput = (Spinner) view.findViewById(R.id.body_type);

        nameInput.setLabel(view.findViewById(R.id.name_label));
        phoneNumberInput.setLabel(view.findViewById(R.id.phone_label));
        carMarkInput.setLabel(view.findViewById(R.id.car_mark_label));
        registrationNumberInput.setLabel(view.findViewById(R.id.car_registration_number_label));

        Activity activity = getActivity();
        Resources resources = activity.getResources();
        carMarks = RealmHelper.getCarMarks(activity);
        carMarkInput.setAdapter(new CarMarksSpinnerAdapter(activity, carMarks));
        bodyTypeInput.setAdapter(new BodyTypeSpinnerAdapter(activity,
                resources.getStringArray(R.array.body_types),
                resources.getStringArray(R.array.body_type_labels),
                resources.getIntArray(R.array.body_type_icons)));
        bodyTypeInput.setSelection(0);

        return view;
    }

    @Override
    public void createItem() {
        new MyCarCreationTask(new GsonBuilder().serializeNulls().create().toJson(getData())) {
            @Override
            public void onSuccess(Car car) {
                close();
            }
        }.execute();
    }

    @Override
    public void updateItem(int itemId) {
        new MyCarUpdateTask(itemId, new GsonBuilder().serializeNulls().create().toJson(getData())) {
            @Override
            public void onSuccess(Car car) {
                close();
            }
        }.execute();
    }

    @Override
    public void deleteItem(int itemId) {
        new MyCarDeleteTask(itemId, Global.testToken) {
            @Override
            public void onSuccess(Car car) {
                close();
            }
        }.execute();
    }

    @Override
    public void cancel() {
        close();
    }

    @Override
    public void getInfoFromMasterFragment(Bundle args) {
        carMarkId = args.getInt(MyCarFragment.ARG_CAR_MARK_ID, 0);
        registrationNumber = args.getString(MyCarFragment.ARG_REGISTRATION_NUMBER);
        bodyType = args.getString(MyCarFragment.ARG_BODY_TYPE);
    }

    @Override
    public void showInfo() {
        int carMarkIndex = 0;
        for(int i = 0; i < carMarks.size(); i++) {
            if(carMarkId == carMarks.get(i).getId()) {
                carMarkIndex = i + 1;
                break;
            }
        }

        carMarkInput.setSelection(carMarkIndex);
        registrationNumberInput.setText(registrationNumber);

        BodyTypeSpinnerAdapter adapter = (BodyTypeSpinnerAdapter) bodyTypeInput.getAdapter();
        String[] bodyTypes = adapter.getBodyTypes();
        for(int i = 0; i < bodyTypes.length; i++) {
            if(bodyType.equals(bodyTypes[i])) {
                bodyTypeInput.setSelection(i);
                break;
            }
        }
    }

    private MyCarCreationData getData() {
        Integer carMarkId = null;
        int carMarkIndex = carMarkInput.getSelectedItemPosition() - 1;
        if(carMarkIndex >= 0) {
            carMarkId = carMarks.get(carMarkIndex).getId();
        }
        String registrationNumber = registrationNumberInput.getText().toString();

        BodyTypeSpinnerAdapter adapter = (BodyTypeSpinnerAdapter) bodyTypeInput.getAdapter();
        String bodyType = adapter.getBodyTypes()[bodyTypeInput.getSelectedItemPosition()];

        return new MyCarCreationData(Global.testToken, carMarkId, registrationNumber, bodyType);
    }
}

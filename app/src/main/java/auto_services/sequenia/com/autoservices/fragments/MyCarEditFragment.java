package auto_services.sequenia.com.autoservices.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import auto_services.sequenia.com.autoservices.Global;
import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.async_tasks.MyCarCreationTask;
import auto_services.sequenia.com.autoservices.async_tasks.MyCarDeleteTask;
import auto_services.sequenia.com.autoservices.async_tasks.MyCarUpdateTask;
import auto_services.sequenia.com.autoservices.drawer_fragments.DetailFragment;
import auto_services.sequenia.com.autoservices.objects.Car;
import auto_services.sequenia.com.autoservices.objects.MyCarCreationData;
import auto_services.sequenia.com.autoservices.widgets.EditTextWithLabel;

/**
 * Created by chybakut2004 on 03.05.15.
 */
public class MyCarEditFragment extends DetailFragment {

    private EditTextWithLabel nameInput;
    private EditTextWithLabel phoneNumberInput;
    private EditTextWithLabel carMarkInput;
    private EditTextWithLabel registrationNumberInput;
    private EditTextWithLabel bodyTypeInput;

    private int carMarkId;
    private String carMarkName;
    private String registrationNumber;
    private String bodyType;

    public MyCarEditFragment() {
        setIsMain(false);
    }

    @Override
    public View createContent(LayoutInflater inflater, LinearLayout layout) {
        View view = inflater.inflate(R.layout.fragment_my_car_edit, layout, false);

        nameInput = (EditTextWithLabel) view.findViewById(R.id.name);
        phoneNumberInput = (EditTextWithLabel) view.findViewById(R.id.phone);
        carMarkInput = (EditTextWithLabel) view.findViewById(R.id.car_mark);
        registrationNumberInput = (EditTextWithLabel) view.findViewById(R.id.car_registration_number);
        bodyTypeInput = (EditTextWithLabel) view.findViewById(R.id.body_type);

        nameInput.setLabel(view.findViewById(R.id.name_label));
        phoneNumberInput.setLabel(view.findViewById(R.id.phone_label));
        carMarkInput.setLabel(view.findViewById(R.id.car_mark_label));
        registrationNumberInput.setLabel(view.findViewById(R.id.car_registration_number_label));
        bodyTypeInput.setLabel(view.findViewById(R.id.body_type_label));

        return view;
    }

    @Override
    public void createItem() {
        int carMarkId = 1;
        String registrationNumber = registrationNumberInput.getText().toString();
        String bodyType = "mini";

        new MyCarCreationTask(new Gson().toJson(new MyCarCreationData(Global.testToken, carMarkId, registrationNumber, bodyType))) {
            @Override
            public void onSuccess(Car car) {
                close();
            }
        }.execute();
    }

    @Override
    public void updateItem(int itemId) {
        int carMarkId = 2;
        String registrationNumber = registrationNumberInput.getText().toString();
        String bodyType = "sedan";

        new MyCarUpdateTask(itemId, new Gson().toJson(new MyCarCreationData(Global.testToken, carMarkId, registrationNumber, bodyType))) {
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
        carMarkName = args.getString(MyCarFragment.ARG_CAR_MARK_NAME);
        registrationNumber = args.getString(MyCarFragment.ARG_REGISTRATION_NUMBER);
        bodyType = args.getString(MyCarFragment.ARG_BODY_TYPE);
    }

    @Override
    public void showInfo() {
        carMarkInput.setText(carMarkName);
        registrationNumberInput.setText(registrationNumber);
        bodyTypeInput.setText(bodyType);
    }
}

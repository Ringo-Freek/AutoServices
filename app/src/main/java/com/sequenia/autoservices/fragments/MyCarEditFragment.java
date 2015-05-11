package com.sequenia.autoservices.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.sequenia.autoservices.Global;

import com.sequenia.autoservices.R;
import com.sequenia.autoservices.drawer_fragments.DetailFragment;
import com.sequenia.autoservices.objects.Car;
import com.sequenia.autoservices.static_classes.RealmHelper;
import com.sequenia.autoservices.widgets.BodyTypeSpinner;
import com.sequenia.autoservices.widgets.CarMarkSpinner;
import com.sequenia.autoservices.widgets.EditTextWithLabel;
import com.sequenia.autoservices.widgets.PhoneEditText;

/**
 * Created by chybakut2004 on 03.05.15.
 */
public class MyCarEditFragment extends DetailFragment {

    private EditTextWithLabel nameInput;
    private PhoneEditText phoneNumberInput;
    private CarMarkSpinner carMarkInput;
    private EditTextWithLabel registrationNumberInput;
    private BodyTypeSpinner bodyTypeInput;

    private int carMarkId = 0;
    private String registrationNumber;
    private String bodyType;
    private String name;
    private String phone;

    public MyCarEditFragment() {
        setIsMain(false);
    }

    @Override
    public View createContent(LayoutInflater inflater, LinearLayout layout) {
        View view = inflater.inflate(R.layout.fragment_my_car_edit, layout, false);

        nameInput = (EditTextWithLabel) view.findViewById(R.id.name);
        phoneNumberInput = (PhoneEditText) view.findViewById(R.id.phone);
        carMarkInput = (CarMarkSpinner) view.findViewById(R.id.car_mark);
        registrationNumberInput = (EditTextWithLabel) view.findViewById(R.id.car_registration_number);
        bodyTypeInput = (BodyTypeSpinner) view.findViewById(R.id.body_type);

        nameInput.setLabel(view.findViewById(R.id.name_label));
        phoneNumberInput.setLabel(view.findViewById(R.id.phone_label));
        carMarkInput.setLabel(view.findViewById(R.id.car_mark_label));
        registrationNumberInput.setLabel(view.findViewById(R.id.car_registration_number_label));

        Activity activity = getActivity();
        name = Global.getName(activity);
        phone = Global.getPhone(activity);

        nameInput.setText(name);
        phoneNumberInput.setText(phone);

        return view;
    }

    @Override
    public void createItem() {
        if(updateUserData()) {
            RealmHelper.updateOrCreateCar(getActivity(), getData(RealmHelper.getNextCarIndex(getActivity())));
            close();
        }
    }

    @Override
    public void updateItem(int itemId) {
        if(updateUserData()) {
            RealmHelper.updateOrCreateCar(getActivity(), getData(itemId));
            close();
        }
    }

    @Override
    public void deleteItem(int itemId) {
        RealmHelper.deleteCar(getActivity(), itemId);
        close();
    }

    @Override
    public void cancel() {
        close();
    }

    @Override
    public void getInfoFromMasterFragment(Bundle args) {
        carMarkId = args.getInt(MyCarsFragment.ARG_CAR_MARK_ID, 0);
        registrationNumber = args.getString(MyCarsFragment.ARG_REGISTRATION_NUMBER);
        bodyType = args.getString(MyCarsFragment.ARG_BODY_TYPE);
    }

    @Override
    public void showInfo() {
        carMarkInput.selectCarMark(carMarkId);
        registrationNumberInput.setText(registrationNumber);
        bodyTypeInput.selectBodyType(bodyType);
    }

    private boolean updateUserData() {
        Activity activity = getActivity();
        String name = nameInput.getText().toString();
        String phone = phoneNumberInput.getPhone();

        if(Global.validateName(activity, name) &&
                Global.validatePhone(activity, phone)) {

            Global.setName(activity, name);
            Global.setPhone(activity, phone);

            return true;
        } else {
            return false;
        }
    }

    private Car getData(int id) {
        Integer carMarkId = carMarkInput.getSelectedCarMarkId();
        String registrationNumber = registrationNumberInput.getText().toString();
        String bodyType = bodyTypeInput.getSelectedBodyType();

        Activity activity = getActivity();

        Car car = new Car();
        car.setId(id);
        if(carMarkId != null) {
            car.setCar_mark_id(carMarkId);
        }
        if(RealmHelper.getCarsCount(activity) == 0) {
            car.setCurrent(true);
        }
        car.setBody_type(bodyType);
        car.setRegistration_number(registrationNumber);

        Car realmCar = RealmHelper.getCarById(activity, id);
        if(realmCar != null) {
            car.setCurrent(realmCar.isCurrent());
        }

        return car;
    }
}

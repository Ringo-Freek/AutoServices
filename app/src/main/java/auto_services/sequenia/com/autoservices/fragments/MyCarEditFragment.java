package auto_services.sequenia.com.autoservices.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.GsonBuilder;

import auto_services.sequenia.com.autoservices.Global;
import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.async_tasks.MyCarCreationTask;
import auto_services.sequenia.com.autoservices.async_tasks.MyCarDeleteTask;
import auto_services.sequenia.com.autoservices.async_tasks.MyCarUpdateTask;
import auto_services.sequenia.com.autoservices.drawer_fragments.DetailFragment;
import auto_services.sequenia.com.autoservices.objects.Car;
import auto_services.sequenia.com.autoservices.objects.MyCarCreationData;
import auto_services.sequenia.com.autoservices.static_classes.RealmHelper;
import auto_services.sequenia.com.autoservices.widgets.BodyTypeSpinner;
import auto_services.sequenia.com.autoservices.widgets.CarMarkSpinner;
import auto_services.sequenia.com.autoservices.widgets.EditTextWithLabel;
import io.realm.Realm;

/**
 * Created by chybakut2004 on 03.05.15.
 */
public class MyCarEditFragment extends DetailFragment {

    private EditTextWithLabel nameInput;
    private EditTextWithLabel phoneNumberInput;
    private CarMarkSpinner carMarkInput;
    private EditTextWithLabel registrationNumberInput;
    private BodyTypeSpinner bodyTypeInput;

    private int carMarkId = 0;
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
        carMarkInput = (CarMarkSpinner) view.findViewById(R.id.car_mark);
        registrationNumberInput = (EditTextWithLabel) view.findViewById(R.id.car_registration_number);
        bodyTypeInput = (BodyTypeSpinner) view.findViewById(R.id.body_type);

        nameInput.setLabel(view.findViewById(R.id.name_label));
        phoneNumberInput.setLabel(view.findViewById(R.id.phone_label));
        carMarkInput.setLabel(view.findViewById(R.id.car_mark_label));
        registrationNumberInput.setLabel(view.findViewById(R.id.car_registration_number_label));

        return view;
    }

    @Override
    public void createItem() {
        RealmHelper.updateOrCreateCar(getActivity(), getData(RealmHelper.getNextCarIndex(getActivity())));
        close();
    }

    @Override
    public void updateItem(int itemId) {
        RealmHelper.updateOrCreateCar(getActivity(), getData(itemId));
        close();
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

    private Car getData(int id) {
        Integer carMarkId = carMarkInput.getSelectedCarMarkId();
        String registrationNumber = registrationNumberInput.getText().toString();
        String bodyType = bodyTypeInput.getSelectedBodyType();

        Car car = new Car();
        car.setId(id);
        if(carMarkId != null) {
            car.setCar_mark_id(carMarkId);
        }
        if(RealmHelper.getCarsCount(getActivity()) == 0) {
            car.setCurrent(true);
        }
        car.setBody_type(bodyType);
        car.setRegistration_number(registrationNumber);

        return car;
    }
}

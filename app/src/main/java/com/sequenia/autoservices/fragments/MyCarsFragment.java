package com.sequenia.autoservices.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.sequenia.autoservices.static_classes.Global;

import com.sequenia.autoservices.R;
import com.sequenia.autoservices.activities.MainActivity;
import com.sequenia.autoservices.drawer_fragments.MasterFragment;
import com.sequenia.autoservices.drawer_fragments.PlaceholderFragment;
import com.sequenia.autoservices.objects.Car;
import com.sequenia.autoservices.objects.CarMark;
import com.sequenia.autoservices.static_classes.RealmHelper;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by chybakut2004 on 30.04.15.
 */
public class MyCarsFragment extends MasterFragment {

    public static final String ARG_CAR_MARK_ID = "CarMarkId";
    public static final String ARG_REGISTRATION_NUMBER = "RegistrationNumber";
    public static final String ARG_BODY_TYPE = "BodyType";

    private Car currentCar;

    @Override
    public void loadObjects(int page) {
        RealmResults<Car> cars = RealmHelper.getCars(getActivity());
        ArrayList<Car> carsList = new ArrayList<Car>();
        for(Car car : cars) {
            carsList.add(car);
            if(car.isCurrent()) {
                currentCar = car;
            }
        }
        addObjects(carsList);
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup container) {
        LayoutInflater localInflater = Global.getInflaterForTheme(getActivity(), R.style.Inputs);
        View view = localInflater.inflate(R.layout.my_car_item, container, false);

        TextView mark = (TextView) view.findViewById(R.id.mark);
        TextView registrationNumber = (TextView) view.findViewById(R.id.car_registration_number);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        ImageView carType = (ImageView) view.findViewById(R.id.car_type);
        View carEditButton = view.findViewById(R.id.edit_car_button);

        return new MyCarViewHolder(view, mark, registrationNumber, checkBox, carType, carEditButton);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, final int position, final RecyclerView.Adapter adapter, Object object) {
        final MyCarViewHolder carViewHolder = (MyCarViewHolder) holder;
        final Car car = (Car) object;

        final Activity activity = getActivity();

        CarMark carMark = RealmHelper.getCarMarkById(activity, car.getCar_mark_id());
        if(carMark != null) {
            carViewHolder.mark.setText(carMark.getName());
        }
        carViewHolder.registrationNumber.setText(car.getRegistration_number());
        carViewHolder.carEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailFragment(car.getId(), position);
            }
        });

        int bodyTypeResId = 0;

        switch (car.getBody_type()) {
            case Car.MINI:
                bodyTypeResId = R.drawable.ic_mini;
                break;

            case Car.SEDAN:
                bodyTypeResId = R.drawable.ic_sedan;
                break;

            case Car.SUV:
                bodyTypeResId = R.drawable.ic_vnedorojnik;
                break;

            case Car.MINIBUS:
                bodyTypeResId = R.drawable.ic_microavtobus;
                break;
        }

        if(bodyTypeResId != 0) {
            carViewHolder.carType.setImageResource(bodyTypeResId);
        }
        carViewHolder.checkBox.setOnCheckedChangeListener(null);
        carViewHolder.checkBox.setChecked(car.isCurrent());
        carViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check(activity, car, adapter);
            }
        });
    }

    private void check(Activity activity, Car car, RecyclerView.Adapter adapter) {
        Realm realm = RealmHelper.initRealm(activity);
        realm.beginTransaction();

        if(currentCar != null) {
            currentCar.setCurrent(false);
        }
        car.setCurrent(true);

        realm.commitTransaction();

        currentCar = car;
        adapter.notifyDataSetChanged();

        updateDrawer();
    }

    private void updateDrawer() {
        ((MainActivity) getActivity()).updateDrawer();
    }

    @Override
    public int getDetailFragmentId() {
        return PlaceholderFragment.MY_CAR_EDIT_SECTION;
    }

    @Override
    public boolean hasEndlessScroll() {
        return false;
    }

    @Override
    public int scrolledToLoading() {
        return 0;
    }

    @Override
    public void setInfoToDetailFragment(Bundle args, Object object) {
        Car car = (Car) object;
        args.putString(ARG_REGISTRATION_NUMBER, car.getRegistration_number());
        args.putInt(ARG_CAR_MARK_ID, car.getCar_mark_id());
        args.putString(ARG_BODY_TYPE, car.getBody_type());
    }


    public class MyCarViewHolder extends RecyclerView.ViewHolder {

        public TextView mark;
        public TextView registrationNumber;
        public CheckBox checkBox;
        public ImageView carType;
        public View carEditButton;

        public MyCarViewHolder(View itemView, TextView mark, TextView registrationNumber, CheckBox checkBox, ImageView carType, View carEditButton) {
            super(itemView);
            this.mark = mark;
            this.registrationNumber = registrationNumber;
            this.checkBox = checkBox;
            this.carType = carType;
            this.carEditButton = carEditButton;
        }
    }
}

package auto_services.sequenia.com.autoservices.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.drawer_fragments.MasterFragment;
import auto_services.sequenia.com.autoservices.drawer_fragments.PlaceholderFragment;
import auto_services.sequenia.com.autoservices.objects.Car;

/**
 * Created by chybakut2004 on 30.04.15.
 */
public class MyCarFragment extends MasterFragment {

    private ArrayList<Object> cars;
    private Car currentCar;

    public MyCarFragment(){
        cars = new ArrayList<Object>();
        Car car;

        car = new Car();
        car.setBody_type(Car.MINI);
        car.setCar_mark_id(0);
        car.setCar_mark_name("Toyota");
        car.setRegistration_number("A 913 BC");
        car.setCurrent(true);
        cars.add(car);

        currentCar = car;

        car = new Car();
        car.setBody_type(Car.SEDAN);
        car.setCar_mark_id(1);
        car.setCar_mark_name("Mazda");
        car.setRegistration_number("D 123 EG");
        car.setCurrent(false);
        cars.add(car);

        addObjects(cars);
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.my_car_item, container, false);

        TextView mark = (TextView) view.findViewById(R.id.mark);
        TextView registrationNumber = (TextView) view.findViewById(R.id.car_registration_number);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        ImageView carType = (ImageView) view.findViewById(R.id.car_type);
        View carEditButton = view.findViewById(R.id.edit_car_button);

        return new MyCarViewHolder(view, mark, registrationNumber, checkBox, carType, carEditButton);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, int position, final RecyclerView.Adapter adapter) {
        final MyCarViewHolder carViewHolder = (MyCarViewHolder) holder;
        final Car car = (Car) cars.get(position);

        carViewHolder.mark.setText(car.getCar_mark_name());
        carViewHolder.registrationNumber.setText(car.getRegistration_number());
        carViewHolder.carEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailFragment(car.getId());
            }
        });

        int bodyTypeResId = R.drawable.ic_access_time_grey600_24dp;

        switch (car.getBody_type()) {
            case Car.MINI:
                bodyTypeResId = R.drawable.ic_access_time_grey600_24dp;
                break;

            case Car.SEDAN:
                bodyTypeResId = R.drawable.ic_access_time_grey600_24dp;
                break;

            case Car.SUV:
                bodyTypeResId = R.drawable.ic_access_time_grey600_24dp;
                break;

            case Car.MINIBUS:
                bodyTypeResId = R.drawable.ic_access_time_grey600_24dp;
                break;
        }

        carViewHolder.carType.setImageResource(bodyTypeResId);
        carViewHolder.checkBox.setOnCheckedChangeListener(null);
        carViewHolder.checkBox.setChecked(car.isCurrent());
        carViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(currentCar != null) {
                    currentCar.setCurrent(false);
                }

                car.setCurrent(true);
                currentCar = car;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getDetailFragmentId() {
        return PlaceholderFragment.MY_CAR_EDIT_SECTION;
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

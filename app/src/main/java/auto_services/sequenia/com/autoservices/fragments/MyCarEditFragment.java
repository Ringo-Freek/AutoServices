package auto_services.sequenia.com.autoservices.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.drawer_fragments.DetailFragment;

/**
 * Created by chybakut2004 on 03.05.15.
 */
public class MyCarEditFragment extends DetailFragment {

    private EditText name;
    private EditText phoneNumber;
    private EditText carMark;
    private EditText registrationNumber;
    private EditText bodyType;

    public MyCarEditFragment() {
        setIsMain(false);
    }

    @Override
    public View createContent(LayoutInflater inflater, LinearLayout layout) {
        View view = inflater.inflate(R.layout.fragment_my_car_edit, layout, false);

        name = (EditText) view.findViewById(R.id.name);
        phoneNumber = (EditText) view.findViewById(R.id.phone);
        carMark = (EditText) view.findViewById(R.id.car_mark);
        registrationNumber = (EditText) view.findViewById(R.id.car_registration_number);
        bodyType = (EditText) view.findViewById(R.id.body_type);

        return view;
    }

    @Override
    public void createItem() {

    }

    @Override
    public void updateItem(int itemId) {

    }

    @Override
    public void deleteItem(int itemId) {

    }

    @Override
    public void cancel() {
        close();
    }

    @Override
    public Object getItem(int itemId) {
        return null;
    }
}

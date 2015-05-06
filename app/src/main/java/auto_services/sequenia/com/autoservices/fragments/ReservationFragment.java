package auto_services.sequenia.com.autoservices.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.drawer_fragments.PlaceholderFragment;
import auto_services.sequenia.com.autoservices.widgets.BodyTypeSpinner;
import auto_services.sequenia.com.autoservices.widgets.CarMarkSpinner;

/**
 * Created by chybakut2004 on 30.04.15.
 */
public class ReservationFragment extends PlaceholderFragment {

    private final static String ARG_CAR_WASH_ID = "CarWashId";
    private final static String ARG_CAR_WASH_NAME = "CarWashName";
    private final static String ARG_CAR_WASH_ADDRESS = "CarWashAddress";

    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView registrationNumberTextView;
    private BodyTypeSpinner bodyTypeSpinner;
    private CarMarkSpinner carMarkSpinner;

    private int carWashId;
    private String carWashName;
    private String carWashAddress;

    public ReservationFragment() {
        setIsMain(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reservation, container, false);

        TextView carWashNameTextView = (TextView) rootView.findViewById(R.id.car_wash_name);
        TextView carWashAddressTextView = (TextView) rootView.findViewById(R.id.car_wash_address);

        carWashNameTextView.setText(carWashName);
        carWashAddressTextView.setText(carWashAddress);

        carMarkSpinner = (CarMarkSpinner) rootView.findViewById(R.id.car_mark);
        carMarkSpinner.setTextSize(16);
        carMarkSpinner.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

        bodyTypeSpinner = (BodyTypeSpinner) rootView.findViewById(R.id.body_type);
        bodyTypeSpinner.setTextSize(16);
        bodyTypeSpinner.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

        return rootView;
    }

    public void setInfo(int carWashId, String carWashName, String carWashAddress) {
        Bundle args = getArguments();
        args.putInt(ARG_CAR_WASH_ID, carWashId);
        args.putString(ARG_CAR_WASH_NAME, carWashName);
        args.putString(ARG_CAR_WASH_ADDRESS, carWashAddress);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        carWashId = args.getInt(ARG_CAR_WASH_ID);
        carWashName = args.getString(ARG_CAR_WASH_NAME);
        carWashAddress = args.getString(ARG_CAR_WASH_ADDRESS);
    }
}

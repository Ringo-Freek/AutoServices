package auto_services.sequenia.com.autoservices.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

import auto_services.sequenia.com.autoservices.Global;
import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.activities.MainActivity;
import auto_services.sequenia.com.autoservices.async_tasks.ReserveTask;
import auto_services.sequenia.com.autoservices.async_tasks.ScheduleTask;
import auto_services.sequenia.com.autoservices.drawer_fragments.PlaceholderFragment;
import auto_services.sequenia.com.autoservices.objects.Reservation;
import auto_services.sequenia.com.autoservices.objects.ReserveData;
import auto_services.sequenia.com.autoservices.objects.ScheduleItem;
import auto_services.sequenia.com.autoservices.widgets.BodyTypeSpinner;
import auto_services.sequenia.com.autoservices.widgets.CarMarkSpinner;

/**
 * Created by chybakut2004 on 30.04.15.
 */
public class ReservationFragment extends PlaceholderFragment {

    private final static String ARG_CAR_WASH_ID = "CarWashId";
    private final static String ARG_CAR_WASH_NAME = "CarWashName";
    private final static String ARG_CAR_WASH_ADDRESS = "CarWashAddress";

    private View rootView;

    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView registrationNumberTextView;
    private BodyTypeSpinner bodyTypeSpinner;
    private CarMarkSpinner carMarkSpinner;

    private LinearLayout scheduleList;
    private ProgressBar progressBar;
    private LayoutInflater localInflater;
    private Button reserveButton;

    private ScheduleItemViewHolder currentChecked = null;

    private int carWashId;
    private String carWashName;
    private String carWashAddress;

    private Resources resources;

    public ReservationFragment() {
        setIsMain(false);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_reservation, container, false);

        localInflater = Global.getInflaterForTheme(getActivity(), R.style.Inputs);

        resources = getActivity().getResources();
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);

        initButton(rootView);
        initFields(rootView);
        initSchedule(rootView);

        return rootView;
    }

    private void initButton(View rootView) {
        reserveButton = (Button) rootView.findViewById(R.id.reserve_button);
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserve();
            }
        });
    }

    private void reserve() {
        if(currentChecked != null) {
            ReserveData data = new ReserveData();
            data.setAuth_token(Global.testToken);
            data.setDate(String.valueOf(new Date().getTime()));
            data.setReservation_time_id(currentChecked.scheduleItem.getId());
            data.setBody_type(bodyTypeSpinner.getSelectedBodyType());
            data.setCar_mark_id(carMarkSpinner.getSelectedCarMarkId());
            data.setName(nameTextView.getText().toString());
            data.setRegistration_number(registrationNumberTextView.getText().toString());
            data.setPhone(phoneTextView.getText().toString());
            new ReserveTask(data) {
                @Override
                public void onSuccess(Reservation reservation) {
                    onReserveSuccess();
                }

                @Override
                public void onError() {
                    onReserveError();
                }
            }.execute();
        }
    }

    private void onReserveSuccess() {
        System.out.println("Забронировано");
        ((MainActivity) getActivity()).onBackPressed();
    }

    private void onReserveError() {
        System.out.println("Ошибка бронирования");
        initSchedule(rootView);
    }

    private void initFields(View rootView) {
        TextView carWashNameTextView = (TextView) rootView.findViewById(R.id.car_wash_name);
        TextView carWashAddressTextView = (TextView) rootView.findViewById(R.id.car_wash_address);

        carWashNameTextView.setText(carWashName);
        carWashAddressTextView.setText(carWashAddress);

        nameTextView = (TextView) rootView.findViewById(R.id.name);
        registrationNumberTextView = (TextView) rootView.findViewById(R.id.car_registration_number);
        phoneTextView = (TextView) rootView.findViewById(R.id.phone);

        carMarkSpinner = (CarMarkSpinner) rootView.findViewById(R.id.car_mark);
        carMarkSpinner.setTextSize(16);
        carMarkSpinner.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

        bodyTypeSpinner = (BodyTypeSpinner) rootView.findViewById(R.id.body_type);
        bodyTypeSpinner.setTextSize(16);
        bodyTypeSpinner.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
    }

    private void initSchedule(View rootView) {
        scheduleList = (LinearLayout) rootView.findViewById(R.id.schedule_list);
        scheduleList.removeAllViews();
        currentChecked = null;
        progressBar.setVisibility(View.VISIBLE);

        new ScheduleTask(carWashId, new Date().getTime()) {
            @Override
            public void onSuccess(ArrayList<ScheduleItem> schedule) {
                showSchedule(schedule, localInflater);
                progressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

    private void showSchedule(ArrayList<ScheduleItem> schedule, LayoutInflater inflater) {
        scheduleList.removeAllViews();
        reserveButton.setEnabled(false);
        for(ScheduleItem scheduleItem : schedule) {
            RelativeLayout scheduleItemView = (RelativeLayout) inflater.inflate(R.layout.schedule_item, scheduleList, false);

            TextView timeTextView = (TextView) scheduleItemView.findViewById(R.id.time);
            TextView textView = (TextView) scheduleItemView.findViewById(R.id.text);
            CheckBox checkBox = (CheckBox) scheduleItemView.findViewById(R.id.checkbox);

            new ScheduleItemViewHolder(scheduleItemView, textView, timeTextView, checkBox, scheduleItem);

            scheduleList.addView(scheduleItemView);
        }
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

    private class ScheduleItemViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public TextView time;
        public CheckBox checkBox;
        public ScheduleItem scheduleItem;

        public ScheduleItemViewHolder(View itemView, TextView text, TextView time, CheckBox checkBox, ScheduleItem scheduleItem) {
            super(itemView);
            this.text = text;
            this.checkBox = checkBox;
            this.time = time;
            this.scheduleItem = scheduleItem;

            reset();
            initTime();
        }

        public void initText() {
            if(scheduleItem.isIs_reserved()) {
                text.setText("Занято");
                text.setTextColor(resources.getColor(R.color.black26));
            } else {
                if(checkBox.isChecked()) {
                    text.setText("Бронь");
                    text.setTextColor(resources.getColor(R.color.amber_700));
                } else {
                    text.setText("Свободно");
                    text.setTextColor(resources.getColor(R.color.teal_300));
                }
            }
        }

        public void initTime() {
            time.setText(scheduleItem.getFrom());
        }

        public void initCheckBox() {
            final ScheduleItemViewHolder self = this;
            if(scheduleItem.isIs_reserved()) {
                checkBox.setEnabled(false);
                checkBox.setChecked(true);
            } else {
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(currentChecked != null) {
                            currentChecked.reset();
                        }

                        reserveButton.setEnabled(true);
                        checkBox.setOnCheckedChangeListener(null);
                        checkBox.setChecked(true);
                        initCheckBox();
                        initText();

                        currentChecked = self;
                    }
                });
            }
        };

        public void reset() {
            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(false);
            initCheckBox();
            initText();
        }
    }
}

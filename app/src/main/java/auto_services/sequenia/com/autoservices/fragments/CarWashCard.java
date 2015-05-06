package auto_services.sequenia.com.autoservices.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import auto_services.sequenia.com.autoservices.Global;
import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.activities.MainActivity;
import auto_services.sequenia.com.autoservices.async_tasks.CarWashTask;
import auto_services.sequenia.com.autoservices.drawer_fragments.PlaceholderDialogFragment;
import auto_services.sequenia.com.autoservices.drawer_fragments.PlaceholderFragment;
import auto_services.sequenia.com.autoservices.objects.CarWash;
import auto_services.sequenia.com.autoservices.objects.Services;
import auto_services.sequenia.com.autoservices.responses.JsonResponse;
import auto_services.sequenia.com.autoservices.widgets.ModernhBoldButton;
import auto_services.sequenia.com.autoservices.widgets.ProportionalImageView;
import auto_services.sequenia.com.autoservices.widgets.Rating;

/**
 * Created by Ringo on 29.04.2015.
 * Фрагмент карточки просмотра мойки
 */
public class CarWashCard extends PlaceholderDialogFragment {

    private static final String ARG_CAR_WASH_ID = "CarWashId";
    private static final String ARG_CAR_WASH_DISTANCE = "CarWashDistance";

    private String carWashId;
    private Float carWashDistance;
    private View rootView;
    private CarWash carWash = null;

    public CarWashCard() {
        setIsMain(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_car_wash_card, container, false);

        new CarWashTask(
                "items/"
                + carWashId
                + ".json?auth_token="
                + Global.testToken
                + "&date="
                + new Date().getTime()) {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s != null){
                    JsonResponse<CarWash> details = new Gson().fromJson(s, new TypeToken<JsonResponse<CarWash>>(){}.getType());
                    if(details.getSuccess()){
                        initDataCarWash(details.getData());
                    }
                }
            }
        }.execute();

        return rootView;
    }

    /**
     * забивка в карточку результата, полученного с сервера
     */
    public void initDataCarWash(CarWash carWash){
        this.carWash = carWash;

        Picasso
                .with(getActivity())
                .load(carWash.getImage())
                .fit()
                .centerCrop()
                .into((ProportionalImageView)rootView.findViewById(R.id.car_wash_img));

        ((TextView)rootView.findViewById(R.id.car_wash_name)).setText(carWash.getName());
        ((TextView)rootView.findViewById(R.id.car_wash_address)).setText(carWash.getAddress());
        ((TextView)rootView.findViewById(R.id.car_wash_distance)).setText("~" + Math.round(carWashDistance) + " м");
        ((TextView)rootView.findViewById(R.id.schedule)).setText(carWash.getTime_from() + " - " + carWash.getTime_to());
        ((TextView)rootView.findViewById(R.id.review)).setText(carWash.getReviews_count() + " отзывов");

        rating(Math.round(carWash.getRating()));
        btnCallPhone(carWash.getPhone());
        enroll(carWash.getServices());
        features(carWash.getFeatures());

        ((Button) rootView.findViewById(R.id.reservation_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReservationForm();
            }
        });

        ((LinearLayout) rootView.findViewById(R.id.reviews_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReviewsForm();
            }
        });
    }

    private void showReservationForm() {
        if(carWash != null) {
            ReservationFragment fragment = (ReservationFragment) PlaceholderFragment.newInstance(PlaceholderDialogFragment.RESERVATION_SECTION);
            fragment.setInfo(Integer.valueOf(carWashId), carWash.getName(), carWash.getAddress());
            ((MainActivity) getActivity()).addSubFragment(fragment);
        }
    }

    private void showReviewsForm() {
        ReviewsFragment fragment = (ReviewsFragment) PlaceholderFragment.newInstance(PlaceholderDialogFragment.REVIEWS_SECTION);
        fragment.setInfo(carWashId);
        ((MainActivity) getActivity()).addSubFragment(fragment);
    }

    public void setInfo(String carWashId, float distance) {
        Bundle args = getArguments();
        args.putString(ARG_CAR_WASH_ID, carWashId);
        args.putFloat(ARG_CAR_WASH_DISTANCE, distance);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carWashId = getArguments().getString(ARG_CAR_WASH_ID);
        carWashDistance = getArguments().getFloat(ARG_CAR_WASH_DISTANCE);
    }

    /**
     * Добавление в блок рейтинга звез в соотвествии с рейтингом
     */
    public void rating(Integer rating){

        final LinearLayout ratingLayout = ((LinearLayout)rootView.findViewById(R.id.rating));

        ((TextView)ratingLayout.findViewById(R.id.count_stars)).setText(String.valueOf(rating));

        Rating ratingView = (Rating)rootView.findViewById(R.id.stars);
        ratingView.initRating(getActivity(), rating);
    }

    /**
     * добавление услуг в блок "Услуги"
     */
    public void enroll(ArrayList<Services> enrollItems){

        final LinearLayout enrollLayout = ((LinearLayout)rootView.findViewById(R.id.enroll));
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        for(int i = 0; i < enrollItems.size(); i++ ) {
            Services service = enrollItems.get(i);
            View view = inflater.inflate(R.layout.enroll_item, null);
            ((TextView) view.findViewById(R.id.enroll_item_name)).setText(service.getName());
            ((TextView) view.findViewById(R.id.enroll_item_coast)).setText("от " + service.getPrice());
            enrollLayout.addView(view);
        }
    }

    /**
     * кнопка дозвона в мойку
     */
    public void btnCallPhone(final String phone){
        ModernhBoldButton btnPhone = (ModernhBoldButton)rootView.findViewById(R.id.car_wash_phone);
        btnPhone.setText(phone);
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
            }
        });
    }

    public void features(String[] features){
        LinearLayout carWashHeader = (LinearLayout)rootView.findViewById(R.id.car_wash_card_header);
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        for(int i = 0; i < features.length; i++) {
            View view = inflater.inflate(R.layout.features_icon, carWashHeader, false);
            ImageView icon = (ImageView)view.findViewById(R.id.feature);
            switch (features[i]) {
                case "has_cafe" :
                    icon.setImageResource(R.drawable.ic_local_restaurant_grey600_24dp);
                    break;
                case "has_tea" :
                    icon.setImageResource(R.drawable.ic_local_cafe_grey600_24dp);
                    break;
                case "has_wifi" :
                    icon.setImageResource(R.drawable.ic_network_wifi_grey600_24dp);
                    break;
                case "has_bank_transfer" :
                    icon.setImageResource(R.drawable.ic_credit_card_grey600_24dp);
                    break;
                case "has_actions" :
                    icon.setImageResource(R.drawable.ic_local_attraction_grey600_24dp);
                    break;
                case "only_online_reservation" :
                    icon.setImageResource(R.drawable.ic_dog_grey600_24dp);
                    break;
            }
            carWashHeader.addView(view);
        }
    }
}

package auto_services.sequenia.com.autoservices.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.activities.MainActivity;
import auto_services.sequenia.com.autoservices.drawer_fragment.PlaceholderFragment;
import auto_services.sequenia.com.autoservices.widgets.ModernhBoldButton;
import auto_services.sequenia.com.autoservices.widgets.ProportionalImageView;

/**
 * Created by Ringo on 29.04.2015.
 * Фрагмент карточки просмотра мойки
 */
public class CarWashCard extends PlaceholderDialogFragment {

    public CarWashCard(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_car_wash_card, container, false);

        Picasso
                .with(getActivity())
                .load("http://img-fotki.yandex.ru/get/4612/30348152.fe/0_580ed_767c2283_orig")
                .fit()
                .centerCrop()
                .into((ProportionalImageView)rootView.findViewById(R.id.car_wash_img));

        ((TextView)rootView.findViewById(R.id.car_wash_name)).setText("Купальня для машин");
        ((TextView)rootView.findViewById(R.id.car_wash_address)).setText("Адриена Лежена 17");
        ((TextView)rootView.findViewById(R.id.car_wash_distance)).setText("~3.4");

        ((ModernhBoldButton)rootView.findViewById(R.id.car_wash_phone)).setText("8-923-177-83-07");
        ((TextView)rootView.findViewById(R.id.schedule)).setText("10:00 - 22:00");

        ((TextView)rootView.findViewById(R.id.review)).setText("45 отзывов");
        ((TextView)rootView.findViewById(R.id.count_stars)).setText("4");
        return rootView;
    }
}

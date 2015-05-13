package com.sequenia.autoservices.fragments;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.sequenia.autoservices.R;
import com.sequenia.autoservices.activities.MainActivity;
import com.sequenia.autoservices.drawer_fragments.PlaceholderFragment;
import com.sequenia.autoservices.objects.HistoryCarWash;
import com.sequenia.autoservices.static_classes.Global;
import com.sequenia.autoservices.static_classes.RealmHelper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.Calendar;

import io.realm.RealmResults;

/**
 * Created by chybakut2004 on 13.05.15.
 */
public class CurrentReservationFragment extends PlaceholderFragment
        implements OnMapReadyCallback {

    private Button callButton;
    private Button cancelButton;

    private TextView distanceTextView;
    private TextView nameTextView;
    private TextView addressTextView;
    private TextView dateTextView;
    private ImageView image;

    private HistoryCarWash reservation;

    private Transformation transformation;

    public CurrentReservationFragment() {
        setIsMain(false);

        transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(Global.previewCornerRadius)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_reservation, container, false);

        reservation = Global.getCurrentReservation(getActivity());

        callButton = (Button) view.findViewById(R.id.call_button);
        cancelButton = (Button) view.findViewById(R.id.cancel_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                Global.cancelCurrentReservation(activity);
                activity.onBackPressed();
            }
        });

        distanceTextView = (TextView) view.findViewById(R.id.car_wash_distance);
        nameTextView = (TextView) view.findViewById(R.id.car_wash_name);
        addressTextView = (TextView) view.findViewById(R.id.car_wash_address);
        dateTextView = (TextView) view.findViewById(R.id.car_wash_date);
        image = (ImageView) view.findViewById(R.id.car_wash_img);

        if(reservation != null) {
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(reservation.getDate());
            Calendar now = Calendar.getInstance();

            nameTextView.setText(reservation.getName());
            addressTextView.setText(reservation.getAddress());
            dateTextView.setText(Global.getDateStr(now, date));

            Picasso
                .with(getActivity())
                .load(reservation.getPreview())
                .fit()
                .centerCrop()
                .transform(transformation)
                .into(image);
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setOnMarkerClickListener(null);

        if(reservation != null) {
            map.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_wash_free))
                    .anchor(0.0f, 1.0f)
                    .position(new LatLng(reservation.getLatitude(), reservation.getLongitude())));

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(reservation.getLatitude(), reservation.getLongitude()), Global.myPositionZoom));
        }

        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if(reservation != null) {
                    distanceTextView.setText("~" + Math.round(Global.getDistance(
                            reservation.getLatitude(), reservation.getLongitude(),
                            location.getLatitude(), location.getLongitude()
                    )) + " м");
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
}

package com.sequenia.autoservices.fragments;

import android.app.Activity;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.sequenia.autoservices.R;
import com.sequenia.autoservices.activities.MainActivity;
import com.sequenia.autoservices.async_tasks.DirectionTask;
import com.sequenia.autoservices.drawer_fragments.PlaceholderFragment;
import com.sequenia.autoservices.objects.Bounds;
import com.sequenia.autoservices.objects.DirectionLocation;
import com.sequenia.autoservices.objects.DirectionsResponse;
import com.sequenia.autoservices.objects.HistoryCarWash;
import com.sequenia.autoservices.objects.Leg;
import com.sequenia.autoservices.objects.Route;
import com.sequenia.autoservices.objects.Step;
import com.sequenia.autoservices.static_classes.Global;
import com.sequenia.autoservices.static_classes.RealmHelper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    private boolean pathShown = false;

    private static final String MESSAGE = "Построение пути";

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
    public void onMapReady(final GoogleMap map) {
        showProgressDialog(MESSAGE);

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

                    if(!pathShown) {
                        showPath(location.getLatitude(), location.getLongitude(), map);
                    }
                }
            }
        });
    }

    private void showPath(final double currentLat, final double currentLng, final GoogleMap map) {
        new DirectionTask(currentLat, currentLng, reservation.getLatitude(), reservation.getLongitude()) {

            @Override
            public void onSuccess(DirectionsResponse response) {
                ArrayList<Route> routes = response.getRoutes();
                if(routes.size() > 0) {
                    showRoute(currentLat, currentLng, routes.get(0), map);
                }
                pathShown = true;
            }

            @Override
            public void onError() {
                pathShown = true;
            }
        }.execute();
    }

    private void showRoute(double currentLat, double currentLng, Route route, GoogleMap map) {
        List<LatLng> points = Global.decodePoly(route.getOverview_polyline().getPoints());

        PolylineOptions options = new PolylineOptions().width(5)
                .color(getActivity().getResources().getColor(R.color.teal_800))
                .geodesic(true);
        options.add(new LatLng(currentLat, currentLng));

        for(int i = 0; i < points.size(); i++) {
            options.add(points.get(i));
        }

        Bounds bounds = route.getBounds();
        DirectionLocation southwest = bounds.getSouthwest();
        DirectionLocation notrheast = bounds.getNortheast();

        closeProgressDialog();

        map.addPolyline(options);
        LatLngBounds b = new LatLngBounds(new LatLng(southwest.getLat(), southwest.getLng()),
                new LatLng(notrheast.getLat(), notrheast.getLng()));
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(b, 100));
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
}

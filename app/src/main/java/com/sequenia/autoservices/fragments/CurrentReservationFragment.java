package com.sequenia.autoservices.fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sequenia.autoservices.R;
import com.sequenia.autoservices.activities.MainActivity;
import com.sequenia.autoservices.drawer_fragments.PlaceholderFragment;
import com.sequenia.autoservices.objects.HistoryCarWash;
import com.sequenia.autoservices.static_classes.Global;
import com.sequenia.autoservices.static_classes.RealmHelper;

import io.realm.RealmResults;

/**
 * Created by chybakut2004 on 13.05.15.
 */
public class CurrentReservationFragment extends PlaceholderFragment
        implements OnMapReadyCallback {

    private static final String ARG_START_LATITUDE = "StartLatitude";
    private static final String ARG_START_LONGITUDE = "StartLongitude";
    private static final String ARG_START_ZOOM = "StartZoom";

    private Button callButton;
    private Button cancelButton;

    private float startLatitude;
    private float startLongitude;
    private float startZoom;

    private HistoryCarWash reservation;

    public CurrentReservationFragment() {
        setIsMain(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_reservation, container, false);

        reservation = Global.getCurrentReservation(getActivity());

        callButton = (Button) view.findViewById(R.id.call_button);
        cancelButton = (Button) view.findViewById(R.id.cancel_button);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(startLatitude, startLongitude), startZoom));

        map.setOnMarkerClickListener(null);

        if(reservation != null) {
            map.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_wash_free))
                    .anchor(0.0f, 1.0f)
                    .position(new LatLng(reservation.getLatitude(), reservation.getLongitude())));
        }

        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

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

    public void setData(float startLatitude, float startLongitude, float startZoom) {
        Bundle args = getArguments();
        args.putFloat(ARG_START_LATITUDE, startLatitude);
        args.putFloat(ARG_START_LONGITUDE, startLongitude);
        args.putFloat(ARG_START_ZOOM, startZoom);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        this.startLatitude = args.getFloat(ARG_START_LATITUDE);
        this.startLongitude = args.getFloat(ARG_START_LONGITUDE);
        this.startZoom = args.getFloat(ARG_START_ZOOM);
    }
}

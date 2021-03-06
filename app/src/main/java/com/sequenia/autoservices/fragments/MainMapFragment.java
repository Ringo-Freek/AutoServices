package com.sequenia.autoservices.fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;

import com.sequenia.autoservices.objects.HistoryCarWash;
import com.sequenia.autoservices.static_classes.Global;
import com.sequenia.autoservices.R;
import com.sequenia.autoservices.activities.MainActivity;
import com.sequenia.autoservices.async_tasks.NearCarWashesTask;
import com.sequenia.autoservices.drawer_fragments.PlaceholderFragment;
import com.sequenia.autoservices.objects.CarWash;

/**
 * Created by chybakut2004 on 29.04.15.
 * Работа с картой (инициализация,
 * определение координат,
 * выставление на карте ближайших объектов)
 */
public class MainMapFragment extends PlaceholderFragment
        implements OnMapReadyCallback {

    // Отвечают за то, какой вид моек сейчас открыт (Лист или карта)
    private static final int CAR_WASH_MAP = 0;
    private static final int CAR_WASH_LIST = 1;
    private int current = CAR_WASH_MAP;

    private LinearLayout carWashList;
    private LinearLayout carWashMap;
    public ArrayList<CarWash> carWashes = new ArrayList<CarWash>();
    private ArrayList<Marker> markers = new ArrayList<Marker>();

    private Button reserveButton;
    private Button currentReservationButton;

    private static final int distanceToUpdate = 500;

    private GoogleMap googleMap;

    private static final String MESSAGE = "Определение местоположения";

    Location personLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        carWashList = (LinearLayout) view.findViewById(R.id.car_wash_list);
        carWashMap = (LinearLayout) view.findViewById(R.id.car_wash_map);

        reserveButton = (Button) view.findViewById(R.id.reservation_button);
        currentReservationButton = (Button) view.findViewById(R.id.current_reservation_button);

        reserveButton.setEnabled(false);
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nearestReservation();
            }
        });

        showMap();

        return view;
    }

    private void nearestReservation() {
        CarWash nearest = null;
        float minDistance = Float.MAX_VALUE;

        for(CarWash carWash : carWashes) {
            float d = Global.getDistance(personLocation.getLatitude(), personLocation.getLongitude(),
                    carWash.getLatitude(), carWash.getLongitude());

            if(d < minDistance) {
                minDistance = d;
                nearest = carWash;
            }
        }

        if(nearest != null) {
            Global.showReservationForm(nearest, nearest.getId(), getActivity());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(googleMap != null) {
            googleMap.setMyLocationEnabled(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(googleMap != null) {
            googleMap.setMyLocationEnabled(true);
        }

        if(((MainActivity) getActivity()).isNeedsUpdate()) {
            System.out.println("adadadasdsa");
            update();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    /**
     * Работа с картой (выставление положения на карте - Новосибирск)
     * Поиск локации (местоположения пользователя)
     * Отправка запроса на сервер и обработка ответа
     * Выставление на карте ближайших моек
     */
    public void onMapReady(final GoogleMap map) {
        googleMap = map;

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(Global.startLatitude, Global.startLongitude), Global.startZoom));

        //showProgressDialog(MESSAGE);

        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                onLocationChange(location, map);
            }
        });
    }

    /**
     * Срабатывает во время смены местоположения
     *
     * @param location - текущие координаты
     * @param map - карта
     */
    private void onLocationChange(Location location, final GoogleMap map) {
        boolean needsUpdate = false;

        if(personLocation == null) {
            needsUpdate = true;
        } else {
            float distance = personLocation.distanceTo(location);
            if(distance > distanceToUpdate || distance < - distanceToUpdate) {
                needsUpdate = true;
            }
        }

        if(needsUpdate) {
            personLocation = location;

            loadCarWashes(map, true);
        }
    }

    private void loadCarWashes(final GoogleMap map, final boolean moveToMyLocation) {
        if(personLocation != null) {

            new NearCarWashesTask(getActivity(), (float) personLocation.getLatitude(), (float) personLocation.getLongitude()) {
                @Override
                public void onSuccess(ArrayList<CarWash> carWashesTask) {

                    //closeProgressDialog();

                    if(moveToMyLocation) {
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(personLocation.getLatitude(), personLocation.getLongitude()), Global.myPositionZoom));
                    }

                    carWashes = carWashesTask;
                    updateButtons();
                    showCarWashesOnMap(carWashesTask, map);
                }
            }.execute();
        }
    }

    public void update() {
        if(googleMap != null) {
            loadCarWashes(googleMap, false);
        }

        ((MainActivity) getActivity()).setNeedsUpdate(false);
    }

    /**
     * Отображает переданные мойки на карте
     *
     * @param carWashes - мойки, которые нужно отобразить на карте
     * @param map - карта, на которой отображать мойки
     */
    private void showCarWashesOnMap(ArrayList<CarWash> carWashes, final GoogleMap map) {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                CarWashCard fragment = (CarWashCard) PlaceholderFragment.newInstance(DIALOG_SECTION);
                fragment.setInfo(marker.getTitle(), Global.getDistance(marker.getPosition().latitude, marker.getPosition().longitude,
                        personLocation.getLatitude(), personLocation.getLongitude()));
                ((MainActivity)getActivity()).addSubFragment(fragment);
                return true;
            }
        });

        map.getUiSettings().setZoomControlsEnabled(true);

        for(Marker marker : markers) {
            marker.remove();
        }
        markers.clear();

        for(int i = 0; i < carWashes.size(); i++){
            CarWash carWashI = carWashes.get(i);

            int iconId;
            if(carWashI.getActions_count() == null) {
                iconId = R.drawable.ic_wash_free;
            } else {
                iconId = R.drawable.ic_wash_free_promo;
            }

            Marker marker = map.addMarker(new MarkerOptions()
                    .title(carWashI.getId().toString())
                    .icon(BitmapDescriptorFactory.fromResource(iconId))
                    .anchor(0.0f, 1.0f)
                    .position(new LatLng(carWashI.getLatitude(), carWashI.getLongitude())));
            markers.add(marker);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.show_list) {
            showList();
            showListItems();
            return true;
        }

        if(id == R.id.show_map) {
            showMap();
            showMapItems();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMap() {
        carWashList.setVisibility(View.GONE);
        carWashMap.setVisibility(View.VISIBLE);

        current = CAR_WASH_MAP;
    }

    private void showList() {
        carWashList.setVisibility(View.VISIBLE);
        carWashMap.setVisibility(View.GONE);

        for(int i = 0; i < carWashes.size(); i++){
            CarWash carWash = carWashes.get(i);
            carWash.setDistance(Global.getDistance(carWash.getLatitude(), carWash.getLongitude(),
                    personLocation.getLatitude(), personLocation.getLongitude()));
        }

        ListCarWash fragment = (ListCarWash) PlaceholderFragment.newInstance(PlaceholderFragment.CAR_LIST_SECTION);
        getChildFragmentManager().beginTransaction().replace(R.id.car_wash_list_fragment, fragment).commit();

        current = CAR_WASH_LIST;
    }

    private void showListItems() {
        MainActivity activity = (MainActivity) getActivity();
        activity.hideMenuItem(R.id.show_list);
        activity.showMenuItem(R.id.show_map);
    }

    private void showMapItems() {
        MainActivity activity = (MainActivity) getActivity();
        activity.hideMenuItem(R.id.show_map);
        activity.showMenuItem(R.id.show_list);
    }

    @Override
    public void restoreMenu(Menu menu, MainActivity activity) {
        super.restoreMenu(menu, activity);

        activity.showMenuItem(R.id.show_filter);

        activity.updateFilterItem();

        switch (current) {
            case CAR_WASH_MAP:
                showMapItems();
                break;

            case CAR_WASH_LIST:
                showListItems();
                break;
        }

        updateButtons();
    }

    private void updateButtons() {
        HistoryCarWash currentReservation = Global.getCurrentReservation(getActivity());

        if(currentReservation == null) {
            currentReservationButton.setVisibility(View.GONE);
            reserveButton.setVisibility(View.VISIBLE);

            reserveButton.setEnabled(carWashes.size() > 0);
        } else {
            currentReservationButton.setVisibility(View.VISIBLE);
            reserveButton.setVisibility(View.GONE);

            currentReservationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Global.showCurrentReservationFragment(getActivity());
                }
            });
        }
    }
}

package auto_services.sequenia.com.autoservices.fragments;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import auto_services.sequenia.com.autoservices.Global;
import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.activities.MainActivity;
import auto_services.sequenia.com.autoservices.async_tasks.NearCarWashesTask;
import auto_services.sequenia.com.autoservices.drawer_fragment.PlaceholderFragment;
import auto_services.sequenia.com.autoservices.objects.CarWash;
import auto_services.sequenia.com.autoservices.responses.JsonResponse;

/**
 * Created by chybakut2004 on 29.04.15.
 */
public class MainMapFragment extends PlaceholderFragment
        implements OnMapReadyCallback {

    private LinearLayout carWashList;
    private LinearLayout carWashMap;

    JsonResponse<CarWash> carWash;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        carWashList = (LinearLayout) view.findViewById(R.id.car_wash_list);
        carWashMap = (LinearLayout) view.findViewById(R.id.car_wash_map);

        return view;
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
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(Global.startLatitude, Global.startLongitude), Global.startZoom));

        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()), Global.myPositionZoom));
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
        new NearCarWashesTask((float) location.getLatitude(), (float) location.getLongitude(),
                Global.radius) {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s != null){
                    carWash = new Gson().fromJson(s, new TypeToken<JsonResponse<CarWash>>(){}.getType());
                    if(carWash.getSuccess()){
                        showCarWashesOnMap(carWash.getData(), map);
                    }
                }
            }
        }.execute();
    }

    /**
     * Отображает переданные мойки на карте
     *
     * @param carWashes - мойки, которые нужно отобразить на карте
     * @param map - карта, на которой отображать мойки
     */
    private void showCarWashesOnMap(ArrayList<CarWash> carWashes, GoogleMap map) {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                ((MainActivity)getActivity()).addSubFragment(PlaceholderFragment.newInstance(-2));
                return true;
            }
        });

        for(int i = 0; i < carWashes.size(); i++){
            CarWash carWashI = carWashes.get(i);
            map.addMarker(new MarkerOptions()
                    .title(carWashI.getId().toString())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker))
                    .anchor(0.0f, 1.0f)
                    .position(new LatLng(carWashI.getLatitude(), carWashI.getLongitude())));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.show_list) {
            carWashList.setVisibility(View.VISIBLE);
            carWashMap.setVisibility(View.GONE);
            return true;
        }

        if(id == R.id.show_map) {
            carWashList.setVisibility(View.GONE);
            carWashMap.setVisibility(View.VISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

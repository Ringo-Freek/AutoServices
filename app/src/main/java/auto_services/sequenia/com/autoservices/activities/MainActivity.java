package auto_services.sequenia.com.autoservices.activities;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;

import auto_services.sequenia.com.autoservices.async_tasks.AsyncTaskGet;
import auto_services.sequenia.com.autoservices.async_tasks.NearCarWashesTask;
import auto_services.sequenia.com.autoservices.objects.CarWash;
import auto_services.sequenia.com.autoservices.Global;
import auto_services.sequenia.com.autoservices.responses.JsonResponse;
import auto_services.sequenia.com.autoservices.fragments.NavigationDrawerFragment;
import auto_services.sequenia.com.autoservices.R;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnMapReadyCallback {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    // Тестовый комментарий

    ArrayList<String> title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onNavigationDrawerItemSelected(int number) {
        // update the main content by replacing fragments
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, MainPage.newInstance(position))
                .commit();*/

        title = new ArrayList(Arrays.asList(getResources().getStringArray(R.array.title)));
        title.addAll(new ArrayList(Arrays.asList(getResources().getStringArray(R.array.sub_menu_title))));

        mTitle = title.get(number);
        switch (number) {
            case 0: // Мой автомобиль

                break;
            case 1: // История посещеней

                break;
            case 2: // Поделиться с друзьями

                break;
            case 3: // Выйти

                break;
            case 4: // О нас

                break;
            case 5: // Контакты

                break;
            case 6: // Вход для администратора

                break;
        }
    }

    public void onSectionAttached(int number) {

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                new LatLng(55.0167, 82.9333), 10));

        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()), 16));
                onLocationChange(location, map);
            }
        });
    }

    private void onLocationChange(Location location, final GoogleMap map) {
        new NearCarWashesTask((float) location.getLatitude(), (float) location.getLongitude(),
                Global.radius) {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s != null){
                    JsonResponse<CarWash> carWash = new Gson().fromJson(s, new TypeToken<JsonResponse<CarWash>>(){}.getType());
                    if(carWash.getSuccess()){
                        showCarWashesOnMap(carWash.getData(), map);
                    }
                }
            }
        }.execute();
    }

    private void showCarWashesOnMap(ArrayList<CarWash> carWashes, GoogleMap map) {
        for(int i = 0; i < carWashes.size(); i++){
            CarWash carWashI = carWashes.get(i);
            map.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker))
                    .anchor(0.0f, 1.0f)
                    .position(new LatLng(carWashI.getLatitude(), carWashI.getLongitude())));
        }
    }
}

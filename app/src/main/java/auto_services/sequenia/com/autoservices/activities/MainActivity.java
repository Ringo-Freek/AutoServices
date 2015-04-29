package auto_services.sequenia.com.autoservices.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import auto_services.sequenia.com.autoservices.drawer_fragment.PlaceholderFragment;
import auto_services.sequenia.com.autoservices.fragments.MainMapFragment;
import auto_services.sequenia.com.autoservices.fragments.NavigationDrawerFragment;
import auto_services.sequenia.com.autoservices.R;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    private Stack<PlaceholderFragment> fragmentStack;

    ArrayList<String> title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Инициализация стека фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentStack = new Stack<PlaceholderFragment>();

        // Помещаем главный фрагмент
        MainMapFragment mapFragment = (MainMapFragment) PlaceholderFragment.newInstance(PlaceholderFragment.MAP_SECTION);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.content, mapFragment);
        fragmentStack.push(mapFragment);
        ft.commit();

        // Set up the drawer.
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }

    @Override
    public void onNavigationDrawerItemSelected(int number) {
        title = new ArrayList(Arrays.asList(getResources().getStringArray(R.array.title)));
        title.addAll(new ArrayList(Arrays.asList(getResources().getStringArray(R.array.sub_menu_title))));
        title.add(getString(R.string.login_admin));

        mTitle = title.get(number);

        addSubFragment(PlaceholderFragment.newInstance(number));
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

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Добавляет фрагмент в стек таким образом,
     * что можно вернуться к главному фрагменту, нажав кнопку назад.
     *
     * @param fragment
     */
    private void addSubFragment(PlaceholderFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.add(R.id.content, fragment);
        fragmentStack.lastElement().onPause();
        ft.hide(fragmentStack.lastElement());
        fragmentStack.push(fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (fragmentStack.size() >= 2) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            fragmentStack.lastElement().onPause();
            ft.remove(fragmentStack.pop());
            fragmentStack.lastElement().onResume();
            ft.show(fragmentStack.lastElement());
            ft.commit();
        } else {
            super.onBackPressed();
        }
    }
}

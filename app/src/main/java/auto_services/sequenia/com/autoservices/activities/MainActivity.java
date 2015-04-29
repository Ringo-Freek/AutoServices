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

    // Стек фрагментов. Используется для навигации между фрагментами.
    private Stack<PlaceholderFragment> fragmentStack;

    ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // init titles
        titles = new ArrayList(Arrays.asList(getResources().getStringArray(R.array.title)));
        titles.addAll(new ArrayList(Arrays.asList(getResources().getStringArray(R.array.sub_menu_title))));
        titles.add(getString(R.string.login_admin));

        // Инициализация стека фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentStack = new Stack<PlaceholderFragment>();

        // Помещаем главный фрагмент в стек
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
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int number) {
        addSubFragment(PlaceholderFragment.newInstance(number));
    }

    public void onSectionAttached(int number) {
        if(number >= 0) {
            mTitle = titles.get(number);
        } else {
            mTitle = getString(R.string.app_name);
        }
    }

    /**
     * Задание вида тулбара в зависимости от секции меню
     */
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    private void updateBackItem() {
        mNavigationDrawerFragment.setDrawerIndicatorEnabled(needsShowBackItem());
    }

    private boolean needsShowBackItem() {
        return fragmentStack.size() == 1;
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

        if(id == android.R.id.home && fragmentStack.size() > 1) {
            onBackPressed();
            return true;
        }

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

        ft.add(R.id.content, fragment); // Добавляем фрагмент на экран

        // Если фрагмент является элементом меню, то очищаем стек,
        // перед там как показать его
        if(fragment.isMain()) {
            while(fragmentStack.size() > 1) {
                fragmentStack.lastElement().onPause();  // Останавливаем верхний фрагмент
                ft.remove(fragmentStack.pop());         // Удаляем его с экрана и из стека
            }
        // Иначе, просто ставим верхний фрагмент на паузу
        } else {
            fragmentStack.lastElement().onPause(); // Останавливаем предыдущий фрагмент
            ft.hide(fragmentStack.lastElement());  // Скрываем предыдущий фрагмент с экрана
        }

        fragmentStack.push(fragment);          // Добавляем новый фрагмент в стек

        ft.commit();

        updateBackItem();
    }

    /**
     * Вызывается при нажатии кнопки назад
     */
    @Override
    public void onBackPressed() {
        if (fragmentStack.size() >= 2) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            fragmentStack.lastElement().onPause();  // Останавливаем текущий фрагмент
            ft.remove(fragmentStack.pop());         // Удаляем его с экрана и из стека
            fragmentStack.lastElement().onResume(); // Возобновляем предыдущий фрагмент
            ft.show(fragmentStack.lastElement());   // Показываем предыдущий фрагмент на экране

            ft.commit();

            // Настройка тулбара для предыдущего фрагмента
            PlaceholderFragment currentFragment = fragmentStack.lastElement();
            onSectionAttached(currentFragment.getNumber());
            restoreActionBar();
            updateBackItem();
        } else {
            super.onBackPressed();
        }
    }
}

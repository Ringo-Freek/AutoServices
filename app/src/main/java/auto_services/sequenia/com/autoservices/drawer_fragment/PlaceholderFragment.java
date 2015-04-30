package auto_services.sequenia.com.autoservices.drawer_fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import auto_services.sequenia.com.autoservices.activities.MainActivity;
import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.fragments.CarWashCard;
import auto_services.sequenia.com.autoservices.fragments.MainMapFragment;

/**
 * Created by Ringo on 21.04.2015.
 */
public class PlaceholderFragment extends Fragment {
    public static final int MAP_SECTION = -1;
    public static final int DIALOG_SECTION = -2;

    private int number;                 // Номер секции меню.
    private boolean isMain = true;      // Главный ли фрагмент. Если главный, то все фрагменты в стеке удалятся.
    private boolean shadowVisible = false; // Видна ли тень под фрагментом.

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_IS_MAIN = "is_main";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment;

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);

        switch (sectionNumber) {
            case MAP_SECTION:
                fragment = new MainMapFragment();
                fragment.setHasOptionsMenu(true);
                args.putBoolean(ARG_IS_MAIN, true);
                break;

            case DIALOG_SECTION:
                fragment = new CarWashCard();
                args.putBoolean(ARG_IS_MAIN, false);
                break;

            default:
                fragment = new PlaceholderFragment();
                args.putBoolean(ARG_IS_MAIN, true);
                break;
        }

        fragment.setArguments(args);

        return fragment;
    }

    public PlaceholderFragment() {

    }

    /**
     * Настройка иконок тулбара в зависимости от открытого фрагмента.
     *
     * Стандартно все скрыто.
     *
     * Нужно перегрузить, чтобы настроить свой вид в дочерних фрагментах.
     */
    public void restoreMenu(Menu menu) {
        MainActivity activity = (MainActivity) getActivity();

        activity.hideMenuItem(R.id.show_list);
        activity.hideMenuItem(R.id.show_map);
        activity.hideMenuItem(R.id.show_filter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Bundle args = getArguments();

        number = args.getInt(ARG_SECTION_NUMBER);
        isMain = args.getBoolean(ARG_IS_MAIN);

        ((MainActivity) activity).onSectionAttached(number);
    }

    public int getNumber() {
        return number;
    }

    public boolean isMain() {
        return isMain;
    }

    public boolean isShadowVisible() {
        return shadowVisible;
    }

    public void setShadowVisible(boolean flag) {
        this.shadowVisible = flag;
    }
}

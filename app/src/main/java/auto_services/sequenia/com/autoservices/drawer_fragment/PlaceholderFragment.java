package auto_services.sequenia.com.autoservices.drawer_fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import auto_services.sequenia.com.autoservices.activities.MainActivity;
import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.fragments.MainMapFragment;

/**
 * Created by Ringo on 21.04.2015.
 */
public class PlaceholderFragment extends Fragment {
    public static final int MAP_SECTION = -1;

    private int number;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment;

        switch (sectionNumber) {
            case MAP_SECTION:
                fragment = new MainMapFragment();
                break;

            default:
                fragment = new PlaceholderFragment();
                break;
        }

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    public PlaceholderFragment() {

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

        number = getArguments().getInt(ARG_SECTION_NUMBER);

        ((MainActivity) activity).onSectionAttached(number);
    }

    public int getNumber() {
        return number;
    }
}

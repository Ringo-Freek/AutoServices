package auto_services.sequenia.com.autoservices.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.drawer_fragments.PlaceholderFragment;

/**
 * Created by chybakut2004 on 30.04.15.
 */
public class ReviewsFragment extends PlaceholderFragment {

    public ReviewsFragment() {
        setIsMain(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);


        return rootView;
    }
}
package com.sequenia.autoservices.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sequenia.autoservices.R;
import com.sequenia.autoservices.drawer_fragments.PlaceholderFragment;

/**
 * Created by chybakut2004 on 30.04.15.
 */
public class AboutFragment extends PlaceholderFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);


        return rootView;
    }
}

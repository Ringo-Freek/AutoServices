package com.sequenia.autoservices.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.sequenia.autoservices.Global;
import com.sequenia.autoservices.R;
import com.sequenia.autoservices.adapters.CoastAdapter;
import com.sequenia.autoservices.drawer_fragments.PlaceholderFragment;
import com.sequenia.autoservices.widgets.Rating;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 30.04.15.
 */
public class FiltersFragment extends PlaceholderFragment implements View.OnClickListener{

    ArrayList<View> views;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filters, container, false);

        Integer radius = Global.radius / 1000;
        final TextView seekBarCount = ((TextView)rootView.findViewById(R.id.seep_bar_count));
        seekBarCount.setText(radius + " км");

        SeekBar radiusBar = (SeekBar)rootView.findViewById(R.id.seep_bar);
        radiusBar.setProgress(radius);
        radiusBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarCount.setText(progress + " км");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Rating rating = (Rating)rootView.findViewById(R.id.mark);
        rating.initRating(getActivity(), 0);
        rating.setListenerClick(true);

        Spinner coast = (Spinner)rootView.findViewById(R.id.coast);
        ArrayList<String> coastList = new ArrayList();
        coastList.add("100");
        coastList.add("200");
        coastList.add("300");
        coastList.add("400");

        CoastAdapter coastAdapter = new CoastAdapter(getActivity(), R.layout.filter_item, coastList);
        coast.setAdapter(coastAdapter);

        LinearLayout filterItemsLayout = (LinearLayout)rootView.findViewById(R.id.filter_content_items);
        String[] titles = getResources().getStringArray(R.array.filter_titles);
        TypedArray images = getResources().obtainTypedArray(R.array.filter_images);
        views = new ArrayList<View>();

        for(int i = 0; i < titles.length; i++){
            View view = inflater.inflate(R.layout.filter_item, container, false);
            ((TextView)view.findViewById(R.id.filter_text)).setText(titles[i]);
            ((ImageView)view.findViewById(R.id.filter_img)).setImageResource(images.getResourceId(i, 0));
            views.add(view);
            filterItemsLayout.addView(view);
        }

        rootView.findViewById(R.id.search).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search:
                goSearch();
                break;
            case R.id.clear:
                clearFilter();
                break;
        }

        closeFilter();
    }

    public void clearFilter(){

    }

    public void goSearch(){

    }

    public void closeFilter(){

    }
}

package com.sequenia.autoservices.fragments;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.sequenia.autoservices.Global;
import com.sequenia.autoservices.R;
import com.sequenia.autoservices.activities.MainActivity;
import com.sequenia.autoservices.adapters.CoastAdapter;
import com.sequenia.autoservices.drawer_fragments.PlaceholderFragment;
import com.sequenia.autoservices.widgets.Rating;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 30.04.15.
 */
public class FiltersFragment extends PlaceholderFragment implements View.OnClickListener{

    private ArrayList<SwitchCompat> switches;
    private Rating rating;
    private SeekBar radiusBar;
    private TextView radiusBarText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filters, container, false);

        Activity activity = getActivity();

        Integer radius = Global.getRadius(activity) / 1000;
        radiusBarText = ((TextView)rootView.findViewById(R.id.seep_bar_count));
        setRadiusText(radius);

        radiusBar = (SeekBar)rootView.findViewById(R.id.seep_bar);
        radiusBar.setProgress(radius);
        radiusBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setRadiusText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rating = (Rating)rootView.findViewById(R.id.mark);
        rating.initRating(getActivity(), Global.getRating(activity));
        rating.setListenerClick(true);

        Spinner coast = (Spinner)rootView.findViewById(R.id.coast);
        ArrayList<String> coastList = new ArrayList();
        coastList.add("100");
        coastList.add("200");
        coastList.add("300");
        coastList.add("400");

        CoastAdapter coastAdapter = new CoastAdapter(getActivity(), R.layout.filter_item, coastList);
        coast.setAdapter(coastAdapter);

        initSwitches(rootView, inflater, container);

        rootView.findViewById(R.id.search).setOnClickListener(this);
        rootView.findViewById(R.id.clear).setOnClickListener(this);
        return rootView;
    }

    private void initSwitches(View rootView, LayoutInflater inflater, ViewGroup container) {
        LinearLayout filterItemsLayout = (LinearLayout)rootView.findViewById(R.id.filter_content_items);
        String[] titles = getResources().getStringArray(R.array.filter_titles);
        TypedArray images = getResources().obtainTypedArray(R.array.filter_images);
        switches = new ArrayList<SwitchCompat>();

        final Activity activity = getActivity();

        for(int i = 0; i < titles.length; i++){
            final int position = i;
            View view = inflater.inflate(R.layout.filter_item, container, false);
            ((TextView)view.findViewById(R.id.filter_text)).setText(titles[i]);
            ((ImageView)view.findViewById(R.id.filter_img)).setImageResource(images.getResourceId(i, 0));

            SwitchCompat switchCompat = (SwitchCompat) view.findViewById(R.id.switch_filter);
            switchCompat.setChecked(Global.getFilter(activity, position));
            switches.add(switchCompat);
            filterItemsLayout.addView(view);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search:
                applyFilter();
                closeFilter();
                break;
            case R.id.clear:
                clearFilter();
                break;
        }
    }

    public void clearFilter(){
        Activity activity = getActivity();
        for(int i = 0; i < switches.size(); i++) {
            switches.get(i).setChecked(false);
        }

        rating.initRating(activity, 0);
        rating.setListenerClick(true);

        int radius = Global.radius / 1000;
        radiusBar.setProgress(radius);
        setRadiusText(radius);
    }

    public void applyFilter(){
        Activity activity = getActivity();

        for(int i = 0; i < switches.size(); i++) {
            Global.setFilter(activity, switches.get(i).isChecked(), i);
        }

        Global.setRating(activity, rating.getRating());
        Global.setRadius(activity, radiusBar.getProgress() * 1000);
    }

    public void closeFilter(){
        getActivity().onBackPressed();
    }

    private void setRadiusText(int radius) {
        radiusBarText.setText(radius + " км");
    }
}

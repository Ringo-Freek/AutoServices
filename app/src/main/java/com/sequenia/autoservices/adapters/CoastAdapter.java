package com.sequenia.autoservices.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sequenia.autoservices.R;

import java.util.ArrayList;

/**
 * Created by Ringo on 11.05.2015.
 * Адаптер для цены
 */
public class CoastAdapter extends ArrayAdapter {

    private LayoutInflater inflater;
    private ArrayList<String> coastList;

    public CoastAdapter(Context context, int resource, ArrayList<String> coastList) {
        super(context, resource, coastList);
        inflater = ((Activity) context).getLayoutInflater();
        this.coastList = coastList;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = createView(position, convertView, parent);
        view.setBackgroundResource(R.color.color_white);
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    public View createView(int position, View convertView, ViewGroup parent){
        View rootView;
        if(convertView != null){
            rootView = convertView;
        }else{
            rootView = inflater.inflate(R.layout.coast_spinner_item, parent, false);
        }

        ((TextView)rootView.findViewById(R.id.coast)).setText(coastList.get(position));
        return rootView;
    }
}

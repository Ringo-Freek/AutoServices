package com.sequenia.autoservices.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sequenia.autoservices.R;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public class BodyTypeSpinnerAdapter extends ArrayAdapter<Integer> {

    private LayoutInflater inflater;
    private String[] bodyTypes;
    private TypedArray bodyTypeIcons;
    private String[] bodyTypeLabels;
    private int black87;
    private int black26;
    private String placeholder;

    private boolean selected;

    public BodyTypeSpinnerAdapter(Context context) {
        super(context, R.layout.big_spinner_item, new Integer[4]);

        inflater = ((Activity) context).getLayoutInflater();
        selected = false;

        Resources resources = context.getResources();
        placeholder = "Не выбран";
        black26 = resources.getColor(R.color.black26);
        black87 = resources.getColor(R.color.black87);

        this.bodyTypes = resources.getStringArray(R.array.body_types);
        this.bodyTypeIcons = resources.obtainTypedArray(R.array.body_type_icons);
        this.bodyTypeLabels = resources.getStringArray(R.array.body_type_labels);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row;

        if(convertView == null) {
            row = inflater.inflate(R.layout.body_type_dropdown_item, parent, false);
        } else {
            row = convertView;
        }

        TextView label = (TextView) row.findViewById(R.id.text);
        ImageView imageView = (ImageView) row.findViewById(R.id.image);

        label.setText(bodyTypeLabels[position]);
        imageView.setImageResource(bodyTypeIcons.getResourceId(position, 0));

        return row;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;

        if(convertView == null) {
            row = inflater.inflate(R.layout.big_spinner_item, parent, false);
        } else {
            row = convertView;
        }

        TextView label = (TextView) row.findViewById(R.id.text);
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, getTextSize());
        label.setGravity(getGravity());

        if(selected) {
            label.setText(bodyTypeLabels[position]);
            label.setTextColor(black87);
        } else {
            label.setText(placeholder);
            label.setTextColor(black26);
        }

        return row;
    }

    public String[] getBodyTypes() {
        return bodyTypes;
    }

    public int getTextSize() {
        return 16;
    }

    public int getGravity() {
        return Gravity.CENTER;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

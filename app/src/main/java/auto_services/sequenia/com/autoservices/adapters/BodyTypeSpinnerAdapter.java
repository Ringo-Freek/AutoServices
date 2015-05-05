package auto_services.sequenia.com.autoservices.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.objects.CarMark;
import io.realm.RealmResults;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public class BodyTypeSpinnerAdapter extends ArrayAdapter<Integer> {

    private LayoutInflater inflater;
    private String[] bodyTypes;
    private int[] bodyTypeIcons;
    private String[] bodyTypeLabels;

    public BodyTypeSpinnerAdapter(Context context, String[] bodyTypes, String[] bodyTypeLabels, int[] bodyTypeIcons) {
        super(context, R.layout.big_spinner_item, new ArrayList(Arrays.asList(bodyTypes)));

        inflater = ((Activity) context).getLayoutInflater();

        this.bodyTypes = bodyTypes;
        this.bodyTypeIcons = bodyTypeIcons;
        this.bodyTypeLabels = bodyTypeLabels;
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
        imageView.setImageResource(bodyTypeIcons[position]);

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

        label.setText(bodyTypeLabels[(position)]);

        return row;
    }

    public String[] getBodyTypes() {
        return bodyTypes;
    }
}

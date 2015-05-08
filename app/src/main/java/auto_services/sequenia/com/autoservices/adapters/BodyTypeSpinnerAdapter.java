package auto_services.sequenia.com.autoservices.adapters;

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

import auto_services.sequenia.com.autoservices.R;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public class BodyTypeSpinnerAdapter extends ArrayAdapter<Integer> {

    private LayoutInflater inflater;
    private String[] bodyTypes;
    private TypedArray bodyTypeIcons;
    private String[] bodyTypeLabels;

    public BodyTypeSpinnerAdapter(Context context) {
        super(context, R.layout.big_spinner_item, new Integer[4]);

        inflater = ((Activity) context).getLayoutInflater();

        Resources resources = context.getResources();

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


        label.setText(bodyTypeLabels[position]);

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
}

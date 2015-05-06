package auto_services.sequenia.com.autoservices.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.objects.CarMark;
import io.realm.RealmResults;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public class CarMarksSpinnerAdapter extends ArrayAdapter<CarMark> {

    private LayoutInflater inflater;
    private String placeholder;
    private int black87;
    private int black26;

    public CarMarksSpinnerAdapter(Context context, RealmResults<CarMark> carMarks) {
        super(context, R.layout.big_spinner_item, carMarks);

        inflater = ((Activity) context).getLayoutInflater();

        Resources resources = context.getResources();
        placeholder = resources.getString(R.string.car_mark);
        black26 = resources.getColor(R.color.black26);
        black87 = resources.getColor(R.color.black87);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row;

        if(convertView == null) {
            row = inflater.inflate(R.layout.car_mark_dropdown_item, parent, false);
        } else {
            row = convertView;
        }

        TextView label = (TextView) row.findViewById(R.id.text);

        if(position == 0){
            label.setText(placeholder);
        } else {
            CarMark carMark = getItem(position - 1);
            label.setText(carMark.getName());
        }

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

        if(position == 0){
            label.setText(placeholder);
            label.setTextColor(black26);
        } else {
            CarMark carMark = getItem(position - 1);
            label.setText(carMark.getName());
            label.setTextColor(black87);
        }

        return row;
    }

    public int getTextSize() {
        return 16;
    }

    public int getGravity() {
        return Gravity.CENTER;
    }
}

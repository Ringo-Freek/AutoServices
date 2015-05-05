package auto_services.sequenia.com.autoservices.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public class SpinnerWithLabel extends Spinner {

    private View label;

    public SpinnerWithLabel(Context context) {
        super(context);
    }

    public SpinnerWithLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLabel(final View label) {
        if(label != null) {
            this.label = label;

            updateLabel(getSelectedItemPosition());

            setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    updateLabel(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void updateLabel(int position) {
        if(position == 0) {
            label.setVisibility(INVISIBLE);
        } else {
            label.setVisibility(VISIBLE);
        }
    }
}

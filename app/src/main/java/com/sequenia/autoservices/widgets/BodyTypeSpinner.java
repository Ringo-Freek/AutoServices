package com.sequenia.autoservices.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.sequenia.autoservices.adapters.BodyTypeSpinnerAdapter;

/**
 * Created by chybakut2004 on 06.05.15.
 */
public class BodyTypeSpinner extends Spinner {

    private int textSize = 20;
    private int gravity = Gravity.CENTER;

    private boolean first = true;

    public BodyTypeSpinner(Context context) {
        super(context);

        if (isInEditMode()) {
            return;
        }

        init(context);
    }

    public BodyTypeSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        }

        init(context);
    }

    private void init(Context context) {
        final BodyTypeSpinner self = this;
        final BodyTypeSpinnerAdapter adapter = new BodyTypeSpinnerAdapter(context) {
            @Override
            public int getTextSize() {
                return self.getTextSize();
            }

            @Override
            public int getGravity() {
                return self.getGravity();
            }
        };
        setAdapter(adapter);

        setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!first) {
                    adapter.setSelected(true);
                    adapter.notifyDataSetChanged();
                }

                first = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void selectBodyType(String bodyType) {
        BodyTypeSpinnerAdapter adapter = (BodyTypeSpinnerAdapter) getAdapter();
        String[] bodyTypes = adapter.getBodyTypes();
        for(int i = 0; i < bodyTypes.length; i++) {
            if(bodyType.equals(bodyTypes[i])) {
                adapter.setSelected(true);
                setSelection(i);
                break;
            }
        }
    }

    public String getSelectedBodyType() {
        BodyTypeSpinnerAdapter adapter = (BodyTypeSpinnerAdapter) getAdapter();
        String bodyType = null;
        if(adapter.isSelected()) {
            bodyType = adapter.getBodyTypes()[getSelectedItemPosition()];
        }
        return bodyType;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getGravity() {
        return gravity;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }
}

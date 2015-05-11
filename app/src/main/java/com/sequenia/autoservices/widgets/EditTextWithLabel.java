package com.sequenia.autoservices.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public class EditTextWithLabel extends EditText {

    private View label;

    public EditTextWithLabel(Context context) {
        super(context);
    }

    public EditTextWithLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLabel(final View label) {
        if(label != null) {
            this.label = label;

            updateLabel(getText().toString());

            addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    updateLabel(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    public void updateLabel(String text) {
        if(text == null || text.length() == 0) {
            label.setVisibility(INVISIBLE);
        } else {
            label.setVisibility(VISIBLE);
        }
    }
}

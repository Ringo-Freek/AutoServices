package com.sequenia.autoservices.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * Created by chybakut2004 on 10.05.15.
 */
public class PhoneEditText extends ModernhMediumEditText {

    public PhoneEditText(Context context) {
        super(context);
        init();
    }

    public PhoneEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && getText().length() == 0) {
                    setText("+7");
                    post(new Runnable() {
                        @Override
                        public void run() {
                            setSelection(2, 2);
                        }
                    });
                }
            }
        });
    }

    public String getPhone() {
        String string = getText().toString();

        if(string.length() == 0) {
            return "";
        }

        boolean plus = string.charAt(0) == '+';

        String phone = string.replaceAll("[^\\d]", "");

        if(plus) {
            phone = "+" + phone;
        }

        return phone;
    }
}

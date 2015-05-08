package com.sequenia.autoservices.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by chybakut2004 on 05.05.15.
 */
public class ModernhMediumEditText extends EditTextWithLabel {

    public ModernhMediumEditText(Context context) {
        super(context);

        if (isInEditMode()) {
            return;
        }

        setTypeface(Typeface.createFromAsset(context.getAssets(), "modernh-medium.ttf"));
    }

    public ModernhMediumEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        }

        setTypeface(Typeface.createFromAsset(context.getAssets(), "modernh-medium.ttf"));
    }
}

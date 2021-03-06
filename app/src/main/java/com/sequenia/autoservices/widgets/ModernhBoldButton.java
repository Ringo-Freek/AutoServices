package com.sequenia.autoservices.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Ringo on 22.04.2015.
 * Кнопка со шрифтом modernh-bold
 */
public class ModernhBoldButton extends Button{
    public ModernhBoldButton(Context context) {
        super(context);

        if (isInEditMode()) {
            return;
        }

        setTypeface(Typeface.createFromAsset(context.getAssets(), "modernh-bold.ttf"));
    }

    public ModernhBoldButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        }

        setTypeface(Typeface.createFromAsset(context.getAssets(), "modernh-bold.ttf"));
    }
}
package auto_services.sequenia.com.autoservices.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Ringo on 22.04.2015.
 * Textview со шрифтом modernh medium
 */
public class ModernhMediumTextView extends TextView {
    public ModernhMediumTextView(Context context) {
        super(context);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "modernh-medium.ttf"));
    }

    public ModernhMediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "modernh-medium.ttf"));
    }
}
package auto_services.sequenia.com.autoservices.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Ringo on 22.04.2015.
 */
public class ModernhBoldTextView extends TextView {
    public ModernhBoldTextView(Context context) {
        super(context);

        if (isInEditMode()) {
            return;
        }

        setTypeface(Typeface.createFromAsset(context.getAssets(), "modernh-bold.ttf"));
    }

    public ModernhBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        }

        setTypeface(Typeface.createFromAsset(context.getAssets(), "modernh-bold.ttf"));
    }
}
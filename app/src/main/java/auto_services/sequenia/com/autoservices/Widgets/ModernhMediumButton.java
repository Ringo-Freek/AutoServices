package auto_services.sequenia.com.autoservices.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Ringo on 22.04.2015.
 */
public class ModernhMediumButton extends Button{
    public ModernhMediumButton(Context context) {
        super(context);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "modernh-medium.ttf"));
    }

    public ModernhMediumButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "modernh-medium.ttf"));
    }
}
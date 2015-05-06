package auto_services.sequenia.com.autoservices.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Spinner;

import auto_services.sequenia.com.autoservices.adapters.BodyTypeSpinnerAdapter;

/**
 * Created by chybakut2004 on 06.05.15.
 */
public class BodyTypeSpinner extends Spinner {

    private int textSize = 20;
    private int gravity = Gravity.CENTER;

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
        setAdapter(new BodyTypeSpinnerAdapter(context) {
            @Override
            public int getTextSize() {
                return self.getTextSize();
            }

            @Override
            public int getGravity() {
                return self.getGravity();
            }
        });
        setSelection(0);
    }

    public void selectBodyType(String bodyType) {
        BodyTypeSpinnerAdapter adapter = (BodyTypeSpinnerAdapter) getAdapter();
        String[] bodyTypes = adapter.getBodyTypes();
        for(int i = 0; i < bodyTypes.length; i++) {
            if(bodyType.equals(bodyTypes[i])) {
                setSelection(i);
                break;
            }
        }
    }

    public String getSelectedBodyType() {
        BodyTypeSpinnerAdapter adapter = (BodyTypeSpinnerAdapter) getAdapter();
        String bodyType = adapter.getBodyTypes()[getSelectedItemPosition()];
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

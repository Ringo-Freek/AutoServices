package auto_services.sequenia.com.autoservices.widgets;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import java.util.ArrayList;

import auto_services.sequenia.com.autoservices.adapters.CarMarksSpinnerAdapter;
import auto_services.sequenia.com.autoservices.objects.CarMark;
import auto_services.sequenia.com.autoservices.static_classes.RealmHelper;
import io.realm.RealmResults;

/**
 * Created by chybakut2004 on 06.05.15.
 */
public class CarMarkSpinner extends SpinnerWithLabel {

    private int textSize = 20;
    private int gravity = Gravity.CENTER;
    private RealmResults<CarMark> carMarks;

    public CarMarkSpinner(Context context) {
        super(context);

        if (isInEditMode()) {
            return;
        }

        init(context);
    }

    public CarMarkSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        }

        init(context);
    }

    private void init(Context context) {
        final CarMarkSpinner self = this;
        carMarks = RealmHelper.getCarMarks(context);
        setAdapter(new CarMarksSpinnerAdapter(context, carMarks) {
            @Override
            public int getTextSize() {
                return self.getTextSize();
            }

            @Override
            public int getGravity() {
                return self.getGravity();
            }
        });
    }

    public void selectCarMark(int carMarkId) {
        int carMarkIndex = 0;
        for(int i = 0; i < carMarks.size(); i++) {
            if(carMarkId == carMarks.get(i).getId()) {
                carMarkIndex = i + 1;
                break;
            }
        }

        setSelection(carMarkIndex);
    }

    public Integer getSelectedCarMarkId() {
        Integer carMarkId = null;
        int carMarkIndex = getSelectedItemPosition() - 1;
        if(carMarkIndex >= 0) {
            carMarkId = carMarks.get(carMarkIndex).getId();
        }

        return carMarkId;
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

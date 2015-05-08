package com.sequenia.autoservices.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import com.sequenia.autoservices.adapters.CarMarksSpinnerAdapter;
import com.sequenia.autoservices.objects.CarMark;
import com.sequenia.autoservices.static_classes.RealmHelper;
import io.realm.RealmResults;

/**
 * Created by chybakut2004 on 06.05.15.
 */
public class CarMarkSpinner extends SpinnerWithLabel {

    private int textSize = 20;
    private int gravity = Gravity.CENTER;
    private int textColor = 0;
    private int textHintColor = 0;
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

            @Override
            public int getTextColor() {
                return self.getTextColor();
            }

            @Override
            public int getTextHintColor() {
                return self.getTextHintColor();
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

    public int getTextColor() {
        return textColor;
    }

    public int getTextHintColor() {
        return textHintColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextHintColor(int textHintColor) {
        this.textHintColor = textHintColor;
    }
}

package com.sequenia.autoservices.dialogs;

import android.app.Activity;
import android.os.Bundle;

import com.sequenia.autoservices.Global;
import com.sequenia.autoservices.R;
import com.sequenia.autoservices.async_tasks.ReviewCreationTask;
import com.sequenia.autoservices.objects.Review;
import com.sequenia.autoservices.objects.ReviewCreationData;
import com.sequenia.autoservices.widgets.EditTextWithLabel;
import com.sequenia.autoservices.widgets.Rating;

/**
 * Created by Ringo on 06.05.2015.
 * Создание диалогового окна для добавления отзыва
 */
public class CreateReviewDialog extends DialogWindow {

    public static final int CREATE_REVIEW_DIALOG = 1;
    public static final int NEEDS_UPDATE = 2;

    private static final String ARG_ITEM_ID = "ItemId";

    private EditTextWithLabel editTextYourName;
    private EditTextWithLabel editTextYourReview;
    private Rating rating;
    private int itemId;

    @Override
    public void doneCloseDialog() {
        String name = editTextYourName.getText().toString();
        String review = editTextYourReview.getText().toString();
        int mark = rating.getRating();

        final Activity activity = getActivity();

        if(Global.validateNotNullName(activity, name) &&
                Global.validateRating(activity, mark)) {
            new ReviewCreationTask(new ReviewCreationData(
                    Global.testToken, mark, name, itemId, review
            )){
                @Override
                public void onSuccess(Review review) {
                    CreateReviewDialog.super.doneCloseDialog();
                }

                @Override
                public void onError() {

                }
            }.execute();
        }
    }

    public void initViewElements(){
        rating = (Rating)view.findViewById(R.id.rating);
        rating.initRating(getActivity(), 0);
        rating.setListenerClick(true);

        editTextYourName = (EditTextWithLabel)view.findViewById(R.id.edit_text_your_name);
        editTextYourName.setLabel(view.findViewById(R.id.text_view_your_name));
        editTextYourName.setText(Global.getName(getActivity()));

        editTextYourReview = (EditTextWithLabel)view.findViewById(R.id.edit_text_your_review);
        editTextYourReview.setLabel(view.findViewById(R.id.text_view_your_review));
    }

    public void setData(int itemId) {
        Bundle args = getArguments();
        args.putInt(ARG_ITEM_ID, itemId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        this.itemId = args.getInt(ARG_ITEM_ID);
    }
}

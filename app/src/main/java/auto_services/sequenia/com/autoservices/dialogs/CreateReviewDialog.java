package auto_services.sequenia.com.autoservices.dialogs;

import android.widget.TextView;

import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.widgets.EditTextWithLabel;
import auto_services.sequenia.com.autoservices.widgets.Rating;

/**
 * Created by Ringo on 06.05.2015.
 * Создание диалогового окна для добавления отзыва
 */
public class CreateReviewDialog extends DialogWindow {
    @Override
    public void doneCloseDialog() {

    }

    public void initViewElements(){
        Rating rating = (Rating)view.findViewById(R.id.rating);
        rating.initRating(getActivity(), 0);
        rating.setListenerClick(true);

        EditTextWithLabel editTextYourName = (EditTextWithLabel)view.findViewById(R.id.edit_text_your_name);
        editTextYourName.setLabel(view.findViewById(R.id.text_view_your_name));

        EditTextWithLabel editTextYourReview = (EditTextWithLabel)view.findViewById(R.id.edit_text_your_review);
        editTextYourReview.setLabel(view.findViewById(R.id.text_view_your_review));
    }
}

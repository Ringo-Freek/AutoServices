package auto_services.sequenia.com.autoservices.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import auto_services.sequenia.com.autoservices.Global;
import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.async_tasks.ReviewsTask;
import auto_services.sequenia.com.autoservices.drawer_fragments.MasterFragment;
import auto_services.sequenia.com.autoservices.objects.Review;
import auto_services.sequenia.com.autoservices.responses.JsonResponse;
import auto_services.sequenia.com.autoservices.widgets.Rating;

/**
 * Created by chybakut2004 on 30.04.15.
 * Вьюшка с отзывами
 */
public class ReviewsFragment extends MasterFragment {

    private static final String ARG_CAR_WASH_ID = "CarWashId";
    private static final int LOADING_COUNT = 15;
    private static final int SCROLLED_TO_LOADING = 10;

    SimpleDateFormat formatter;
    SimpleDateFormat formatDay;
    SimpleDateFormat formatTime;

    private String carWashId;

    public ReviewsFragment() {
        setIsMain(false);

        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        formatDay = new SimpleDateFormat("dd/MM/yy");
        formatTime = new SimpleDateFormat("HH:mm");

        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public void loadObjects(int page) {
        new ReviewsTask(
                "reviews.json?auth_token="
                        + Global.testToken
                        + "&item_id="
                        + carWashId
                        + "&page=" + (page + 1) + "&count=" + LOADING_COUNT){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s != null){
                    JsonResponse<ArrayList<Review>> reviewsResponse = new Gson().fromJson(s, new TypeToken<JsonResponse<ArrayList<Review>>>(){}.getType());
                    if(reviewsResponse.getSuccess()){
                        addObjects(reviewsResponse.getData());
                    }
                }
            }
        }.execute();
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.review_item, container, false);

        TextView reviewText = (TextView)view.findViewById(R.id.review_text);
        TextView userName = (TextView)view.findViewById(R.id.user_name);
        TextView reviewDate = (TextView)view.findViewById(R.id.review_date);

        ImageView avatar = (ImageView)view.findViewById(R.id.avatar);
        Rating rating = (Rating)view.findViewById(R.id.mark);

        return new ReviewItem(view, reviewText, userName, reviewDate, avatar, rating);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, int position, RecyclerView.Adapter adapter, Object object) {

        ReviewItem reviewItem = (ReviewItem)holder;
        Review review = (Review) object;

        reviewItem.userName.setText(review.getUser_name());
        reviewItem.reviewText.setText(review.getText());

        reviewItem.reviewDate.setText(reviewDate(review.getCreated_at()));

        reviewItem.rating.initRating(getActivity(), review.getMark());

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(4)
                .build();
        Picasso
                .with(getActivity())
                .load("http://developer.android.com/design/media/notifications_pattern_phone_ticker.png")
                .transform(transformation)
                .fit()
                .centerCrop()
                .into(reviewItem.avatar);
    }

    @Override
    public int getDetailFragmentId() {
        return 0;
    }

    @Override
    public boolean hasEndlessScroll() {
        return true;
    }

    @Override
    public int scrolledToLoading() {
        return SCROLLED_TO_LOADING;
    }

    @Override
    public void setInfoToDetailFragment(Bundle args, Object object) {

    }

    public class ReviewItem extends RecyclerView.ViewHolder{

        public TextView reviewText;
        public TextView userName;
        public TextView reviewDate;
        public ImageView avatar;
        public Rating rating;

        public ReviewItem(View itemView, TextView reviewText, TextView userName, TextView reviewDate, ImageView avatar, Rating rating) {
            super(itemView);
            this.reviewText = reviewText;
            this.userName = userName;
            this.reviewDate = reviewDate;
            this.avatar = avatar;
            this.rating = rating;
        }
    }

    public void setInfo(String carWashId) {
        Bundle args = getArguments();
        args.putString(ARG_CAR_WASH_ID, carWashId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carWashId = getArguments().getString(ARG_CAR_WASH_ID);
    }

    /**
     * Определяется формат даты для отзыва (сегодня, вчера или вообще)
     * @param reviewDateStr
     * @return
     */
    public String reviewDate(String reviewDateStr){
        String reviewDateFormatted = "";
        try {
            Calendar dateNow = Calendar.getInstance();
            Calendar reviewDate = Calendar.getInstance();
            Date date = formatter.parse(reviewDateStr);

            reviewDate.setTimeInMillis(date.getTime());

            if(dates(dateNow, reviewDate)){
                reviewDateFormatted = "сегодня " + formatTime.format(date);
            } else {
                dateNow.add(Calendar.DATE, -1);

                if (dates(dateNow, reviewDate)) {
                    reviewDateFormatted = "вчера " + formatTime.format(date);
                } else {
                    reviewDateFormatted = formatDay.format(date);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  reviewDateFormatted;
    }

    public Boolean dates(Calendar dateNow, Calendar dateReview){
        if(dateNow.get(Calendar.MONTH) == dateReview.get(Calendar.MONTH)
                && dateNow.get(Calendar.DAY_OF_MONTH) == dateReview.get(Calendar.DAY_OF_MONTH)
                && dateNow.get(Calendar.YEAR) == dateReview.get(Calendar.YEAR)){
            return true;
        }else{
            return false;
        }
    }
}

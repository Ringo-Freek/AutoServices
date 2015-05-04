package auto_services.sequenia.com.autoservices.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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

    private String carWashId;
    private ArrayList<Review> reviews;

    public ReviewsFragment() {
        setIsMain(false);
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
    public void bindViewHolder(RecyclerView.ViewHolder holder, int position, RecyclerView.Adapter adapter) {

        ReviewItem reviewItem = (ReviewItem)holder;
        Review review = reviews.get(position);

        reviewItem.userName.setText(review.getUser_name());
        reviewItem.reviewText.setText(review.getText());
        reviewItem.reviewDate.setText(review.getCreated_at());
        reviewItem.rating.initRating(getActivity(), review.getMark());

        Picasso
                .with(getActivity())
                .load("http://developer.android.com/design/media/notifications_pattern_phone_ticker.png")
                .fit()
                .centerCrop()
                .into(reviewItem.avatar);
    }

    @Override
    public int getDetailFragmentId() {
        return 0;
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

        new ReviewsTask(
                "reviews.json?auth_token="
                        + Global.testToken
                        + "&item_id="
                        + carWashId
                        + "page=0&count=10"){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s != null){
                    JsonResponse<ArrayList<Review>> reviewsResponse = new Gson().fromJson(s, new TypeToken<JsonResponse<ArrayList<Review>>>(){}.getType());
                    if(reviewsResponse.getSuccess()){
                        reviews = reviewsResponse.getData();
                        addObjects(reviews);
                    }
                }
            }
        }.execute();
    }
}

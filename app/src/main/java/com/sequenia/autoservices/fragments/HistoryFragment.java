package com.sequenia.autoservices.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.sequenia.autoservices.R;
import com.sequenia.autoservices.dialogs.CreateReviewDialog;
import com.sequenia.autoservices.dialogs.DialogWindow;
import com.sequenia.autoservices.drawer_fragments.MasterFragment;
import com.sequenia.autoservices.drawer_fragments.PlaceholderFragment;
import com.sequenia.autoservices.objects.HistoryCarWash;
import com.sequenia.autoservices.static_classes.Global;
import com.sequenia.autoservices.static_classes.RealmHelper;
import com.sequenia.autoservices.widgets.Rating;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by chybakut2004 on 30.04.15.
 */
public class HistoryFragment extends MasterFragment {

    private int black54;
    private int amber700;

    private HistoryCarWash editingHistory;

    private Transformation transformation;

    public HistoryFragment() {
        transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(Global.previewCornerRadius)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Resources resources = getActivity().getResources();
        black54 = resources.getColor(R.color.black54);
        amber700 = resources.getColor(R.color.amber_700);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void loadObjects(int page) {
        ArrayList<HistoryCarWash> historyCarWashes = new ArrayList<HistoryCarWash>();
        RealmResults<HistoryCarWash> realmHistory = RealmHelper.getHistory(getActivity());
        for(int i = 0; i < realmHistory.size(); i++) {
            historyCarWashes.add(realmHistory.get(i));
        }
        addObjects(historyCarWashes);
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.car_wash_history_item, container, false);
        ((CardView)view).setPreventCornerOverlap(false);

        ImageView carWashImg = (ImageView)view.findViewById(R.id.car_wash_img);
        TextView carWashName = (TextView)view.findViewById(R.id.car_wash_name);
        TextView carWashDate = (TextView)view.findViewById(R.id.car_wash_date);
        TextView carWashAddress = (TextView)view.findViewById(R.id.car_wash_address);
        TextView carWashSchedule = (TextView)view.findViewById(R.id.car_wash_schedule);
        View setRatingButton = view.findViewById(R.id.set_rating_button);
        View content = view.findViewById(R.id.item_content);

        Rating rating = (Rating)view.findViewById(R.id.mark);
        rating.initRating(getActivity(), 0);

        return new CarWashItem(view, carWashImg, carWashName,
                carWashDate, carWashAddress, carWashSchedule,
                setRatingButton, rating, content);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, int position, RecyclerView.Adapter adapter, Object object) {
        final HistoryCarWash carWash = (HistoryCarWash) object;
        final CarWashItem carWashHolder = (CarWashItem) holder;

        carWashHolder.carWashName.setText(carWash.getName());
        carWashHolder.carWashAddress.setText(carWash.getAddress());
        carWashHolder.carWashSchedule.setText(carWash.getTime_from() + " - " + carWash.getTime_to());

        Picasso
            .with(getActivity())
            .load(carWash.getPreview())
            .fit()
            .centerCrop()
            .transform(transformation)
            .into(carWashHolder.carWashImg);

        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(carWash.getDate());
        Calendar now = Calendar.getInstance();

        carWashHolder.carWashDate.setText(Global.getDateStr(now, date));

        if(carWash.getRating() == 0) {
            carWashHolder.setRatingButton.setVisibility(View.VISIBLE);
            carWashHolder.rating.setVisibility(View.GONE);

            carWashHolder.setRatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRatingWindow(carWash);
                }
            });
        } else {
            carWashHolder.setRatingButton.setVisibility(View.GONE);
            carWashHolder.rating.setVisibility(View.VISIBLE);
            carWashHolder.rating.initRating(getActivity(), carWash.getRating());
        }

        if(Global.dateExpired(date)) {
            carWashHolder.carWashDate.setTextColor(black54);
        } else {
            carWashHolder.carWashDate.setTextColor(amber700);
            carWashHolder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMap();
                }
            });
        }
    }

    private void showRatingWindow(HistoryCarWash carWash) {
        editingHistory = carWash;
        CreateReviewDialog createReviewDialog = (CreateReviewDialog) DialogWindow.customInstance(getResources().getString(R.string.create_review), R.layout.create_review_dialog, new CreateReviewDialog());
        createReviewDialog.setData(Integer.valueOf(carWash.getCarWashId()));
        createReviewDialog.setTargetFragment(this, CreateReviewDialog.CREATE_REVIEW_DIALOG);
        createReviewDialog.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CreateReviewDialog.CREATE_REVIEW_DIALOG) {
            if(resultCode == Activity.RESULT_OK) {
                if(editingHistory != null) {
                    int rating = data.getIntExtra(CreateReviewDialog.RATING_VALUE_DATA, 0);
                    Realm realm = RealmHelper.initRealm(getActivity());
                    realm.beginTransaction();
                    editingHistory.setRating(rating);
                    realm.commitTransaction();
                    getAdapter().notifyDataSetChanged();
                }
            }
        }
    }

    private void showMap() {
        Global.showCurrentReservationFragment(getActivity());
    }

    @Override
    public int getDetailFragmentId() {
        return 0;
    }

    @Override
    public boolean hasEndlessScroll() {
        return false;
    }

    @Override
    public int scrolledToLoading() {
        return 0;
    }

    @Override
    public void setInfoToDetailFragment(Bundle args, Object object) {

    }

    @Override
    public boolean canCreate() {
        return false;
    }

    public class CarWashItem extends RecyclerView.ViewHolder{

        public ImageView carWashImg;
        public TextView carWashName;
        public TextView carWashDate;
        public TextView carWashAddress;
        public TextView carWashSchedule;
        public View setRatingButton;
        public Rating rating;
        public View content;

        public CarWashItem(
                View itemView,
                ImageView carWashImg,
                TextView carWashName,
                TextView carWashDate,
                TextView carWashAddress,
                TextView carWashSchedule,
                View setRatingButton,
                Rating rating,
                View content) {
            super(itemView);
            this.carWashImg = carWashImg;
            this.carWashName = carWashName;
            this.carWashDate = carWashDate;
            this.carWashAddress = carWashAddress;
            this.carWashSchedule  = carWashSchedule;
            this.setRatingButton = setRatingButton;
            this.rating = rating;
            this.content = content;
        }
    }

    public void setUpLayout(View view){
        view.setBackgroundResource(R.color.dark_grey);
    }
}

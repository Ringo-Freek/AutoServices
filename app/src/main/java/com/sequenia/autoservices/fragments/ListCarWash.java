package com.sequenia.autoservices.fragments;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sequenia.autoservices.R;
import com.sequenia.autoservices.activities.MainActivity;
import com.sequenia.autoservices.drawer_fragments.MasterFragment;
import com.sequenia.autoservices.drawer_fragments.PlaceholderFragment;
import com.sequenia.autoservices.objects.CarWash;
import com.sequenia.autoservices.static_classes.Global;
import com.sequenia.autoservices.widgets.Rating;
import com.squareup.picasso.Picasso;

/**
 * Created by Ringo on 11.05.2015.
 */
public class ListCarWash extends MasterFragment {

    public ListCarWash(){
        setIsMain(false);
    }

    @Override
    public void loadObjects(int page) {
        addObjects(((MainMapFragment) getParentFragment()).carWashes);
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.car_wash_item, container, false);
        ((CardView)view).setPreventCornerOverlap(false);

        ImageView carWashImg = (ImageView)view.findViewById(R.id.car_wash_img);
        TextView carWashName = (TextView)view.findViewById(R.id.car_wash_name);
        TextView carWashDistance = (TextView)view.findViewById(R.id.car_wash_distance);
        TextView carWashAddress = (TextView)view.findViewById(R.id.car_wash_address);
        TextView carWashSchedule = (TextView)view.findViewById(R.id.car_wash_schedule);
        TextView carWashMinCoast = (TextView)view.findViewById(R.id.car_wash_min_coast);

        Rating rating = (Rating)view.findViewById(R.id.mark);

        ImageView carWashActions = (ImageView)view.findViewById(R.id.action);

        return new CarWashItem(view, carWashImg, carWashName, carWashDistance, carWashAddress, carWashSchedule, carWashMinCoast, rating, carWashActions);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, int position, RecyclerView.Adapter adapter, Object object) {
        CarWashItem carWashItem = (CarWashItem)holder;
        final CarWash carWash = (CarWash) object;

        /*Picasso
        .with(getActivity())
                .load(carWash.getPreview())
                .fit()
                .centerCrop()
                .error(R.drawable.ic_account_box_grey600_48dp)
                .into(carWashItem.carWashImg)*/

        carWashItem.carWashName.setText(carWash.getName());
        carWashItem.carWashDistance.setText("~" + Math.round(carWash.getDistance()) + " м");
        carWashItem.carWashAddress.setText(carWash.getAddress());
        carWashItem.carWashSchedule.setText(Global.getSchedule(carWash.getTime_from(), carWash.getTime_to()));
        carWashItem.carWashMinCoast.setText("250");

        carWashItem.rating.initRating(getActivity(), Math.round(carWash.getRating()));

        if(carWash.getActions_count() != null){
            carWashItem.carWashActions.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarWashCard fragment = (CarWashCard) PlaceholderFragment.newInstance(DIALOG_SECTION);
                fragment.setInfo(carWash.getId().toString(), carWash.getDistance());
                ((MainActivity)getActivity()).addSubFragment(fragment);
            }
        });
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
    public boolean isInNavigation() {
        return false;
    }

    public class CarWashItem extends RecyclerView.ViewHolder{

        public ImageView carWashImg;
        public TextView carWashName;
        public TextView carWashDistance;
        public TextView carWashAddress;
        public TextView carWashSchedule;
        public TextView carWashMinCoast;
        public Rating rating;
        public ImageView carWashActions;

        public CarWashItem(
                View itemView,
                ImageView carWashImg,
                TextView carWashName,
                TextView carWashDistance,
                TextView carWashAddress,
                TextView carWashSchedule,
                TextView carWashMinCoast,
                Rating rating,
                ImageView carWashActions) {
            super(itemView);
            this.carWashImg = carWashImg;
            this.carWashName = carWashName;
            this.carWashDistance = carWashDistance;
            this.carWashAddress = carWashAddress;
            this.carWashSchedule  = carWashSchedule;
            this.carWashMinCoast = carWashMinCoast;
            this.rating = rating;
            this.carWashActions = carWashActions;
        }
    }

    @Override
    public boolean canCreate() {
        return false;
    }

    public void setUpLayout(View view){
        view.setBackgroundResource(R.color.dark_grey);
    }
}

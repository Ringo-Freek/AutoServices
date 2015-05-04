package auto_services.sequenia.com.autoservices.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import auto_services.sequenia.com.autoservices.R;

/**
 * Created by Ringo on 03.05.2015.
 * Блок для показа рейтинга и отмечания его
 */
public class Rating extends LinearLayout{

    Integer ratingCount;
    Integer countStars = 5;
    Context context;

    public Rating(Context context) {
        super(context);
    }

    public Rating(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initRating(Context context, Integer ratingCount){
        LayoutInflater inflater = LayoutInflater.from(context);

        for(int i = 0; i < countStars; i++ ){
            View view = inflater.inflate(R.layout.rating_star, null);
            ImageView star = (ImageView)view.findViewById(R.id.star);

            if(i < ratingCount){
                star.setImageResource(R.drawable.ic_star_black_24dp);
            }else{
                star.setImageResource(R.drawable.ic_star_outline_grey600_24dp);
            }

            addView(view);
        }
    }
}

package auto_services.sequenia.com.autoservices.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import auto_services.sequenia.com.autoservices.R;

/**
 * Created by Ringo on 03.05.2015.
 * Блок для показа рейтинга и отмечания его
 */
public class Rating extends LinearLayout{

    Integer ratingCount;
    Integer countStars = 5;
    Context context;
    Boolean listener = false;
    ArrayList<View> viewsStar;

    public Rating(Context context) {
        super(context);
    }

    public Rating(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initRating(Context context, Integer ratingCount){
        this.ratingCount = ratingCount;
        LayoutInflater inflater = LayoutInflater.from(context);

        removeAllViews(); // Чтобы при повторном ините не было в 2 раза больше звезд
        viewsStar = new ArrayList<View>();

        for(int i = 0; i < countStars; i++ ){
            View view = inflater.inflate(R.layout.rating_star, null);

            setImage(view, i < ratingCount);

            viewsStar.add(view);
            addView(view);
        }
    }

    /**
     * Вешаем слушатель на клики
     * звездочек и реакцию
     */
    public void setListenerClick(Boolean listener){
        this.listener = listener;
        if(listener){
            clickStarts();
        }else{
            noClickStats();
        }
    }

    /**
     * клик по звезде задает рейтинг
     */
    public void clickStarts(){
        for(int i = 0; i < countStars; i++){
            final int index = i + 1;
            viewsStar.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int j = 0; j < countStars; j++){
                        setImage(viewsStar.get(j), j < index);
                    }

                    ratingCount = index;
                }
            });
        }
    }

    /**
     * убираем слушатель с рейтинга
     */
    public void noClickStats(){
        for(int i = 0; i < countStars; i++){
            viewsStar.get(i).setOnClickListener(null);
        }
    }

    /**
     * устанавливаем кратинку в соотвесвие с рейтингом
     */
    public void setImage(View view, Boolean ratingState){
        ImageView star = (ImageView)view.findViewById(R.id.star);

        if(ratingState){
            star.setImageResource(R.drawable.ic_star_amber700_24dp);
        }else{
            star.setImageResource(R.drawable.ic_star_outline_grey600_24dp);
        }
    }

    public int getRating(){
        return ratingCount;
    }
}

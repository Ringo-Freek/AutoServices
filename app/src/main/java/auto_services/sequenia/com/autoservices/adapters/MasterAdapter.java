package auto_services.sequenia.com.autoservices.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 30.04.15.
 */
public abstract class MasterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Object> objects;

    public MasterAdapter(ArrayList<Object> objects) {
        this.objects = objects;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}

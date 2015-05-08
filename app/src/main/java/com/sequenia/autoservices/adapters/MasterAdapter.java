package com.sequenia.autoservices.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by chybakut2004 on 30.04.15.
 */
public abstract class MasterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM = 0;
    public static final int RELOADING = 1;

    private ArrayList<Object> objects;

    public MasterAdapter(ArrayList<Object> objects) {
        this.objects = objects;
    }

    @Override
    public int getItemCount() {
        if(reloadingShown()) {
            return objects.size() + 1;
        } else {
            return objects.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position < objects.size()) {
            return ITEM;
        } else {
            return RELOADING;
        }
    }

    public boolean reloadingShown() {
        return false;
    }
}

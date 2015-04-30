package auto_services.sequenia.com.autoservices.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.drawer_fragments.MasterFragment;
import auto_services.sequenia.com.autoservices.drawer_fragments.PlaceholderFragment;

/**
 * Created by chybakut2004 on 30.04.15.
 */
public class MyCarFragment extends MasterFragment {

    private ArrayList<Object> strings;

    public MyCarFragment(){
        strings = new ArrayList<Object>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        addObjects(strings);
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.master_item, container, false);

        TextView textView = (TextView) view.findViewById(R.id.text);

        return new MyCarViewHolder(view, textView);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyCarViewHolder) holder).text.setText((String) strings.get(position));
    }

    public class MyCarViewHolder extends RecyclerView.ViewHolder {

        public TextView text;

        public MyCarViewHolder(View itemView, TextView text) {
            super(itemView);
            this.text = text;
        }
    }
}

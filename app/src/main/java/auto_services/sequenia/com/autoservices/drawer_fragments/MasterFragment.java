package auto_services.sequenia.com.autoservices.drawer_fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.adapters.MasterAdapter;

/**
 * Используется для отображения списка элементов с возможностью открыть форму создания
 * или редактирования.
 *
 * T - тип вьюхолдера, используемого в адаптере
 *
 * Created by chybakut2004 on 30.04.15.
 */
public abstract class MasterFragment extends PlaceholderFragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Object> objects;

    public MasterFragment() {
        objects = new ArrayList<Object>();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master, container, false);

        final MasterFragment self = this;

        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new MasterAdapter(objects) {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return self.createViewHolder(inflater, container);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                self.bindViewHolder(holder, position, this);
            }
        };

        recyclerView = (RecyclerView) rootView.findViewById(R.id.items_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public void addObjects(ArrayList<Object> newObjects) {
        this.objects.addAll(newObjects);
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public abstract RecyclerView.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup container);
    public abstract void bindViewHolder(RecyclerView.ViewHolder holder, int position, RecyclerView.Adapter adapter);
}

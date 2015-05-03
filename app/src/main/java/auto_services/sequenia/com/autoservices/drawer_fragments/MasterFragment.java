package auto_services.sequenia.com.autoservices.drawer_fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.activities.MainActivity;
import auto_services.sequenia.com.autoservices.adapters.MasterAdapter;

/**
 * Используется для отображения списка элементов с возможностью открыть форму создания
 * или редактирования.
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

    /**
     * Создание контента и адаптера для рисования элементов.
     * Так же здесь происходит обработка кнопки СОЗДАТЬ.
     *
     * Адаптер имеет 2 абстрактных метода (createViewHolder и bindViewHolder),
     * которые должны быть перегружены в дочерних классах
     * для создания и заполнения элементов списка соответственно.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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

        rootView.findViewById(R.id.create_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailFragment(DetailFragment.NO_ITEM);
            }
        });

        return rootView;
    }

    /**
     * Добавляет объекты в список, после чего они отображаются на экране
     *
     * @param newObjects
     */
    public void addObjects(ArrayList<Object> newObjects) {
        this.objects.addAll(newObjects);
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Открывает форму редактирования.
     *
     * Для открытия формы создания подайте DetailFragment.NO_ITEM в itemId.
     *
     * @param itemId
     */
    public void showDetailFragment(int itemId) {
        DetailFragment fragment = (DetailFragment) PlaceholderFragment.newInstance(getDetailFragmentId());
        fragment.setId(itemId);
        ((MainActivity) getActivity()).addSubFragment(fragment);
    }

    /**
     * В этом методе создается вид одного элемента списка
     *
     * @param inflater
     * @param container
     * @return
     */
    public abstract RecyclerView.ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup container);

    /**
     * В этом методе заполняются данные в элементе списка
     *
     * @param holder
     * @param position
     * @param adapter
     */
    public abstract void bindViewHolder(RecyclerView.ViewHolder holder, int position, RecyclerView.Adapter adapter);

    /**
     * Возвратить в этом методе id фрагмента для создания/редактирования.
     * @return
     */
    public abstract int getDetailFragmentId();
}

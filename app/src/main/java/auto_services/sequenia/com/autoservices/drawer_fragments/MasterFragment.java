package auto_services.sequenia.com.autoservices.drawer_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.activities.MainActivity;
import auto_services.sequenia.com.autoservices.adapters.MasterAdapter;
import auto_services.sequenia.com.autoservices.listeners.EndlessScrollListener;

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
    private ProgressBar progressBar;
    private ProgressBar scrollProgressBar;

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

        initList(rootView, inflater, container);
        initButtons(rootView);
        initProgress(rootView);

        updateProgressAndLoadObjects(0);

        return rootView;
    }

    private void initList(View rootView, final LayoutInflater inflater, final ViewGroup container) {
        final MasterFragment self = this;
        Activity activity = getActivity();

        layoutManager = new LinearLayoutManager(activity);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.items_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MasterAdapter(objects) {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return self.createViewHolder(inflater, container);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                self.bindViewHolder(holder, position, this, objects.get(position));
            }
        };
        recyclerView.setAdapter(adapter);

        if(hasEndlessScroll()) {
            resetScrollListener(activity);
        }
    }

    private void initButtons(View rootView) {
        rootView.findViewById(R.id.create_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailFragment(DetailFragment.NO_ITEM, DetailFragment.NO_ITEM);
            }
        });
    }

    private void initProgress(View rootView) {
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);
        scrollProgressBar = (ProgressBar) rootView.findViewById(R.id.scroll_progress);
    }

    private void resetScrollListener(Activity activity) {
        recyclerView.setOnScrollListener(null);
        objects.clear();
        recyclerView.setOnScrollListener(new EndlessScrollListener(activity, layoutManager, scrolledToLoading()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadObjects(page);
            }
        });
    }

    @Override
    public void resumeFragment() {
        super.resumeFragment();
        resetScrollListener(getActivity());
        updateProgressAndLoadObjects(0);
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void showScrollProgress() {
        scrollProgressBar.setVisibility(View.VISIBLE);
    }

    public void showList() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    public void hideScrollProgress() {
        scrollProgressBar.setVisibility(View.GONE);
    }

    public void hideList() {
        progressBar.setVisibility(View.GONE);
    }

    private void updateProgressAndLoadObjects(int page) {
        if(page == 0) {
            showProgress();
            hideScrollProgress();
        } else {
            showScrollProgress();
            hideProgress();
        }

        loadObjects(page);
    }

    /**
     * Добавляет объекты в список, после чего они отображаются на экране
     *
     * @param newObjects
     */
    public void addObjects(ArrayList newObjects) {
        hideProgress();
        hideScrollProgress();
        this.objects.addAll(newObjects);
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Вызывается для загрузки новых данных при прокрутке
     */
    public abstract void loadObjects(int page);

    /**
     * Открывает форму редактирования.
     *
     * Для открытия формы создания подайте DetailFragment.NO_ITEM в itemId.
     *
     * @param itemId
     */
    public void showDetailFragment(int itemId, int position) {
        DetailFragment fragment = (DetailFragment) PlaceholderFragment.newInstance(getDetailFragmentId());
        fragment.setId(itemId);
        if(position >= 0) {
            setInfoToDetailFragment(fragment.getArguments(), objects.get(position));
        }
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
    public abstract void bindViewHolder(RecyclerView.ViewHolder holder, int position, RecyclerView.Adapter adapter, Object object);

    /**
     * Возвратить в этом методе id фрагмента для создания/редактирования.
     *
     * @return
     */
    public abstract int getDetailFragmentId();

    /**
     * Есть ли подгрузка при прокрутке
     *
     * @return
     */
    public abstract boolean hasEndlessScroll();


    /**
     * Сколько элементов нужно пролистать, чтобы начать грузить новые
     *
     * @return
     */
    public abstract int scrolledToLoading();

    /**
     * С помощью этого метода можно передавать информацию в Detail Fragment.
     *
     * Поместить информацию в args из object
     *
     * @param args
     * @param object
     */
    public abstract void setInfoToDetailFragment(Bundle args, Object object);
}

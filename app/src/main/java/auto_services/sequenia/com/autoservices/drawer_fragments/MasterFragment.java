package auto_services.sequenia.com.autoservices.drawer_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private MasterAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    private Button reloadButton;
    private boolean scrollReloadingShown = false;
    private RecyclerView.OnScrollListener onScrollListener;

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
        initReloadButton(rootView);

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
                if(viewType == ITEM) {
                    return self.createViewHolder(inflater, parent);
                } else {
                    return createScrollReloadingButton(inflater, parent);
                }
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                if(getItemViewType(position) == ITEM) {
                    self.bindViewHolder(holder, position, this, objects.get(position));
                } else {
                    bindScrollReloadingButton(holder);
                }
            }

            @Override
            public boolean reloadingShown() {
                return scrollReloadingShown;
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
    }

    private void initReloadButton(View rootView) {
        reloadButton = (Button) rootView.findViewById(R.id.reload_button);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideReloading();
                reset();
            }
        });
        hideReloadButton();
    }

    private RecyclerView.ViewHolder createScrollReloadingButton(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.reloading_button, parent, false);
        Button button = (Button) view.findViewById(R.id.list_reloading_button);
        return new ReloadingViewHolder(view, button);
    }

    private void bindScrollReloadingButton(RecyclerView.ViewHolder holder) {
        ((ReloadingViewHolder) holder).button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollReloadingShown = false;
                adapter.notifyDataSetChanged();
                ((EndlessScrollListener) onScrollListener).loadMore();
            }
        });
    }

    private void resetScrollListener(Activity activity) {
        recyclerView.setOnScrollListener(null);
        objects.clear();
        onScrollListener = new EndlessScrollListener(activity, layoutManager, scrolledToLoading()) {
            @Override
            public void onLoadMore(int page) {
                updateProgressAndLoadObjects(page);
            }

            @Override
            public boolean hasExtraLine() {
                return scrollReloadingShown;
            }
        };
        recyclerView.setOnScrollListener(onScrollListener);
    }

    @Override
    public void resumeFragment() {
        super.resumeFragment();
        reset();
    }

    private void reset() {
        resetScrollListener(getActivity());
        updateProgressAndLoadObjects(0);
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private void hideList() {
        recyclerView.setVisibility(View.GONE);
    }

    private void showList() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showReloadButton() {
        reloadButton.setVisibility(View.VISIBLE);
    }

    private void hideReloadButton() {
        reloadButton.setVisibility(View.GONE);
    }

    public void showReloading(int page) {
        if(page == 0) {
            hideProgress();
            hideList();
            showReloadButton();
        } else {
            hideProgress();
            scrollReloadingShown = true;
            adapter.notifyDataSetChanged();
        }
    }

    public void hideReloading() {
        hideProgress();
        hideReloadButton();
        showList();
    }

    private void updateProgressAndLoadObjects(int page) {
        if(page == 0) {
            showProgress();
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

    private static class ReloadingViewHolder extends RecyclerView.ViewHolder {

        public Button button;

        public ReloadingViewHolder(View itemView, Button button) {
            super(itemView);
            this.button = button;
        }
    }
}

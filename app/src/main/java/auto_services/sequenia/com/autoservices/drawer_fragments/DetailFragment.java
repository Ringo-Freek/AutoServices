package auto_services.sequenia.com.autoservices.drawer_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import auto_services.sequenia.com.autoservices.R;
import auto_services.sequenia.com.autoservices.activities.MainActivity;

/**
 * Фрагмент является формой редактирования/создания элементов.
 *
 * Если задан item_id, то действует как редактирование записи.
 * Если item_id не задан (Равен NO_ITEM), то действует как создание записи.
 *
 * Created by chybakut2004 on 30.04.15.
 */
public abstract class DetailFragment extends PlaceholderFragment {

    public static final int NO_ITEM = -1; // itemId при отсутсвии записи (Ее создании)

    private static final String ARG_ITEM_ID = "ItemId";
    private int itemId;
    private Object item;

    /**
     * Тут создается контент и привязываются обработчики для кнопок
     * ПОДТВЕРДИТЬ и УДАЛИТЬ.
     * Так же здесь происходит загрузка редактируемых данных с сервера или из БД,
     * если itemId задан.
     *
     * Обработчики вызывают методы updateItem и deleteItem при редактировании записи и
     * createItem и cancel при создании записи.
     * Эти методы должны быть реализованы в дочерних классах.
     *
     * Создание записи имеет место, когда itemId = NO_ITEM.
     * Редактирование записи имеет место, когда itemId >= 0.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.detail_content);
        View content = createContent(inflater, layout);
        layout.addView(content);

        Button confirmButton = (Button) view.findViewById(R.id.confirm_button);
        Button deleteButton = (Button) view.findViewById(R.id.delete_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordExists()) {
                    if(item != null) {
                        updateItem(itemId);
                    }
                } else {
                    createItem();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordExists()) {
                    if(item != null) {
                        deleteItem(itemId);
                    }
                } else {
                    cancel();
                }
            }
        });

        if(recordExists()) {
            getItem(itemId);
        }

        return view;
    }

    /**
     * Задает id редактируемой записи.
     *
     * @param itemId
     */
    public void setId(int itemId) {
        Bundle args = getArguments();
        args.putInt(ARG_ITEM_ID, itemId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemId = getArguments().getInt(ARG_ITEM_ID, -1);
    }

    /**
     * Возвращает id редактируемой записи
     *
     * @return
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * true, когда запись существует (Редактирование).
     * false, когда запись не существует (Создание)
     * @return
     */
    public boolean recordExists() {
        return getItemId() > -1;
    }

    /**
     * Этим методом можно закрыть фрагмент
     */
    public void close() {
        ((MainActivity) getActivity()).onBackPressed();
    }

    /**
     * Этот метод нужно вызвать после получения реадктируемой записи.
     * 
     * @param item
     */
    public void setItem(Object item) {
        this.item = item;
    }

    /**
     * В этом методе нужно создать весь контент
     *
     * @param inflater
     * @param layout
     * @return
     */
    public abstract View createContent(LayoutInflater inflater, LinearLayout layout);

    /**
     * Вызывается для создания записи
     */
    public abstract void createItem();

    /**
     * Вызывается для обновления записи
     */
    public abstract void updateItem(int itemId);

    /**
     * Вызывается для удаления записи
     */
    public abstract void deleteItem(int itemId);

    /**
     * Вызывается для отмены (Нажание на удалить, когда записи нет)
     */
    public abstract void cancel();

    /**
     * Вызывается для получения записи (От сервера или из базы)
     * @return
     */
    public abstract Object getItem(int itemId);
}

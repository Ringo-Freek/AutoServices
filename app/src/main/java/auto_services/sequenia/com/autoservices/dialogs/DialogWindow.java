package auto_services.sequenia.com.autoservices.dialogs;

/**
 * Created by Ringo on 06.05.2015.
 * диалоговое окно с собственным заголовком и разметкой
 */

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import auto_services.sequenia.com.autoservices.R;

public abstract class DialogWindow extends DialogFragment {

    public static final String ARG_TITLE = "title";
    public static final String ARG_LAYOUT = "layout";
    public View view;

    public static DialogWindow customInstance(String title, int layout, DialogWindow fragment) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putInt(ARG_LAYOUT, layout);
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(getLayout(), null);

        ((TextView)view.findViewById(R.id.dialog_header)).setText(getTitle());

        view.findViewById(R.id.dialog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        initViewElements();
        return view;
    }

    private String getTitle(){
        return getArguments().getString(ARG_TITLE);
    }

    private int getLayout(){
        return getArguments().getInt(ARG_LAYOUT);
    }

    public abstract void doneCloseDialog();

    public abstract void initViewElements();

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() == null) {
            return;
        }

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}

package com.sequenia.autoservices.drawer_fragments;

import android.view.Menu;

import com.sequenia.autoservices.activities.MainActivity;

/**
 * Диалог. Отличия от обычного фрагмента:
 * - использует вид тулбара как у предыдущего открытого фрагмента.
 * - затемяет рабочее пространство за собой
 *
 * Created by chybakut2004 on 30.04.15.
 */
public class PlaceholderDialogFragment extends PlaceholderFragment {

    public PlaceholderDialogFragment() {
        setShadowVisible(true);
    }

    @Override
    public void restoreMenu(Menu menu) {
        MainActivity activity = (MainActivity) getActivity();
        activity.restorePreviousFragmentMenu(this);
    }
}

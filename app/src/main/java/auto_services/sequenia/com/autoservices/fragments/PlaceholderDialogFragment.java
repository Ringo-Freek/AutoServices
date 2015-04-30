package auto_services.sequenia.com.autoservices.fragments;

import android.view.Menu;

import auto_services.sequenia.com.autoservices.activities.MainActivity;
import auto_services.sequenia.com.autoservices.drawer_fragment.PlaceholderFragment;

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

package sngular.com.atmsmap.presentation.util;

import android.os.Handler;
import android.widget.CheckBox;

/**
 * Created by alberto.hernandez on 07/04/2016.
 */
public class ViewUtils {
    public static void cancelCheckedDelay(final CheckBox mActionOpenATMsFinder, long duration) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                mActionOpenATMsFinder.setChecked(false);
            }
        }, duration);
    }
}

package sngular.com.awesomemap.utils;

import android.app.Activity;
import android.util.Log;

/**
 * Created by julio.molinera on 18/06/2015.
 */
public class ResourcesValidator {

    public static boolean isValidDrawableResource(Activity activity, int resource) {
        try {
            activity.getResources().getDrawable(resource);
        } catch (Exception e) {
            Log.e("Error", "No se ha encontrado el recurso", e);
            return false;
        }
        return true;
    }

}

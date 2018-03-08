package sngular.com.atmsmap.presentation.info;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import sngular.com.atmsmap.app.constant.AppConstants;

/**
 * Created by alberto.hernandez on 26/04/2016.
 */

@Singleton
public class InfoRSI implements InfoRSIService {

    private final Context mContext;

    public InfoRSI(Context ctx) {
        mContext = ctx;
    }

    @Override
    public void setEntity(String entity) {
        SharedPreferences mPreferences = mContext.getSharedPreferences(AppConstants.APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(AppConstants.ENTITY_PREF, entity);
        editor.apply();
    }

    @Override
    public void setEntityHelpTitle(String entityHelpTitle) {
        SharedPreferences mPreferences = mContext.getSharedPreferences(AppConstants.APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(AppConstants.ENTITY_HELP_TITLE_INTENT, entityHelpTitle);
        editor.apply();
    }

    @Override
    public void setEntityHelpGroups(boolean showGroupAtms) {
        SharedPreferences mPreferences = mContext.getSharedPreferences(AppConstants.APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(AppConstants.ENTITY_HELP_GROUPS_INTENT, showGroupAtms);
        editor.apply();
    }

    @Override
    public void setGoogleMapsKeys(String googleMapsWebKey, String googleMapsKey, Context context) {
        SharedPreferences mPreferences = context.getSharedPreferences(AppConstants.APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(AppConstants.MAP_WEB_KEY_PREF, googleMapsWebKey);
        editor.putString(AppConstants.MAP_KEY_PREF, googleMapsKey);
        editor.apply();
    }


    public void setTheme(String entity) {
        //TODO
    }
}

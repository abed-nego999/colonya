package sngular.com.atmsmap.presentation.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import sngular.com.atmsmap.app.constant.AppConstants;
import sngular.com.atmsmap.presentation.view.activity.MapActivity;
import sngular.com.atmsmap.presentation.view.activity.SearchActivity;
import sngular.com.awesomemap.net.model.LatLon;

/**
 * Created by alberto.hernandez on 04/04/2016.
 */
public class IntentNavigation {

    public static void openATMMapModule(Context context, String googleMapsWebKey, String googleMapsKey,
                                        String entity, String atmsUrl, String officesUrl, String entityPlaceHelpTitle,
                                        boolean showGroupAtms) {
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(AppConstants.MAP_WEB_KEY_INTENT, googleMapsWebKey);
        intent.putExtra(AppConstants.MAP_KEY_INTENT, googleMapsKey);
        intent.putExtra(AppConstants.ENTITY_INTENT, entity);
        intent.putExtra(AppConstants.ATMS_INTENT, atmsUrl);
        intent.putExtra(AppConstants.OFFICES_INTENT, officesUrl);
        intent.putExtra(AppConstants.ENTITY_HELP_TITLE_INTENT, entityPlaceHelpTitle);
        intent.putExtra(AppConstants.ENTITY_HELP_GROUPS_INTENT, showGroupAtms);
        context.startActivity(intent);
    }

    public static void openSearchActivity(Activity act) {
        Intent intent = new Intent(act, SearchActivity.class);
        act.startActivityForResult(intent, AppConstants.CODE_SEARCH);
    }

    public static void openNavigation(Context context, LatLon destiny, LatLon origin) {
        String url;
        if (origin == null) {
            url = "http://maps.google.com/maps?f=d&daddr=" + destiny.getLatitude() + "," +
                    destiny.getLongitude() + "&dirflg=w";
        } else {
            url = "http://maps.google.com/maps?saddr=" + origin.getLatitude() + "," + origin.getLongitude() +
                    "&daddr=" + destiny.getLatitude() + "," + destiny.getLongitude() + "&dirflg=w";
        }
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    public static void phoneCall(Activity act, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        act.startActivity(intent);
    }
}

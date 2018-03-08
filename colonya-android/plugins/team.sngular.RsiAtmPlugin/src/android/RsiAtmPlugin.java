package team.sngular;

import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sngular.com.atmsmap.presentation.navigation.IntentNavigation;

public class RsiAtmPlugin extends CordovaPlugin {

    CordovaInterface mCordova;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView){
        super.initialize(cordova, webView);
        Log.e("PLUGIN", "INITIALIZE");
        mCordova = cordova;
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException{
        if("init".equals(action)){
            Log.e("PLUGIN", "INIT");
            JSONObject configJson = args.optJSONObject(0);
            if(configJson == null){
                configJson = new JSONObject();
            }

            Log.e("PLUGIN", configJson.toString());

            final String atmsUrl = configJson.getString("atmsURL");
            Log.e("PLUGIN", "atms "+atmsUrl);
            final String officesUrl = configJson.getString("officesURL");
            Log.e("PLUGIN", "offices " +officesUrl);
            final String googleMapsWebKey = configJson.getString("googleMapsWebKey");
            Log.e("PLUGIN", "maps web " +googleMapsWebKey);
            //La siguiente clave de google debe ir en el manifest de la app top-level, el framework no lo necesita.
            final String googleMapsKey = configJson.getString("googleMapsAndroidKey");
            Log.e("PLUGIN", "maps key" +googleMapsKey);

            final String entity = configJson.getString("entity");
            Log.e("PLUGIN", "entity "+entity);
            
            final String entityPlaceHelpTitle = configJson.getString("entityPlaceHelpTitle");
            Log.e("PLUGIN", "entityPlaceHelpTitle "+entityPlaceHelpTitle);
            
            final boolean showGroupATMs = configJson.getBoolean("showGroupATMs");
            Log.e("PLUGIN", "showGroupATMs "+ String.valueOf(showGroupATMs));

            IntentNavigation.openATMMapModule(mCordova.getActivity(), googleMapsWebKey, googleMapsKey, entity, atmsUrl, officesUrl, entityPlaceHelpTitle, showGroupATMs);

            callbackContext.success();

            return super.execute(action, args, callbackContext);
        }
        return false;
    }
}

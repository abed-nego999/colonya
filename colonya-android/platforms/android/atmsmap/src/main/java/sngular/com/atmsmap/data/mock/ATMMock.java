package sngular.com.atmsmap.data.mock;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import sngular.com.atmsmap.data.model.atm.ATMFullResponseEntity;
import sngular.com.atmsmap.data.model.office.OfficeFullResponseEntity;

/**
 * Created by alberto.hernandez on 11/04/2016.
 */
public class ATMMock {
    private static final String TAG = ATMMock.class.getSimpleName();

    private ATMMock() {
        //Empty Constructor
    }

    public static ATMFullResponseEntity getATMFullResponse(Context ctx) {
        String json = loadJSONFromAsset(ctx, "mock/ATMsSuccess.json");
        ATMFullResponseEntity atmFullResponseEntity;
        try {
            JSONObject atmObject = new JSONObject(json);
            String value = atmObject.getJSONObject("EE_O_LocalizadorCajeros").toString();
            Gson mGson = new Gson();
            atmFullResponseEntity = mGson.fromJson(value, ATMFullResponseEntity.class);

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
        return atmFullResponseEntity;
    }

    public static OfficeFullResponseEntity getOfficeFullResponse(Context ctx) {
        String json = loadJSONFromAsset(ctx, "mock/Offices.json");
        OfficeFullResponseEntity officeFullResponseEntity;
        try {
            JSONObject officeObject = new JSONObject(json);
            String value = officeObject.getJSONObject("EE_O_LocalizadorOficinas").toString();
            Gson mGson = new Gson();
            officeFullResponseEntity = mGson.fromJson(value, OfficeFullResponseEntity.class);

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
        return officeFullResponseEntity;
    }

    public static String loadJSONFromAsset(Context ctx, String path) {
        String json;
        try {
            InputStream is = ctx.getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            int count = is.read(buffer);
            if (count < 1) {
                Log.e(TAG, "No hay datos en json");
            }
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
        return json;
    }
}

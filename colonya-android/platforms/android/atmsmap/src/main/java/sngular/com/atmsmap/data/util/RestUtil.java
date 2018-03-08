package sngular.com.atmsmap.data.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sngular.com.atmsmap.R;
import sngular.com.atmsmap.app.constant.AppConstants;
import sngular.com.atmsmap.data.model.atm.ATMEntity;
import sngular.com.atmsmap.data.model.atm.ATMFullResponseEntity;
import sngular.com.atmsmap.data.model.atm.ATMFullResponseSingleEntity;
import sngular.com.atmsmap.data.model.atm.ATMResponseEntity;
import sngular.com.atmsmap.data.model.office.OfficeEntity;
import sngular.com.atmsmap.data.model.office.OfficeFullResponseEntity;
import sngular.com.atmsmap.data.model.office.OfficeFullResponseSingleEntity;
import sngular.com.atmsmap.data.model.office.OfficeResponseEntity;
import sngular.com.atmsmap.presentation.model.map.ScreenCenterAndRadius;

/**
 * Created by alberto.hernandez on 28/04/2016.
 */
public class RestUtil {
    private RestUtil() {
        //Empty Constructor
    }

    public static void saveURLs(String atmsUrl, String officesUrl, Context mContext) {
        SharedPreferences mPreferences = mContext.getSharedPreferences(AppConstants.APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(AppConstants.ATMS_URL_PREF, atmsUrl);
        editor.putString(AppConstants.OFFICES_URL_PREF, officesUrl);
        editor.apply();
    }


    public static String createGetATMURL(ScreenCenterAndRadius screenCenterAndRadius, Context mContext) {
        SharedPreferences mPreferences = mContext.getSharedPreferences(AppConstants.APP_PREFS, Context.MODE_PRIVATE);
        String atmUrl = mPreferences.getString(AppConstants.ATMS_URL_PREF, "");
        return createURL(screenCenterAndRadius, atmUrl, mContext);
    }

    public static String createGetOfficeURL(ScreenCenterAndRadius screenCenterAndRadius, Context mContext) {
        SharedPreferences mPreferences = mContext.getSharedPreferences(AppConstants.APP_PREFS, Context.MODE_PRIVATE);
        String officeUrl = mPreferences.getString(AppConstants.OFFICES_URL_PREF, "");
        return createURL(screenCenterAndRadius, officeUrl, mContext);
    }

    private static String createURL(ScreenCenterAndRadius screenCenterAndRadius, String baseUrl, Context mContext) {
        String entity = mContext.getString(R.string.ent_code);
        SharedPreferences mPreferences = mContext.getSharedPreferences(AppConstants.APP_PREFS, Context.MODE_PRIVATE);
        String entityCode = mPreferences.getString(AppConstants.ENTITY_PREF, "");
        String longitude = mContext.getString(R.string.longitude);
        String latitude = mContext.getString(R.string.latitude);
        String radius = mContext.getString(R.string.radius);

        return baseUrl + entity + entityCode + longitude + screenCenterAndRadius.getCenterScreenLongitude() +
                latitude + screenCenterAndRadius.getCenterScreenLatitude() + radius +
                calculateRadius(screenCenterAndRadius.getRadius());
    }


    public static Map<String, String> createGetATMHeaders(Context mContext) {
        HashMap<String, String> headers = new HashMap<>();
        SharedPreferences mPreferences = mContext.getSharedPreferences(AppConstants.APP_PREFS, Context.MODE_PRIVATE);
        headers.put(mContext.getString(R.string.sec_ent), mPreferences.getString(AppConstants.ENTITY_PREF, ""));
        headers.put(mContext.getString(R.string.sec_user), mContext.getString(R.string.sec_user_value));
        headers.put(mContext.getString(R.string.sec_trans), mContext.getString(R.string.sec_trans_value));
        headers.put(mContext.getString(R.string.terminal), mContext.getString(R.string.terminal_value));
        headers.put(mContext.getString(R.string.apl), mContext.getString(R.string.apl_value));
        headers.put(mContext.getString(R.string.canal), mContext.getString(R.string.canal_value));
        headers.put(mContext.getString(R.string.sec_ip), mContext.getString(R.string.sec_ip_value));
        return headers;
    }

    public static ATMFullResponseEntity convertSingleATMEntity(ATMFullResponseSingleEntity atmFullResponseSingleEntity) {
        ATMFullResponseEntity atmFullResponseEntity = new ATMFullResponseEntity();
        atmFullResponseEntity.setCodigoRetorno(atmFullResponseSingleEntity.getCodigoRetorno());
        atmFullResponseEntity.setErrores(atmFullResponseSingleEntity.getErrores());
        ATMResponseEntity atmResponseEntity = new ATMResponseEntity();
        atmResponseEntity.setNumeroRegistros(atmFullResponseSingleEntity.getRespuesta().getNumeroRegistros());
        ATMEntity singleEntity = atmFullResponseSingleEntity.getRespuesta().getListaCajeros();
        List<ATMEntity> listSingleEntity = new ArrayList<>();
        listSingleEntity.add(singleEntity);
        atmResponseEntity.setListaCajeros(listSingleEntity);
        atmFullResponseEntity.setRespuesta(atmResponseEntity);
        return atmFullResponseEntity;
    }

    public static OfficeFullResponseEntity convertSingleOfficeEntity(OfficeFullResponseSingleEntity officeFullResponseSingleEntity) {
        OfficeFullResponseEntity officeFullResponseEntity = new OfficeFullResponseEntity();
        officeFullResponseEntity.setCodigoRetorno(officeFullResponseSingleEntity.getCodigoRetorno());
        officeFullResponseEntity.setErrores(officeFullResponseSingleEntity.getErrores());
        OfficeResponseEntity officeResponseEntity = new OfficeResponseEntity();
        officeResponseEntity.setNumeroRegistros(officeFullResponseSingleEntity.getRespuesta().getNumeroRegistros());
        OfficeEntity singleEntity = officeFullResponseSingleEntity.getRespuesta().getListaLocalizacion();
        List<OfficeEntity> listSingleEntity = new ArrayList<>();
        listSingleEntity.add(singleEntity);
        officeResponseEntity.setListaLocalizacion(listSingleEntity);
        officeFullResponseEntity.setRespuesta(officeResponseEntity);
        return officeFullResponseEntity;
    }

    private static String calculateRadius(Integer radius) {
        if (radius > 99000) {
            return "99";
        } else if (radius < 1000) {
            return "1";
        } else {
            int newRadius = (radius + 500) / 1000 * 1000;
            return String.valueOf(newRadius / 1000);
        }
    }
}

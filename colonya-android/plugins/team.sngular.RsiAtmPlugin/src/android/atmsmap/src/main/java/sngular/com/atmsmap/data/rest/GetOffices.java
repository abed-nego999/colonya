package sngular.com.atmsmap.data.rest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import javax.inject.Singleton;

import sngular.com.atmsmap.R;
import sngular.com.atmsmap.app.RSIApplication;
import sngular.com.atmsmap.app.constant.AppConstants;
import sngular.com.atmsmap.data.listener.OnOfficesRequest;
import sngular.com.atmsmap.data.mock.ATMMock;
import sngular.com.atmsmap.data.model.ErrorEntity;
import sngular.com.atmsmap.data.model.ErrorOfficeEntity;
import sngular.com.atmsmap.data.model.ResponseEntity;
import sngular.com.atmsmap.data.model.office.OfficeFullResponseEntity;
import sngular.com.atmsmap.data.model.office.OfficeFullResponseSingleEntity;
import sngular.com.atmsmap.data.util.RestUtil;
import sngular.com.atmsmap.presentation.model.map.ScreenCenterAndRadius;

/**
 * Created by alberto.hernandez on 21/04/2016.
 */
@Singleton
public class GetOffices implements GetOfficesService {
    public static final String TAG = GetOffices.class.getSimpleName();
    private static final long WAIT_TIME = 1000;
    private final Context mContext;

    public GetOffices(Context ctx) {
        mContext = ctx;
    }

    @Override
    public void getOffices(ScreenCenterAndRadius screenCenterAndRadius, final OnOfficesRequest onOfficesRequest) {
        String tag = "getOfficesReq";
        String url = RestUtil.createGetOfficeURL(screenCenterAndRadius, mContext);
        Log.d(TAG, "URL Request: " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        OfficeFullResponseEntity officeFullResponseEntity;
                        try {
                            String assString = "jsonCallback";
                            if (response.startsWith(assString)) {
                                response = response.substring(assString.length() + 1, response.length() - 1);
                            }
                            JSONObject atmObject = new JSONObject(response);
                            String value = atmObject.getJSONObject(mContext.getString(R.string.office_output)).toString();
                            Gson mGson = new Gson();
                            ResponseEntity responseEntity = mGson.fromJson(value, ResponseEntity.class);

                            if (responseEntity.getCodigoRetorno().equalsIgnoreCase(AppConstants.RESPONSE_CODE_SUCCESS)) {
                                if (responseEntity.getRespuesta() != null) {
                                    Log.d(TAG, "Número de oficinas: " + responseEntity.getRespuesta().getNumeroRegistros());
                                    if (Integer.valueOf(responseEntity.getRespuesta().getNumeroRegistros()) > 1) {
                                        officeFullResponseEntity = mGson.fromJson(value, OfficeFullResponseEntity.class);
                                    } else {
                                        OfficeFullResponseSingleEntity officeFullResponseSingleEntity = mGson.fromJson
                                                (value, OfficeFullResponseSingleEntity.class);
                                        officeFullResponseEntity = RestUtil.convertSingleOfficeEntity(officeFullResponseSingleEntity);
                                    }
                                    if (officeFullResponseEntity != null) {
                                        onOfficesRequest.onResponse(officeFullResponseEntity);
                                    }
                                }
                            } else if (responseEntity.getCodigoRetorno().equalsIgnoreCase(AppConstants.RESPONSE_CODE_FAIL)) {
                                if (responseEntity.getErrores() != null) {
                                    onOfficesRequest.onErrorResponse(responseEntity.getErrores());
                                } else if (responseEntity.getOfficeErrores() != null) {
                                    ErrorOfficeEntity errores = responseEntity.getOfficeErrores();
                                    ErrorEntity errorEntity = new ErrorEntity();
                                    errorEntity.setMensajeMostrar(errores.getMensajeMostrar().getText());
                                    errorEntity.setSolucion(errores.getSolucion().getText());
                                    onOfficesRequest.onErrorResponse(errorEntity);
                                }
                            }
                        } catch (JSONException exception) {
                            Log.e(TAG, "Error  Json: " + exception);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error Response: " + error);
                ErrorEntity errorEntity = new ErrorEntity();
                errorEntity.setMensajeMostrar(mContext.getString(R.string.dialog_error_title));
                errorEntity.setSolucion(mContext.getString(R.string.dialog_error_service));
                onOfficesRequest.onErrorResponse(errorEntity);
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, e.getMessage(), e);
                    return Response.error(new ParseError(e));
                }
            }
        };
        RSIApplication.getInstance().addToRequestQueue(stringRequest, tag);
    }

    //TODO Separar la lógica de Mock en flavours
    @Override
    public void getMockOffices(ScreenCenterAndRadius screenCenterAndRadius, OnOfficesRequest onOfficesRequest) {
        new getMockOfficesTask(onOfficesRequest).execute();
    }

    private class getMockOfficesTask extends AsyncTask<String, Void, OfficeFullResponseEntity> {

        private final OnOfficesRequest onOfficesRequest;

        public getMockOfficesTask(OnOfficesRequest onOfficesRequest) {
            this.onOfficesRequest = onOfficesRequest;
        }

        @Override
        protected OfficeFullResponseEntity doInBackground(String... params) {
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                //Empty
            }
            return ATMMock.getOfficeFullResponse(mContext);
        }

        @Override
        protected void onPostExecute(OfficeFullResponseEntity officeFullResponse) {
            super.onPostExecute(officeFullResponse);
            if (officeFullResponse != null) {
                onOfficesRequest.onResponse(officeFullResponse);
            }
        }
    }
}

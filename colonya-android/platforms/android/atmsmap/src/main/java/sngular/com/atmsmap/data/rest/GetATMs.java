package sngular.com.atmsmap.data.rest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
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
import java.util.Map;

import javax.inject.Singleton;

import sngular.com.atmsmap.R;
import sngular.com.atmsmap.app.RSIApplication;
import sngular.com.atmsmap.app.constant.AppConstants;
import sngular.com.atmsmap.data.listener.OnATMsRequest;
import sngular.com.atmsmap.data.mock.ATMMock;
import sngular.com.atmsmap.data.model.ErrorEntity;
import sngular.com.atmsmap.data.model.ResponseEntity;
import sngular.com.atmsmap.data.model.atm.ATMFullResponseEntity;
import sngular.com.atmsmap.data.model.atm.ATMFullResponseSingleEntity;
import sngular.com.atmsmap.data.util.RestUtil;
import sngular.com.atmsmap.presentation.model.map.ScreenCenterAndRadius;

/**
 * Created by alberto.hernandez on 21/04/2016.
 */
@Singleton
public class GetATMs implements GetATMsService {
    public static final String TAG = GetATMs.class.getSimpleName();
    private static final long WAIT_TIME = 1000;
    private final Context mContext;

    public GetATMs(Context ctx) {
        mContext = ctx;
    }

    @Override
    public void getAtms(ScreenCenterAndRadius screenCenterAndRadius, final OnATMsRequest onATMsRequest) {
        String tag = "getAtmsReq";
        String url = RestUtil.createGetATMURL(screenCenterAndRadius, mContext);
        Log.d(TAG, "URL Request: " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        ATMFullResponseEntity atmFullResponseEntity;
                        try {
                            JSONObject atmObject = new JSONObject(response);
                            String value = atmObject.getJSONObject(mContext.getString(R.string.atm_output)).toString();
                            Gson mGson = new Gson();
                            ResponseEntity responseEntity = mGson.fromJson(value, ResponseEntity.class);
                            if (responseEntity.getCodigoRetorno().equalsIgnoreCase(AppConstants.RESPONSE_CODE_SUCCESS)) {
                                Log.d(TAG, "Número de cajeros: " + responseEntity.getRespuesta().getNumeroRegistros());
                                if (Integer.valueOf(responseEntity.getRespuesta().getNumeroRegistros()) > 1) {
                                    atmFullResponseEntity = mGson.fromJson(value, ATMFullResponseEntity.class);
                                } else {
                                    ATMFullResponseSingleEntity atmFullResponseSingleEntity = mGson.fromJson
                                            (value, ATMFullResponseSingleEntity.class);
                                    atmFullResponseEntity = RestUtil.convertSingleATMEntity(atmFullResponseSingleEntity);
                                }
                                if (atmFullResponseEntity != null) {
                                    onATMsRequest.onResponse(atmFullResponseEntity);
                                }
                            } else if (responseEntity.getCodigoRetorno().equalsIgnoreCase(AppConstants.RESPONSE_CODE_FAIL)) {
                                onATMsRequest.onErrorResponse(responseEntity.getErrores());
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
                onATMsRequest.onErrorResponse(errorEntity);
            }
        }
        ) {
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

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return RestUtil.createGetATMHeaders(mContext);
            }
        };

        RSIApplication.getInstance().addToRequestQueue(stringRequest, tag);
    }

    //TODO Separar la lógica de Mock en flavours
    @Override
    public void getMockAtms(ScreenCenterAndRadius screenCenterAndRadius, OnATMsRequest onATMsRequest) {
        new getMockATMsTask(onATMsRequest).execute();
    }

    private class getMockATMsTask extends AsyncTask<String, Void, ATMFullResponseEntity> {

        private final OnATMsRequest onATMsRequest;

        public getMockATMsTask(OnATMsRequest onATMsRequest) {
            this.onATMsRequest = onATMsRequest;
        }

        @Override
        protected ATMFullResponseEntity doInBackground(String... params) {
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                //Empty
            }
            return ATMMock.getATMFullResponse(mContext);
        }

        @Override
        protected void onPostExecute(ATMFullResponseEntity atmFullResponseEntity) {
            super.onPostExecute(atmFullResponseEntity);
            if (atmFullResponseEntity != null) {
                onATMsRequest.onResponse(atmFullResponseEntity);
            }
        }
    }
}

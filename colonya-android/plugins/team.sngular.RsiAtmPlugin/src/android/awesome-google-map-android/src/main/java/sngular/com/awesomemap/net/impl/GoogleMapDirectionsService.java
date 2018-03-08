package sngular.com.awesomemap.net.impl;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import sngular.com.awesomemap.R;
import sngular.com.awesomemap.net.DirectionsService;
import sngular.com.awesomemap.net.dto.google.directions.GoogleDirectionRouteDTO;
import sngular.com.awesomemap.net.dto.google.directions.LegDTO;
import sngular.com.awesomemap.net.dto.google.directions.RouteDTO;
import sngular.com.awesomemap.net.dto.google.directions.StepDTO;
import sngular.com.awesomemap.net.model.LatLon;
import sngular.com.awesomemap.net.model.Route;

/**
 * Created by julio.molinera on 15/06/2015.
 */
public class GoogleMapDirectionsService implements DirectionsService {

    private static final String PARAM_ORIGIN = "origin";
    private static final String PARAM_DESTINATION = "destination";
    private static final String PARAM_MODE = "mode";
    private static final String PARAM_KEY = "key";

    Context mContext;
    OkHttpClient mClient;
    Gson mGson;
    String mGoogleMapServiceKey;


    public GoogleMapDirectionsService(Context context, String googleMapWebKey) {
        mContext = context;
        mClient = new OkHttpClient();
        mGson = new Gson();
        mGoogleMapServiceKey = googleMapWebKey;

    }

    @Override
    public Route getRoute(LatLon origin, LatLon destination, ROUTE_MODE mode) {
        List<LatLon> steps = new ArrayList<>();
        String baseURL = mContext.getString(R.string.google_directions_url);
        List<NameValuePair> params = new LinkedList<>();
        params.add(new BasicNameValuePair(PARAM_ORIGIN,
                String.format(Locale.US, "%f", origin.getLatitude()) + "," +
                        String.format(Locale.US, "%f", origin.getLongitude())));
        params.add(new BasicNameValuePair(PARAM_DESTINATION,
                String.format(Locale.US, "%f", destination.getLatitude()) + "," +
                        String.format(Locale.US, "%f", destination.getLongitude())));
        params.add(new BasicNameValuePair(PARAM_MODE, mode.name().toLowerCase()));
        params.add(new BasicNameValuePair(PARAM_KEY, mGoogleMapServiceKey));
        String url = baseURL + URLEncodedUtils.format(params, "utf-8");
        Route route = new Route();
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = mClient.newCall(request).execute();
            GoogleDirectionRouteDTO googleDirectionRoute = mGson.fromJson(new InputStreamReader(response.body().byteStream()), GoogleDirectionRouteDTO.class);
            steps.add(origin);
            List<RouteDTO> routes = googleDirectionRoute.getRoutes();
            for (RouteDTO routeDTO : routes) {
                for (LegDTO legDTO : routeDTO.getLegs()) {
                    route.setDistance(legDTO.getDistance().getValue() + route.getDistance());
                    route.setTime(route.getTime() + legDTO.getDuration().getValue());
                    for (StepDTO step : legDTO.getSteps()) {
                        List<LatLng> decodedPath = PolyUtil.decode(step.getPolyline().getPoints());
                        for (LatLng latLng : decodedPath) {
                            steps.add(new LatLon(latLng.latitude, latLng.longitude));
                        }
                    }
                }
            }
            steps.add(destination);
            route.setSteps(steps);
        } catch (IOException ex) {
            Log.e("excepcion", ex.getMessage() + "", ex);
        }
        return route;
    }
}

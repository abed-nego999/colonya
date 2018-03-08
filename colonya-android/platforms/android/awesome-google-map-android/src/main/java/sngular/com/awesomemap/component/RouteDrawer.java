package sngular.com.awesomemap.component;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import sngular.com.awesomemap.model.PoiItem;
import sngular.com.awesomemap.model.RouteConfiguration;
import sngular.com.awesomemap.net.DirectionsService;
import sngular.com.awesomemap.net.impl.GoogleMapDirectionsService;
import sngular.com.awesomemap.net.model.LatLon;
import sngular.com.awesomemap.net.model.Route;


/**
 * Created by julio.molinera on 17/06/2015.
 */
public class RouteDrawer {

    private GoogleMap mMap;
    private DirectionsService mDirectionService;
    private FragmentActivity mActivity;
    private RouteConfiguration mRouteConfiguration;
    private Polyline mCurrentRoute;
    private PoiItem mDestination;
    private PoiItem mOrigin;
    private static final int ANIMATION_SHOW_MY_LOCATION_DURATION = 200;
    final static int PADDING_LEFTRIGHT = 20;
    final static int PADDING_TOP = 50;
    final static int PADDING_BOTTOM = 220;

    public RouteDrawer(GoogleMap mMap, FragmentActivity mactivity, RouteConfiguration mRouteConfiguration, String googleMapWebKey) {
        this.mMap = mMap;
        this.mActivity = mactivity;
        if (mRouteConfiguration != null) {
            this.mRouteConfiguration = mRouteConfiguration;
        } else {
            RouteConfiguration auxConfig = new RouteConfiguration(DirectionsService.ROUTE_MODE.WALKING);
            auxConfig.setLineColor(mActivity.getResources().getColor(android.R.color.black));
            auxConfig.setLineWidth(10);
            this.mRouteConfiguration = auxConfig;
        }
        mDirectionService = new GoogleMapDirectionsService(mactivity, googleMapWebKey);
    }

    public void drawRoute(PoiItem origin, PoiItem destination) {
        mOrigin = origin;
        mDestination = destination;
        DrawRouteTask drawRouteTask = new DrawRouteTask();
        drawRouteTask.execute(origin, destination, mRouteConfiguration.getmRouteMode());
    }


    private LatLon createLatLon(LatLng position) {
        return new LatLon(position.latitude, position.longitude);
    }

    public void removeRoute() {
        if (mCurrentRoute != null) {
            mCurrentRoute.remove();
        }
    }

    private class DrawRouteTask extends AsyncTask<Object, Void, Route> {

        @Override
        protected Route doInBackground(Object... params) {
            PoiItem origin = (PoiItem) params[0];
            PoiItem destination = (PoiItem) params[1];
            DirectionsService.ROUTE_MODE routemode = (DirectionsService.ROUTE_MODE) params[2];
            return mDirectionService.getRoute(createLatLon(origin.getPosition()), createLatLon(destination.getPosition()), routemode);
        }

        @Override
        protected void onPostExecute(Route route) {
            super.onPostExecute(route);
            if (mCurrentRoute != null) {
                mCurrentRoute.remove();
            }
            PolylineOptions options = new PolylineOptions();
            options.color(mRouteConfiguration.getLineColor());
            options.width(mRouteConfiguration.getLineWidth());
            options.geodesic(true);
            for (LatLon step : route.getSteps()) {
                options.add(new LatLng(step.getLatitude(), step.getLongitude()));
            }
            mCurrentRoute = mMap.addPolyline(options);
            LatLngBounds.Builder bounds = new LatLngBounds.Builder();
            bounds.include(mOrigin.getPosition());
            bounds.include(mDestination.getPosition());

            mMap.setPadding(dpConverter(PADDING_LEFTRIGHT), dpConverter(PADDING_TOP),
                    dpConverter(PADDING_LEFTRIGHT), dpConverter(PADDING_BOTTOM));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds.build(), 0);
            mMap.animateCamera(cameraUpdate, ANIMATION_SHOW_MY_LOCATION_DURATION, null);
            mMap.setPadding(0, 0, 0, 0);
        }
    }

    public int dpConverter(int dp) {
        final float scale = mActivity.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}

package sngular.com.awesomemap;

import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import sngular.com.awesomemap.controller.MapController;
import sngular.com.awesomemap.listener.ClusterClickListener;
import sngular.com.awesomemap.listener.OnMapAwesomeListener;
import sngular.com.awesomemap.listener.PoiClickListener;
import sngular.com.awesomemap.model.PoiItem;
import sngular.com.awesomemap.model.RendererConfiguration;
import sngular.com.awesomemap.model.RouteConfiguration;


/**
 * Created by julio.molinera on 18/06/2015.
 */
public class AwesomeMap {

    private final MapController mMapController;

    public AwesomeMap(FragmentActivity activity, SupportMapFragment mapFragment,
                      OnMapAwesomeListener listener, String googleMapWebKey) {
        mMapController = new MapController(activity, mapFragment, listener, googleMapWebKey);
    }

    public Marker addPoi(PoiItem poiItem) {
        return mMapController.addPoi(poiItem);
    }

    public void removePoi(Marker marker) {
        mMapController.removePoi(marker);
    }

    public void addPoisWithCluster(List<PoiItem> poilist, RendererConfiguration rendererConfiguration,
                                   PoiClickListener poiClickListener, ClusterClickListener clusterClickListener) {
        mMapController.addPoisWithCluster(poilist, rendererConfiguration, poiClickListener, clusterClickListener);
    }

    public void removePoisWithCluster() {
        mMapController.removePoisWithCluster();
    }

    public void deselectPoiWithCluster() {
        mMapController.deselectPoiWithCluster();
    }

    public void drawRoute(LatLng origin, PoiItem destination, RouteConfiguration routeConfiguration) {
        mMapController.drawRoute(origin, destination, routeConfiguration);
    }

    public void removeRoute() {
        mMapController.removeRoute();
    }

    public void centerMapWithLatLng(LatLng latLng) {
        mMapController.centerMapWithLatLng(latLng);
    }

    public void centerMapWithLatLngRadius(LatLng latLng, Integer radius) {
        mMapController.centerMapWithLatLngRadius(latLng, radius);
    }

    public GoogleMap getMap() {
        return mMapController.getMap();
    }

    public void reloadPoiSelected(PoiItem poiItem) {
        mMapController. reloadPoiSelected(poiItem);
    }

    public void addPoiToExistList(PoiItem poiItem) {
        mMapController.addPoiToExistList(poiItem);
    }
}



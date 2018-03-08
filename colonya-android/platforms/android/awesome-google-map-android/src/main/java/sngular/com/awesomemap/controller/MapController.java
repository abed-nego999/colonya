package sngular.com.awesomemap.controller;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import sngular.com.awesomemap.component.ClusterManagerHelper;
import sngular.com.awesomemap.component.RouteDrawer;
import sngular.com.awesomemap.listener.ClusterClickListener;
import sngular.com.awesomemap.listener.MyCameraChangeListener;
import sngular.com.awesomemap.listener.OnMapAwesomeListener;
import sngular.com.awesomemap.listener.PoiClickListener;
import sngular.com.awesomemap.model.PoiItem;
import sngular.com.awesomemap.model.RendererConfiguration;
import sngular.com.awesomemap.model.RouteConfiguration;
import sngular.com.awesomemap.utils.LocationUtils;
import sngular.com.awesomemap.utils.ResourcesValidator;


/**
 * Created by julio.molinera on 16/06/2015.
 */
public class MapController implements OnMapReadyCallback, OnMapAwesomeListener, GoogleMap.OnMapClickListener {
    private static final int ANIMATION_SHOW_MY_LOCATION_DURATION = 1000;

    private final FragmentActivity mActivity;
    private final OnMapAwesomeListener mListener;
    private final String mGoogleMapWebKey;
    private GoogleMap mMap;
    private ClusterManagerHelper mClusterManagerHelper;
    private RouteDrawer mRouteDrawer;
    private MyCameraChangeListener mMultipleCameraChangeListener;


    public MapController(FragmentActivity mActivity, SupportMapFragment mapFragment,
                         OnMapAwesomeListener listener, String googleMapWebKey) {
        this.mActivity = mActivity;
        mListener = listener;
        mGoogleMapWebKey = googleMapWebKey;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mListener != null) {
            mListener.onMapReady(mMap);
        }
        mMap.setOnMapClickListener(this);
        mMultipleCameraChangeListener = new MyCameraChangeListener(this);
        mMap.setOnCameraChangeListener(mMultipleCameraChangeListener);
    }

    public Marker addPoi(PoiItem poiItem) {
        MarkerOptions markerOptions = new MarkerOptions().title(poiItem.getId())
                .position(poiItem.getPosition());
        if (ResourcesValidator.isValidDrawableResource(mActivity, poiItem.getImageResource())) {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(poiItem.getImageResource()));
        } else {
            Log.e("addPoi", "Fallo obteniendo recurso");
        }

        return mMap.addMarker(markerOptions);
    }

    public void removePoi(Marker marker) {
        if (marker != null) {
            marker.remove();
        }
    }

    public void addPoisWithCluster(List<PoiItem> poiList, RendererConfiguration rendererConfiguration, PoiClickListener poiClickListener, ClusterClickListener clusterClickListener) {

        if (mMap != null) {
            if (poiList != null) {
                if (mClusterManagerHelper == null) {
                    mClusterManagerHelper = new ClusterManagerHelper(mMap, poiList, mActivity, rendererConfiguration, poiClickListener, clusterClickListener);
                    mMultipleCameraChangeListener.setClusterManager(mClusterManagerHelper.getClusterManager());
                    mMap.setOnCameraChangeListener(mMultipleCameraChangeListener);
                } else {
                    mClusterManagerHelper.addPois(poiList);
                }
            }
        }
    }

    public void addPoiToExistList(PoiItem poiItem) {
        if (mMap != null) {
            if (mClusterManagerHelper != null) {
                mClusterManagerHelper.addPoiToExistList(poiItem);
            }
        }
    }

    public void removePoisWithCluster() {
        if (mMap != null && clusterManagerNotNull()) {
            mClusterManagerHelper.removePois();
        }
    }

    public void deselectPoiWithCluster() {
        if (mMap != null && clusterManagerNotNull()) {
            mClusterManagerHelper.deselectPoiWithCluster();
        }
    }

    public void drawRoute(LatLng origin, PoiItem destination, RouteConfiguration routeConfiguration) {
        if (mMap != null && origin != null) {
            if (mRouteDrawer == null) {
                mRouteDrawer = new RouteDrawer(mMap, mActivity, routeConfiguration, mGoogleMapWebKey);
            }
            PoiItem originPoi = new PoiItem(origin.latitude, origin.longitude);
            mRouteDrawer.drawRoute(originPoi, destination);
        }
    }

    public void removeRoute() {
        if (mRouteDrawer != null) {
            mRouteDrawer.removeRoute();
        }
    }

    public void centerMapWithLatLng(LatLng latLng) {
        if (mMap != null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            mMap.animateCamera(cameraUpdate, ANIMATION_SHOW_MY_LOCATION_DURATION, null);
        }
    }

    public void centerMapWithLatLngRadius(LatLng latLng, Integer radius) {
        if (mMap != null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(
                    LocationUtils.calculateBounds(latLng, radius), 0);
            mMap.animateCamera(cameraUpdate, ANIMATION_SHOW_MY_LOCATION_DURATION, null);
        }
    }

    public void onCameraChange(CameraPosition cameraPosition) {
        mListener.onCameraChange(cameraPosition);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mListener.onMapClick(latLng);
    }

    public GoogleMap getMap() {
        return mMap;
    }

    private boolean clusterManagerNotNull() {
        return mClusterManagerHelper != null;
    }

    public void reloadPoiSelected(PoiItem poiItem) {
        mClusterManagerHelper.onClusterItemClick(poiItem);
    }


}

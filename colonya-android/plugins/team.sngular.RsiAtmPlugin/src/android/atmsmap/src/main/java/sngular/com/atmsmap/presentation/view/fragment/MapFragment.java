package sngular.com.atmsmap.presentation.view.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import sngular.com.atmsmap.R;
import sngular.com.atmsmap.app.constant.AppConstants;
import sngular.com.atmsmap.domain.event.LocationProviderDisableEvent;
import sngular.com.atmsmap.domain.location.LocationService;
import sngular.com.atmsmap.presentation.map.MapService;
import sngular.com.atmsmap.presentation.map.PoiManager;
import sngular.com.atmsmap.presentation.model.POI;
import sngular.com.atmsmap.presentation.model.Presentation;
import sngular.com.atmsmap.presentation.model.PresentationATM;
import sngular.com.atmsmap.presentation.model.PresentationOffice;
import sngular.com.atmsmap.presentation.model.SearchResult;
import sngular.com.atmsmap.presentation.model.map.LocationAndZoom;
import sngular.com.atmsmap.presentation.view.fragment.base.BaseSupportMapFragment;
import sngular.com.atmsmap.presentation.view.listener.OnMapEventsListener;
import sngular.com.atmsmap.presentation.view.listener.OnMapPoisListener;
import sngular.com.awesomemap.AwesomeMap;
import sngular.com.awesomemap.listener.ClusterClickListener;
import sngular.com.awesomemap.listener.OnMapAwesomeListener;
import sngular.com.awesomemap.listener.PoiClickListener;
import sngular.com.awesomemap.model.PoiItem;
import sngular.com.awesomemap.model.RendererConfiguration;
import sngular.com.awesomemap.model.RouteConfiguration;
import sngular.com.awesomemap.net.DirectionsService;

/**
 * Created by alberto.hernandez on 05/04/2016.
 */
public class MapFragment extends BaseSupportMapFragment implements OnMapAwesomeListener, PoiClickListener,
        OnMapPoisListener, ClusterClickListener {
    private static final String TAG = MapFragment.class.getSimpleName();

    public enum POI_FILTER {ATM, OFFICE}

    @Inject
    LocationService mLocationManagerWrapper;
    @Inject
    MapService mMapManager;

    private OnMapEventsListener mOnMapEventsListener;
    private POI_FILTER mFilter = POI_FILTER.ATM;
    private Map<String, POI> mPois;
    private AwesomeMap mAwesomeMap;
    private GoogleMap mMap;
    private POI mCurrentPOISelected;
    private LocationAndZoom mLocationAndZoom;
    private Marker markerSearch;
    private boolean isRouteLoading;
    private PresentationOffice mLastOffice;
    private PresentationATM mLastATM;


    //region Ciclo de vida
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPois = new HashMap<>();

        mOnMapEventsListener = mMapManager.getMapEventsListener(getContext());
        mLocationAndZoom = mLocationManagerWrapper.getLocationZoom();

        SharedPreferences mPreferences = getActivity().getSharedPreferences(AppConstants.APP_PREFS, Context.MODE_PRIVATE);
        mAwesomeMap = new AwesomeMap(getActivity(), MapFragment.this, this,
                mPreferences.getString(AppConstants.MAP_WEB_KEY_PREF, ""));
    }

    //endregion

    //region Acciones sobre el mapa
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = mAwesomeMap.getMap();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (mLocationAndZoom != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom
                    (mLocationAndZoom.getLatLng(), mLocationAndZoom.getZoom()));
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        Log.d(TAG, "onCameraChange:" + isRouteLoading);
        if (isRouteLoading) {
            createDestinyPoi();
        } else {
            mMapManager.recalculateRadius(mMap);
            mMapManager.loadMarkers(mFilter, this);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mOnMapEventsListener.onMapClick();
    }

    @Override
    public void poiClicked(PoiItem poiItem) {
        POI poi = mPois.get(poiItem.getId());
        if (poi != null) {
            mCurrentPOISelected = poi;
        }
        if (mCurrentPOISelected != null) {
            if (mCurrentPOISelected.getPresentation() instanceof PresentationATM) {
                mOnMapEventsListener.onMarkerTapATM((PresentationATM) mCurrentPOISelected.getPresentation());
            } else if (mCurrentPOISelected.getPresentation() instanceof PresentationOffice) {
                mOnMapEventsListener.onMarkerTapOffice((PresentationOffice) mCurrentPOISelected.getPresentation());
            }
        }
    }

    @Override
    public void onLastClusterClick(Cluster<PoiItem> cluster) {
        List<PoiItem> items = (List<PoiItem>) cluster.getItems();
        List<Presentation> presentationList = new ArrayList<>();
        for (PoiItem item : items) {
            Presentation poi = mPois.get(item.getId()).getPresentation();
            presentationList.add(poi);
        }
        mOnMapEventsListener.onClusterList(presentationList);
    }

    @Override
    public void addPoisCluster(List<PoiItem> poisList, Map<String, POI> poisMap) {
        mPois = poisMap;
        try {
            RendererConfiguration render = new RendererConfiguration(R.style.clusterGenText,
                    R.style.iconGenText, R.style.iconGenBigText, ResourcesCompat.getDrawable(getResources(),
                    R.drawable.ic_poi_cajero, null), ResourcesCompat.getDrawable(getResources(),
                    R.drawable.ic_oficina_agrupada, null));
            mAwesomeMap.addPoisWithCluster(poisList, render, this, this);
        } catch (Exception e) {
            Log.d(TAG, "Error al dibujar los Pois:" + e.getMessage(), e);
        }
    }

    @Override
    public void addATMDistanceCommission(PresentationATM atm) {
        if (mLastATM == null) {
            mLastATM = atm;
            mOnMapEventsListener.onATMDistanceCommission(mLastATM);
        }
    }

    @Override
    public void addOfficeDistanceCommission(PresentationOffice office) {
        if (mLastOffice == null) {
            mLastOffice = office;
            mOnMapEventsListener.onOfficeDistanceCommission(mLastOffice);
        }
    }
    //endregion

    public void showRoute(boolean showRoute) {
        if (showRoute) {
            isRouteLoading = true;
            LatLng origin;
            if (markerSearch != null) {
                origin = markerSearch.getPosition();
            } else {
                origin = mLocationManagerWrapper.getLocationZoom().getLatLng();
            }
            RouteConfiguration routeConfig = new RouteConfiguration(DirectionsService.ROUTE_MODE.WALKING);
            routeConfig.setLineColor(getResources().getColor(R.color.app_green));
            routeConfig.setLineWidth(10);
            mAwesomeMap.drawRoute(origin, mCurrentPOISelected.getPoiItem(), routeConfig);
        } else {
            mAwesomeMap.removeRoute();
        }
    }

    public void centerViewInMyLocation() {
        Log.d(TAG, "centerViewInMyLocation");

        mLastATM = null;
        mLastOffice = null;

        mAwesomeMap.removePoi(markerSearch);
        markerSearch = null;

        mLocationAndZoom = mLocationManagerWrapper.getLocationZoom();
        if (mLocationAndZoom != null) {
            mAwesomeMap.centerMapWithLatLng(mLocationAndZoom.getLatLng());
        } else {
            EventBus.getDefault().post(new LocationProviderDisableEvent());
        }
    }

    public void centerViewInAddress(SearchResult searchResult) {
        Log.d(TAG, "centerViewInAddress");
        mAwesomeMap.removePoi(markerSearch);
        final LatLng position = new LatLng(searchResult.getLatitude(), searchResult.getLongitude());
        mAwesomeMap.centerMapWithLatLngRadius(position, searchResult.getRadius());
        markerSearch = mAwesomeMap.addPoi(mMapManager.addSinglePoi(position));

        mLastATM = null;
        mLastOffice = null;
    }

    public void centerViewInPoi(PoiItem poiItem) {
        Log.d(TAG, "centerViewInPoi");
        final LatLng atmPosition = new LatLng(poiItem.getPosition().latitude,
                poiItem.getPosition().longitude);
        mAwesomeMap.centerMapWithLatLng(atmPosition);
        mAwesomeMap.reloadPoiSelected(poiItem);
    }

    public void deselectPoi() {
        mAwesomeMap.deselectPoiWithCluster();
    }

    public void changeFilter(boolean isOffices) {

        if (isOffices) {
            mFilter = POI_FILTER.OFFICE;
            PresentationOffice office = mLastOffice;
            mLastOffice = null;
            addOfficeDistanceCommission(office);
        } else {
            mFilter = POI_FILTER.ATM;
            PresentationATM atm = mLastATM;
            mLastATM = null;
            addATMDistanceCommission(atm);
        }
        mAwesomeMap.removePoisWithCluster();
        mMapManager.loadMarkers(mFilter, this);
    }

    public void changeCommissionsFilter(boolean noCommissions) {
        mAwesomeMap.removePoisWithCluster();
        mMapManager.loadFreeATMs(noCommissions);
    }

    private void createDestinyPoi() {
        mAwesomeMap.addPoiToExistList(mCurrentPOISelected.getPoiItem());
        isRouteLoading = false;
    }

    public void onOfficeSelected(PresentationOffice office) {
        PoiItem poiItem = PoiManager.createOfficePoi(office);
        mCurrentPOISelected = new POI(office, poiItem);
        mOnMapEventsListener.onMarkerTapOffice(office);
        mAwesomeMap.reloadPoiSelected(poiItem);
    }

    public void onATMSelected(PresentationATM atm) {
        PoiItem poiItem = PoiManager.createATMPoi(atm);
        mCurrentPOISelected = new POI(atm, poiItem);
        mOnMapEventsListener.onMarkerTapATM(atm);
        mAwesomeMap.reloadPoiSelected(poiItem);
    }
}

package sngular.com.atmsmap.presentation.map;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import sngular.com.atmsmap.app.constant.ATM_Type;
import sngular.com.atmsmap.app.constant.AppConstants;
import sngular.com.atmsmap.domain.listener.OnUserCaseResponse;
import sngular.com.atmsmap.domain.model.ATMDTO;
import sngular.com.atmsmap.domain.model.ErrorDTO;
import sngular.com.atmsmap.domain.model.OfficeDTO;
import sngular.com.atmsmap.domain.usercase.UserCaseService;
import sngular.com.atmsmap.presentation.model.POI;
import sngular.com.atmsmap.presentation.model.Presentation;
import sngular.com.atmsmap.presentation.model.PresentationATM;
import sngular.com.atmsmap.presentation.model.PresentationOffice;
import sngular.com.atmsmap.presentation.model.map.ScreenCenterAndRadius;
import sngular.com.atmsmap.presentation.model.mapper.PresentationATMMapper;
import sngular.com.atmsmap.presentation.model.mapper.PresentationOfficeMapper;
import sngular.com.atmsmap.presentation.util.Network;
import sngular.com.atmsmap.presentation.view.fragment.MapFragment;
import sngular.com.atmsmap.presentation.view.listener.OnMapEventsListener;
import sngular.com.atmsmap.presentation.view.listener.OnMapPoisListener;
import sngular.com.awesomemap.model.PoiItem;
import sngular.com.awesomemap.utils.LocationUtils;

/**
 * Created by alberto.hernandez on 18/04/2016.
 */

@Singleton
public class MapManager implements MapService, OnUserCaseResponse {
    private static final String TAG = MapManager.class.getSimpleName();

    private UserCaseService mUserCase;
    private final Context mContext;
    private LatLng mLastLocationRequest;
    private ScreenCenterAndRadius screenCenterAndRadius;
    private int mLastRadius;
    private MapFragment.POI_FILTER mLastfilter;
    private OnMapPoisListener mMapListener;
    private List<PresentationATM> mPresentationATMs;
    private List<PresentationOffice> mPresentationOffices;
    private OnMapEventsListener mOnMapEventsListener;
    private boolean mNoCommissions;

    public MapManager(Context context, UserCaseService userCase) {
        mContext = context;
        mUserCase = userCase;
    }

    @Override
    public OnMapEventsListener getMapEventsListener(Context context) {
        if (context instanceof OnMapEventsListener) {
            mOnMapEventsListener = (OnMapEventsListener) context;
        } else {
            mOnMapEventsListener = new OnMapEventsListener() {
                @Override
                public void onError(Throwable ex) {
                    //Empty Constructor
                }

                @Override
                public void onErrorStrings(String title, String subtitle) {
                    //Empty Constructor
                }

                @Override
                public void showHelp() {
                    //Empty Constructor
                }

                @Override
                public void onATMDistanceCommission(PresentationATM lastATM) {
                    //Empty Constructor
                }

                @Override
                public void onOfficeDistanceCommission(PresentationOffice lastOffice) {
                    //Empty Constructor
                }

                @Override
                public void onClusterList(List<Presentation> clusterList) {

                }

                @Override
                public void onMarkerTapATM(PresentationATM atm) {
                    //Empty Constructor
                }

                @Override
                public void onMarkerTapOffice(PresentationOffice office) {
                    //Empty Constructor
                }

                @Override
                public void onMapClick() {
                    //Empty Constructor
                }
            };
        }
        return mOnMapEventsListener;
    }

    @Override
    public ScreenCenterAndRadius recalculateRadius(GoogleMap mMap) {
        screenCenterAndRadius = new ScreenCenterAndRadius();
        if (mMap != null && mMap.getProjection().getVisibleRegion() != null) {
            VisibleRegion visibleRegion = mMap.getProjection().getVisibleRegion();

            Float longitude = LocationUtils.calculateValidLongitude(visibleRegion.latLngBounds.getCenter().longitude);
            Float latitude = LocationUtils.calculateValidLatitude(visibleRegion.latLngBounds.getCenter().latitude);

            screenCenterAndRadius.setCnterScreenLatitude(latitude);
            screenCenterAndRadius.setCenterScreenLongitude(longitude);
            LatLng center = new LatLng(latitude, longitude);
            LatLng northeast = visibleRegion.latLngBounds.northeast;
            screenCenterAndRadius.setRadius((int) LocationUtils.calculateRadius(center, northeast));

            Log.d(TAG, "Recalculated Radius: Center Latitude: " + screenCenterAndRadius.getCenterScreenLatitude() +
                    " Center Longitud: " + screenCenterAndRadius.getCenterScreenLongitude() +
                    " Radius: " + screenCenterAndRadius.getRadius());
        }
        return screenCenterAndRadius;
    }

    @Override
    public void loadMarkers(MapFragment.POI_FILTER filter, OnMapPoisListener listener) {
        mMapListener = listener;

        if (!Network.checkConnection(mContext, mOnMapEventsListener)) {
            return;
        }

        if (!isNeedMakeRequest() && mLastfilter.equals(filter)) {
            return;
        }
        mLastfilter = filter;
        Log.d(TAG, "Make Load Markers Request");

        switch (filter) {
            case ATM:
                mUserCase.getATMs(screenCenterAndRadius, this);
                break;
            case OFFICE:
                mUserCase.getOffices(screenCenterAndRadius, this);
                break;
            default:
                break;
        }
    }

    @Override
    public void loadFreeATMs(boolean noCommissions) {
        mNoCommissions = noCommissions;
        if (mPresentationATMs != null) {
            createATMListPois();
        }
    }

    @Override
    public void onATMListResponse(List<ATMDTO> atmDtoList) {
        SharedPreferences mPreferences = mContext.getSharedPreferences
                (AppConstants.APP_PREFS, Context.MODE_PRIVATE);
        String entityCode = mPreferences.getString(AppConstants.ENTITY_PREF, "");

        PresentationATMMapper mapper = new PresentationATMMapper();
        mPresentationATMs = mapper.modelToData(atmDtoList, entityCode, null);

        if (!mPresentationATMs.isEmpty()) {
            for (PresentationATM atm : mPresentationATMs) {
                if (atm.getType() == ATM_Type.ATM_SAME_ENTITY || atm.getType() == ATM_Type.ATM_SAME_GROUP_WITHOUT_COMMISSION ||
                        atm.getType() == ATM_Type.ATM_DIFFERENT_GROUP_WITHOUT_COMMISSION) {
                    mMapListener.addATMDistanceCommission(atm);
                    break;
                }
            }
        }
        createATMListPois();
    }

    @Override
    public void onOfficeListResponse(List<OfficeDTO> officeDtoList) {
        PresentationOfficeMapper mapper = new PresentationOfficeMapper();
        mPresentationOffices = mapper.modelToData(officeDtoList, null, screenCenterAndRadius);

        if (!mPresentationOffices.isEmpty()) {
            PresentationOffice office = mPresentationOffices.get(0);
            mMapListener.addOfficeDistanceCommission(office);
        }
        createOfficeListPois();
    }

    private void createATMListPois() {
        List<PoiItem> list = new ArrayList<>();
        Map<String, POI> mapPois = new HashMap<>();
        for (int i = 0; i < mPresentationATMs.size(); i++) {
            PresentationATM atm = mPresentationATMs.get(i);
            if (mNoCommissions) {
                if (atm.getType() == ATM_Type.ATM_SAME_ENTITY || atm.getType() == ATM_Type.ATM_SAME_GROUP_WITHOUT_COMMISSION ||
                        atm.getType() == ATM_Type.ATM_DIFFERENT_GROUP_WITHOUT_COMMISSION) {
                    PoiItem poiItem = PoiManager.createATMPoi(atm);
                    mapPois.put(poiItem.getId(), new POI(atm, poiItem));
                    list.add(poiItem);
                }
            } else {
                PoiItem poiItem = PoiManager.createATMPoi(atm);
                mapPois.put(poiItem.getId(), new POI(atm, poiItem));
                list.add(poiItem);
            }
        }
        mMapListener.addPoisCluster(list, mapPois);
    }

    private void createOfficeListPois() {
        List<PoiItem> list = new ArrayList<>();
        Map<String, POI> mapPois = new HashMap<>();
        for (int i = 0; i < mPresentationOffices.size(); i++) {
            PresentationOffice office = mPresentationOffices.get(i);
            PoiItem poiItem = PoiManager.createOfficePoi(office);
            mapPois.put(poiItem.getId(), new POI(office, poiItem));
            list.add(poiItem);
        }
        mMapListener.addPoisCluster(list, mapPois);
    }

    @Override
    public void onResponseError(ErrorDTO errorDTO) {
        mOnMapEventsListener.onErrorStrings(errorDTO.getMensajeMostrar(), errorDTO.getSolucion());
    }

    @Override
    public PoiItem addSinglePoi(LatLng position) {
        return PoiManager.createSearchPoi(position);
    }

    @Override
    public List<PresentationATM> getPresentationAtms() {
        return mPresentationATMs;
    }

    @Override
    public List<PresentationOffice> getPresentationOffices() {
        return mPresentationOffices;
    }

    @Override
    public boolean isNoCommissions() {
        return mNoCommissions;
    }

    private boolean isNeedMakeRequest() {
        //Si la distancia entre la loc anterior y la actual es mayor que la mitad del radio actual
        if (mLastLocationRequest != null && screenCenterAndRadius.getLatLng() != null) {
            float distance = LocationUtils.getDistance(mLastLocationRequest, screenCenterAndRadius.getLatLng());
            float radius50per = (screenCenterAndRadius.getRadius() / 100) * 50;
            if (distance >= radius50per) {
                mLastLocationRequest = null;
            }
        }
        if (mLastLocationRequest == null) {
            mLastLocationRequest = new LatLng(screenCenterAndRadius.getCenterScreenLatitude(),
                    screenCenterAndRadius.getCenterScreenLongitude());
            mLastRadius = screenCenterAndRadius.getRadius();
            Log.d(TAG, "Location Null: Center Latitude: " + screenCenterAndRadius.getCenterScreenLatitude() +
                    " Center Longitud: " + screenCenterAndRadius.getCenterScreenLongitude() +
                    " Radius: " + screenCenterAndRadius.getRadius());
            return true;
        } else {
            float radiusDiff = Math.abs(mLastRadius - screenCenterAndRadius.getRadius());
            float radius25per = (mLastRadius / 100) * 25;
            Log.d(TAG, "Location Correcto: radiusDiff: " + radiusDiff + " radius25per: " + radius25per);
            if (radiusDiff <= radius25per) {
                return false;
            } else {
                mLastLocationRequest = new LatLng(screenCenterAndRadius.getCenterScreenLatitude(),
                        screenCenterAndRadius.getCenterScreenLongitude());
                mLastRadius = screenCenterAndRadius.getRadius();
                return true;
            }
        }
    }
}

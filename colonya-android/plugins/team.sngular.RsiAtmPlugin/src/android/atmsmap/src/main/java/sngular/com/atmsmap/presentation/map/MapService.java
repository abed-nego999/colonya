package sngular.com.atmsmap.presentation.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import sngular.com.atmsmap.presentation.model.PresentationATM;
import sngular.com.atmsmap.presentation.model.PresentationOffice;
import sngular.com.atmsmap.presentation.model.map.ScreenCenterAndRadius;
import sngular.com.atmsmap.presentation.view.fragment.MapFragment;
import sngular.com.atmsmap.presentation.view.listener.OnMapEventsListener;
import sngular.com.atmsmap.presentation.view.listener.OnMapPoisListener;
import sngular.com.awesomemap.model.PoiItem;

/**
 * Created by alberto.hernandez on 18/04/2016.
 */
public interface MapService {
    OnMapEventsListener getMapEventsListener(Context context);

    ScreenCenterAndRadius recalculateRadius(GoogleMap mMap);

    void loadMarkers(MapFragment.POI_FILTER filter, OnMapPoisListener listener);

    List<PresentationATM> getPresentationAtms();

    List<PresentationOffice> getPresentationOffices();

    PoiItem addSinglePoi(LatLng position);

    void loadFreeATMs(boolean noCommissions);

    boolean isNoCommissions();
}

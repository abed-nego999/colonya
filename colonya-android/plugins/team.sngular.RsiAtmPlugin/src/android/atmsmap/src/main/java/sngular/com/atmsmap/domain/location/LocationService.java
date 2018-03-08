package sngular.com.atmsmap.domain.location;

import android.location.Location;

import sngular.com.atmsmap.presentation.model.map.LocationAndZoom;

public interface LocationService {
    void startRequest();

    Location getLastLocation();

    Location getIsActive();

    LocationAndZoom getLocationZoom();

}

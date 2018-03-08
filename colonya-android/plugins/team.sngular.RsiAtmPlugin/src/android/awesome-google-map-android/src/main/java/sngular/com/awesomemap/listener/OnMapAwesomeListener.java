package sngular.com.awesomemap.listener;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by alberto.hernandez on 07/04/2016.
 */
public interface OnMapAwesomeListener {
    void onMapReady(GoogleMap mMap);

    void onCameraChange(CameraPosition cameraPosition);

    void onMapClick(LatLng latLng);
}

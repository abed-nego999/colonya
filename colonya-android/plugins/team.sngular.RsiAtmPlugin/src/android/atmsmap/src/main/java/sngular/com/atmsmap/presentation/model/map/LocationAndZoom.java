package sngular.com.atmsmap.presentation.model.map;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by alberto.hernandez on 18/04/2016.
 */
public class LocationAndZoom {
    private float latitude, longitude;
    private float zoom;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }
}

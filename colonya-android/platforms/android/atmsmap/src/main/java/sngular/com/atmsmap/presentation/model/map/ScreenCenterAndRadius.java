package sngular.com.atmsmap.presentation.model.map;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by alberto.hernandez on 18/04/2016.
 */
public class ScreenCenterAndRadius {
    private float centerScreenLatitude, centerScreenLongitude;
    private Integer radius;

    public float getCenterScreenLatitude() {
        return centerScreenLatitude;
    }

    public void setCnterScreenLatitude(float centerScreenLatitude) {
        this.centerScreenLatitude = centerScreenLatitude;
    }

    public float getCenterScreenLongitude() {
        return centerScreenLongitude;
    }

    public void setCenterScreenLongitude(float centerScreenLongitude) {
        this.centerScreenLongitude = centerScreenLongitude;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public LatLng getLatLng() {
        return new LatLng(centerScreenLatitude, centerScreenLongitude);
    }
}

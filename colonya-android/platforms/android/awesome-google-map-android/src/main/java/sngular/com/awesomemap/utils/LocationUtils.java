package sngular.com.awesomemap.utils;

import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

/**
 * Created by Fabio on 31/3/15.
 * fabio.santana@medianet.es
 */
public class LocationUtils {

    public static float calculateRadius(LatLng center, LatLng corner) {
        float[] result = new float[1];
        Location.distanceBetween(center.latitude, center.longitude, corner.latitude, corner.longitude, result);
        return result[0];
    }

    public static LatLngBounds calculateBounds(LatLng center, Integer radius) {
        LatLng southwest = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 225);
        LatLng northeast = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 45);
        return new LatLngBounds(southwest, northeast);
    }

    public static float getDistance(LatLng origin, LatLng destiny) {
        Location originLocation = new Location(LocationManager.GPS_PROVIDER);
        originLocation.setLatitude(origin.latitude);
        originLocation.setLongitude(origin.longitude);
        Location destinyLocation = new Location(LocationManager.GPS_PROVIDER);
        destinyLocation.setLatitude(destiny.latitude);
        destinyLocation.setLongitude(destiny.longitude);

        return originLocation.distanceTo(destinyLocation);
    }

    public static String getDistanceToShow(String distance) {
        if (!distance.contains(".")) {
            distance = distance + " km";
        } else {
            distance = distance + "00";
            Float intDistance = Float.valueOf(distance);
            if (intDistance >= 1) {
                String mKmDistance = distance.substring(0, distance.indexOf("."));
                String mMDistance = distance.substring(distance.indexOf("."), distance.indexOf(".") + 3);
                distance = mKmDistance + mMDistance + " km";
            } else {
                distance = String.valueOf(distance).substring(distance.indexOf(".") + 1, distance.indexOf(".") + 1 + 3) + " m";
            }
        }
        return distance;
    }

    public static Float calculateValidLongitude(double longitude) {
        String longitudeString = String.valueOf(longitude);
        if (longitudeString.contains("E")) {
            longitudeString = longitudeString.substring(0, longitudeString.indexOf("E") - 1);
        }
        String decimal = longitudeString.substring(longitudeString.indexOf("."));
        while (decimal.length() > 7) {
            longitudeString = longitudeString.substring(0, longitudeString.length() - 1);
            decimal = longitudeString.substring(longitudeString.indexOf("."));
        }
        return Float.valueOf(longitudeString);
    }

    public static Float calculateValidLatitude(double latitude) {
        String latitudeString = String.valueOf(latitude);
        if (latitudeString.contains("E")) {
            latitudeString = latitudeString.substring(0, latitudeString.indexOf("E") - 1);
        }
        String decimal = latitudeString.substring(latitudeString.indexOf("."));
        while (decimal.length() > 7) {
            latitudeString = latitudeString.substring(0, latitudeString.length() - 1);
            decimal = latitudeString.substring(latitudeString.indexOf("."));
        }
        return Float.valueOf(latitudeString);
    }
}

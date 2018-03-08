package sngular.com.atmsmap.domain.location;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import de.greenrobot.event.EventBus;
import sngular.com.atmsmap.domain.event.LocationProviderDisableEvent;
import sngular.com.atmsmap.domain.event.LocationProviderEnableEvent;

public class LocationListenerAndroid implements LocationListener {
    private static final String TAG = LocationListenerAndroid.class.getSimpleName();

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

        if (status == LocationProvider.AVAILABLE) {
            Log.d(TAG, "Status activado");
            EventBus.getDefault().post(new LocationProviderEnableEvent());
        } else {
            Log.d(TAG, "Status desactivado");
            EventBus.getDefault().post(new LocationProviderDisableEvent());
        }
        Log.d(TAG, status + ": " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        EventBus.getDefault().post(new LocationProviderEnableEvent());
        Log.d(TAG, "Posicion activada");
    }

    @Override
    public void onProviderDisabled(String provider) {
        EventBus.getDefault().post(new LocationProviderDisableEvent());
        Log.d(TAG, "Posicion desactivada");
    }
}

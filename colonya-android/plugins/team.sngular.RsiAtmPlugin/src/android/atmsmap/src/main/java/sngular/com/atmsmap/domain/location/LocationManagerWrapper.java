package sngular.com.atmsmap.domain.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import javax.inject.Singleton;

import de.greenrobot.event.EventBus;
import sngular.com.atmsmap.R;
import sngular.com.atmsmap.domain.event.LocationProviderEnableEvent;
import sngular.com.atmsmap.domain.event.NetworkEnableEvent;
import sngular.com.atmsmap.presentation.model.map.LocationAndZoom;


@Singleton
public class LocationManagerWrapper implements LocationService, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final long UPDATE_TIME = 20 * 60 * 1000;

    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationRequest mLocationRequest;
    private long mLastRequest;


    public LocationManagerWrapper(Context context) {
        mContext = context;
        mLastRequest = 0;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void startRequest() {
        long diff = System.currentTimeMillis() - mLastRequest;

        if (mGoogleApiClient == null || (!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting() && diff > UPDATE_TIME)) {
            mLastRequest = System.currentTimeMillis();
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mLastRequest = 0;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            if (mLocation == null) {
                EventBus.getDefault().post(new LocationProviderEnableEvent());
            }
            mLocation = location;
            if (mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                mGoogleApiClient.disconnect();
            }
        }
    }

    @Override
    public Location getLastLocation() {
        if (mLocation == null && mGoogleApiClient != null) {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        return mLocation;
    }

    public Location getIsActive() {
        return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public LocationAndZoom getLocationZoom() {
        Location location = getLastLocation();

        if (location != null) {
            LocationAndZoom locationAndZoom = new LocationAndZoom();
            locationAndZoom.setLatitude((float) location.getLatitude());
            locationAndZoom.setLongitude((float) location.getLongitude());
            locationAndZoom.setZoom(Integer.parseInt(mContext.getString(R.string.default_zoom)));

            EventBus.getDefault().post(new NetworkEnableEvent());
            return locationAndZoom;
        } else {
            return null;
        }

    }
}

package sngular.com.awesomemap.component;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import sngular.com.awesomemap.adapter.PlaceAutocompleteAdapter;
import sngular.com.awesomemap.listener.PlaceClickListener;


/**
 * Created by julio.molinera on 23/06/2015.
 */
public class AddressSearcher implements GoogleApiClient.OnConnectionFailedListener {

    private FragmentActivity mActivity;
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private PlaceClickListener mPlaceClickListener;

    private static final String TAG = "AddressSearcher";

    public AddressSearcher(FragmentActivity mActivity, AutoCompleteTextView autocompleteView,
                           int listResource, PlaceClickListener placeClickListener) {
        this.mActivity = mActivity;
        if (placeClickListener != null) {
            this.mPlaceClickListener = placeClickListener;
        } else {
            this.mPlaceClickListener = new PlaceClickListener() {
                @Override
                public void placeClick(PlaceBuffer place) {

                }
            };
        }
        this.mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .enableAutoManage(mActivity, this)
                .addApi(Places.GEO_DATA_API)
                .build();
        this.mAdapter = new PlaceAutocompleteAdapter(mActivity, listResource, mGoogleApiClient, null);
        autocompleteView.setAdapter(mAdapter);
        autocompleteView.setOnItemClickListener(mAutocompleteClickListener);
    }

    public AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a PlaceAutocomplete object from which we
             read the place ID.
              */
            final PlaceAutocompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Autocomplete item selected: " + item.description);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
              details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Log.i(TAG, "Called getPlaceById to get Place details for " + item.placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            mPlaceClickListener.placeClick(places);
        }
    };

    /**
     * Called when the Activity could not connect to Google Play services and the auto manager
     * could resolve the error automatically.
     * In this case the API is not available and notify the user.
     *
     * @param connectionResult can be inspected to determine the cause of the failure
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
        Toast.makeText(mActivity,
                "No se puede conectar al API de Google: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

}

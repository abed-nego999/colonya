package sngular.com.atmsmap.presentation.view.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import de.greenrobot.event.EventBus;
import sngular.com.atmsmap.app.RSIApplication;
import sngular.com.atmsmap.app.module.ActivityModule;
import sngular.com.atmsmap.domain.event.NetworkEnableEvent;
import sngular.com.atmsmap.domain.location.LocationService;

/**
 * Created by alberto.hernandez on 04/04/2016.
 */
public class BaseActivity extends AppCompatActivity {

    public static final int GPS_ERRORDIALOG_REQUEST = 9001;

    @Inject
    LocationService mLocationService;

    private ObjectGraph activityGraph;
    private IntentFilter mIntentFilter;
    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager conn = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conn.getActiveNetworkInfo();
            if (networkInfo != null && stateIsConnected(networkInfo)) {
                EventBus.getDefault().post(new NetworkEnableEvent());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the activity graph by .plus-ing our modules onto the application graph.
        RSIApplication application = (RSIApplication) getApplication();
        activityGraph = application.getApplicationGraph().plus(getModules().toArray());

        // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
        activityGraph.inject(this);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mIntentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");


        int isGoogleServicesAvailable = GoogleApiAvailability.getInstance().
                isGooglePlayServicesAvailable(getApplicationContext());
        if (isGoogleServicesAvailable != ConnectionResult.SUCCESS) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog
                    (this, isGoogleServicesAvailable, GPS_ERRORDIALOG_REQUEST);
            dialog.show();
        } else {
            mLocationService.startRequest();
        }

    }

    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected as
        // soon as possible.
        activityGraph = null;

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(mIntentReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mIntentReceiver);
    }

    private boolean stateIsConnected(NetworkInfo networkInfo) {
        return networkInfo.getState() == NetworkInfo.State.CONNECTED;
    }

    /**
     * A list of modules to use for the individual activity graph. Subclasses can override this
     * method to provide additional modules provided they call and include the modules returned by
     * calling {@code super.getModules()}.
     */
    protected List<ActivityModule> getModules() {
        return Arrays.asList(new ActivityModule(this));
    }

    /**
     * Inject the supplied {@code object} using the activity-specific graph.
     */
    public void inject(Object object) {
        activityGraph.inject(object);
    }
}

package sngular.com.atmsmap.presentation.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import sngular.com.atmsmap.presentation.exception.NotNetworkException;
import sngular.com.atmsmap.presentation.view.listener.OnMapEventsListener;

/**
 * Created by alberto.hernandez on 27/04/2016.
 */
public class Network {
    private Network() {
        //Empty Constructor
    }

    public static boolean checkConnection(Context context, OnMapEventsListener onMapEventsListener) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            onMapEventsListener.onError(new NotNetworkException());
            return false;
        }
        return true;
    }
}

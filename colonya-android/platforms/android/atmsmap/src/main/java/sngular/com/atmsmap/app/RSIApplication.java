package sngular.com.atmsmap.app;

import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import sngular.com.atmsmap.app.module.AndroidModule;

public class RSIApplication extends MultiDexApplication {
    public static final String TAG = RSIApplication.class.getSimpleName();

    private ObjectGraph applicationGraph;
    private RequestQueue mRequestQueue;
    private static RSIApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationGraph = ObjectGraph.create(getModules().toArray());
        setInstance(this);
    }

    /**
     * A list of modules to use for the application graph. Subclasses can override this method to
     * provide additional modules provided they call {@code super.getModules()}.
     */
    protected List<AndroidModule> getModules() {
        return Arrays.asList(new AndroidModule(this));
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }

    /**
     * Volley
     */
    public static synchronized RSIApplication getInstance() {
        return mInstance;
    }

    public static void setInstance(RSIApplication mInstance) {
        RSIApplication.mInstance = mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
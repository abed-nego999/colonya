package sngular.com.awesomemap.listener;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.maps.android.clustering.ClusterManager;

import sngular.com.awesomemap.controller.MapController;
import sngular.com.awesomemap.model.PoiItem;


/**
 * Created by julio.molinera on 29/06/2015.
 */

/**
 * Clase encargada de "duplicar el onCameraChangeListener para que se pueda usa tanto en el clusterManager como en el MapController"
 */
public class MyCameraChangeListener implements GoogleMap.OnCameraChangeListener {
    private ClusterManager<PoiItem> mClusterManager;
    private MapController mMapController;

    public MyCameraChangeListener(MapController mMapController) {
        this.mMapController = mMapController;
    }

    public void setClusterManager(ClusterManager<PoiItem> mClusterManager) {
        this.mClusterManager = mClusterManager;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

        if (mClusterManager != null) {
            mClusterManager.onCameraChange(cameraPosition);
        }
        mMapController.onCameraChange(cameraPosition);
    }
}

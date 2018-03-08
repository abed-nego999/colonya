package sngular.com.awesomemap.component;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.MarkerManager;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sngular.com.awesomemap.listener.ClusterClickListener;
import sngular.com.awesomemap.listener.PoiClickListener;
import sngular.com.awesomemap.model.PoiItem;
import sngular.com.awesomemap.model.RendererConfiguration;
import sngular.com.awesomemap.utils.PoiItemRenderer;
import sngular.com.awesomemap.utils.ResourcesValidator;


/**
 * Created by julio.molinera on 12/06/2015.
 */

public class ClusterManagerHelper implements ClusterManager.OnClusterClickListener<PoiItem>,
        ClusterManager.OnClusterItemClickListener<PoiItem> {

    private final GoogleMap mMap;
    private ClusterManager mClusterManager;
    private final List<PoiItem> mPoiList;
    private PoiItem mLastPoiSelected;
    private final FragmentActivity mActivity;
    private final RendererConfiguration mRendererConfiguration;
    private PoiClickListener mPoiClickListener;
    private ClusterClickListener mClusterClickListener;

    final static int PADDING_LEFT = 12;
    final static int PADDING_TOP = 8;

    public ClusterManagerHelper(GoogleMap mMap, List<PoiItem> mPoiList, FragmentActivity activity,
                                RendererConfiguration mRendererConfiguration, PoiClickListener mPoiClickListener, ClusterClickListener mClusterClickListener) {
        this.mMap = mMap;
        this.mPoiList = mPoiList;
        this.mActivity = activity;
        this.mRendererConfiguration = mRendererConfiguration;


        if (mClusterClickListener != null) {
            this.mClusterClickListener = mClusterClickListener;
        } else {
            this.mClusterClickListener = new ClusterClickListener() {
                @Override
                public void onLastClusterClick(Cluster<PoiItem> cluster) {

                }
            };
        }
        if (mPoiClickListener != null) {
            this.mPoiClickListener = mPoiClickListener;
        } else {
            this.mPoiClickListener = new PoiClickListener() {
                @Override
                public void poiClicked(PoiItem poiItem) {
                }
            };
        }
        initCluster();
    }

    @Override
    public boolean onClusterClick(Cluster<PoiItem> cluster) {
        float zoom = mMap.getCameraPosition().zoom;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cluster.getPosition(),
                (float) Math.floor(zoom + 1)), 300, null);
        if (zoom >= 21.0) {
            mClusterClickListener.onLastClusterClick(cluster);
        }
        return true;
    }


    @Override
    public boolean onClusterItemClick(PoiItem item) {
        if (!item.isSelected()) {
            mPoiClickListener.poiClicked(item);
            if (mLastPoiSelected != null) {
                mLastPoiSelected.setIsSelected(false);
                item.setIsSelected(true);
                reloadMarker(mLastPoiSelected);
                mLastPoiSelected = item;
                reloadMarker(item);
            } else {
                item.setIsSelected(true);
                mLastPoiSelected = item;
                reloadMarker(item);
            }
        }
        return true;
    }


    /**
     * Metodo encargado de cambiar la imagen del marker si estos son seleccionables
     *
     * @param item
     */
    private void reloadMarker(PoiItem item) {
        MarkerManager.Collection markerCollection = mClusterManager.getMarkerCollection();
        Collection<Marker> markers = markerCollection.getMarkers();
        String strId = item.getId();
        for (Marker m : markers) {
            if (strId.equals(m.getTitle())) {
                if (item.isSelected()) {
                    if (ResourcesValidator.isValidDrawableResource(mActivity, item.getOverResource())) {
                        if (Float.floatToRawIntBits(item.getCommission()) == 0) {
                            m.setIcon(BitmapDescriptorFactory.fromResource(item.getOverResource()));
                        } else {
                            m.setIcon(getIconBigWithText(item));
                        }
                    } else {
                        Log.e("reloadMarker", "Fallo obteniendo recurso");
                    }
                } else {
                    if (ResourcesValidator.isValidDrawableResource(mActivity, item.getImageResource())) {
                        if (Float.floatToRawIntBits(item.getCommission()) == 0) {
                            m.setIcon(BitmapDescriptorFactory.fromResource(item.getImageResource()));
                        } else {
                            m.setIcon(getIconWithText(item));
                        }
                    } else {
                        Log.e("reloadMarker", "Fallo obteniendo recurso");
                    }
                }
                m.setTitle(item.getId());
                break;
            }
        }
    }

    private BitmapDescriptor getIconWithText(PoiItem item) {
        IconGenerator mClusterIconGenerator = new IconGenerator(mActivity);
        mClusterIconGenerator.setBackground(mActivity.getResources().getDrawable(item.getImageResource()));
        String comision;
        if ("S".equalsIgnoreCase(item.getIndicadorComision())) {
            mClusterIconGenerator.setContentPadding(dpConverter(PADDING_LEFT) + dpConverter(3),
                    dpConverter(PADDING_TOP) + dpConverter(7), 0, 0);
            comision = String.valueOf(item.getCommission() + "€");
        } else {
            mClusterIconGenerator.setContentPadding(dpConverter(PADDING_LEFT),
                    dpConverter(PADDING_TOP), 0, 0);
            comision = String.valueOf("    <\n" + item.getCommission() + "€");
        }

        mClusterIconGenerator.setTextAppearance(mActivity, mRendererConfiguration.getPoiStyle());
        Bitmap icon = mClusterIconGenerator.makeIcon(comision);
        return BitmapDescriptorFactory.fromBitmap(icon);
    }

    private BitmapDescriptor getIconBigWithText(PoiItem item) {
        IconGenerator mClusterIconGenerator = new IconGenerator(mActivity);
        mClusterIconGenerator.setBackground(mActivity.getResources().getDrawable(item.getOverResource()));
        String comision;
        if ("S".equalsIgnoreCase(item.getIndicadorComision())) {
            mClusterIconGenerator.setContentPadding(dpConverter(PADDING_LEFT) + dpConverter(8),
                    dpConverter(PADDING_TOP) + dpConverter(14), 0, 0);
            comision = String.valueOf(item.getCommission() + "€");
        } else {
            mClusterIconGenerator.setContentPadding(dpConverter(PADDING_LEFT) + dpConverter(5),
                    dpConverter(PADDING_TOP) + dpConverter(7), 0, 0);
            comision = String.valueOf("    <\n" + item.getCommission() + "€");
        }

        mClusterIconGenerator.setTextAppearance(mActivity, mRendererConfiguration.getPoiBigStyle());
        Bitmap icon = mClusterIconGenerator.makeIcon(comision);
        return BitmapDescriptorFactory.fromBitmap(icon);
    }

    /**
     * Metodo encargado de inicializar el cluster manager y de agregar los es.awesomegooglemap.listeners del cluster al mapa
     */

    private void initCluster() {

        mClusterManager = new ClusterManager<>(mActivity, mMap);
        mClusterManager.setRenderer(new PoiItemRenderer(mActivity, mMap, mClusterManager, mRendererConfiguration));
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.addItems(mPoiList);
        mMap.stopAnimation();
    }

    public void addPois(List<PoiItem> poilist) {
        for (PoiItem poiItem : poilist) {
            if (!mPoiList.contains(poiItem)) {
                mPoiList.add(poiItem);
                mClusterManager.addItem(poiItem);
            }
        }
        List<PoiItem> newPoiList = new ArrayList<>();
        for (PoiItem poiItemList : mPoiList) {
            if (!poilist.contains(poiItemList)) {
                mClusterManager.removeItem(poiItemList);
                newPoiList.add(poiItemList);
            }
        }
        for (PoiItem newPoi : newPoiList) {
            mPoiList.remove(newPoi);
        }
        mClusterManager.cluster();
    }

    public void addPoiToExistList(PoiItem poiItem) {
        if (!mPoiList.contains(poiItem)) {
            mPoiList.add(poiItem);
            mClusterManager.addItem(poiItem);
        }
        mClusterManager.cluster();
    }

    public void removePois() {
        mPoiList.clear();
        mClusterManager.clearItems();
        mClusterManager.cluster();
    }

    public void deselectPoiWithCluster() {
        if (mLastPoiSelected != null) {
            mLastPoiSelected.setIsSelected(false);
            reloadMarker(mLastPoiSelected);
            mLastPoiSelected = null;
        }
    }

    public ClusterManager getClusterManager() {
        return mClusterManager;
    }

    public int dpConverter(int dp) {
        final float scale = mActivity.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


}

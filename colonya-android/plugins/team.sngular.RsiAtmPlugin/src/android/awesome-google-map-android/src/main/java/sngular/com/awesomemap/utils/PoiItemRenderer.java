package sngular.com.awesomemap.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.List;

import sngular.com.awesomemap.model.PoiItem;
import sngular.com.awesomemap.model.RendererConfiguration;


/**
 * Created by julio.molinera on 09/06/2015.
 */
public class PoiItemRenderer extends DefaultClusterRenderer<PoiItem> implements GoogleMap.OnCameraChangeListener {

    private final IconGenerator mClusterIconGenerator;
    private final Activity mContext;
    private final RendererConfiguration mRendererConfiguration;

    final static int PADDING_LEFT = 12;
    final static int PADDING_TOP = 8;
    final static int PADDING_CLUSTER_LEFT = 6;
    final static int PADDING_CLUSTER_TOP = 22;

    public PoiItemRenderer(Activity context, GoogleMap map, ClusterManager<PoiItem> clusterManager, RendererConfiguration rendererConfiguration) {
        super(context, map, clusterManager);

        mRendererConfiguration = rendererConfiguration;
        mClusterIconGenerator = new IconGenerator(context.getApplicationContext());
        mContext = context;

    }

    /**
     * Metedo que se llama antes de renderizar un marker simple cuando estamos utilizando clusters
     *
     * @param item
     * @param markerOptions
     */

    @Override
    protected void onBeforeClusterItemRendered(PoiItem item, MarkerOptions markerOptions) {
        if (ResourcesValidator.isValidDrawableResource(mContext, item.getImageResource())) {
            if (Float.floatToRawIntBits(item.getCommission()) == 0) {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(item.getImageResource()));
            } else {
                markerOptions.icon(getIconWithText(item));
            }
        } else {
            Log.e("onBeforeClusterItem", "Fallo obteniendo recurso");
        }
        markerOptions.title(item.getId() + "");
    }

    private BitmapDescriptor getIconWithText(PoiItem item) {
        mClusterIconGenerator.setBackground(mContext.getResources().getDrawable(item.getImageResource()));
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

        mClusterIconGenerator.setTextAppearance(mContext, mRendererConfiguration.getPoiStyle());
        Bitmap icon = mClusterIconGenerator.makeIcon(comision);
        return BitmapDescriptorFactory.fromBitmap(icon);
    }

    /**
     * Metodo que se llama antes de renderizar un cluster
     *
     * @param cluster
     * @param markerOptions
     */

    @Override
    protected void onBeforeClusterRendered(Cluster<PoiItem> cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);
        if (mRendererConfiguration != null) {
            List<PoiItem> items = (List<PoiItem>) cluster.getItems();
            if (items.get(0).getIndicadorComision() == null) {
                mClusterIconGenerator.setBackground(mRendererConfiguration.getImageOfficeDrawable());
            } else {
                mClusterIconGenerator.setBackground(mRendererConfiguration.getImageATMDrawable());
            }
            if (cluster.getSize() < 10) {
                mClusterIconGenerator.setContentPadding(dpConverter(PADDING_CLUSTER_LEFT) + dpConverter(15),
                        dpConverter(PADDING_CLUSTER_TOP), 0, 0);
            } else if (cluster.getSize() < 100) {
                mClusterIconGenerator.setContentPadding(dpConverter(PADDING_CLUSTER_LEFT) + dpConverter(11),
                        dpConverter(PADDING_CLUSTER_TOP), 0, 0);
            } else {
                mClusterIconGenerator.setContentPadding(dpConverter(PADDING_CLUSTER_LEFT) + dpConverter(8),
                        dpConverter(PADDING_CLUSTER_TOP), 0, 0);
            }

            mClusterIconGenerator.setTextAppearance(mContext, mRendererConfiguration.getClusterStyle());
            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }
    }

    /**
     * Metodo que se llama antes de renderizar un cluster para ver si se tiene que renderizar como cluster o como otra cosa
     *
     * @param cluster
     * @return
     */
    @Override
    protected boolean shouldRenderAsCluster(Cluster<PoiItem> cluster) {
        return cluster.getSize() > 1;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    public int dpConverter(int dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}

package sngular.com.awesomemap.listener;

import com.google.maps.android.clustering.Cluster;

import sngular.com.awesomemap.model.PoiItem;

/**
 * Created by julio.molinera on 30/06/2015.
 */
public interface ClusterClickListener {
    void onLastClusterClick(Cluster<PoiItem> cluster);
}

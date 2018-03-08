package sngular.com.awesomemap.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by julio.molinera on 09/06/2015.
 */
public class PoiItem implements ClusterItem {

    private final LatLng mPosition;
    private int mImageResource;
    private int mOverResource;
    private String id;
    private boolean isSelected;
    private String indicadorComision;
    private float commission;


    public PoiItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);

    }

    public int getImageResource() {
        return mImageResource;
    }

    public void setImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getOverResource() {
        return mOverResource;
    }

    public void setOverResource(int mOverResource) {
        this.mOverResource = mOverResource;
    }

    public String getIndicadorComision() {
        return indicadorComision;
    }

    public void setIndicadorComision(String indicadorComision) {
        this.indicadorComision = indicadorComision;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof PoiItem) {
            PoiItem other = (PoiItem) object;
            return id.equals(other.id);
        } else {
            return false;
        }
    }
}

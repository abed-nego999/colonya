package sngular.com.awesomemap.model;

import android.graphics.drawable.Drawable;

/**
 * Created by julio.molinera on 09/06/2015.
 */
public class RendererConfiguration {

    private int clusterStyle, poiStyle, poiBigStyle;
    private Drawable imageATMDrawable;
    private Drawable imageOfficeDrawable;

    public RendererConfiguration(int clusterStyle, int poiStyle, int poiBigStyle, Drawable imageATMDrawable, Drawable imageOfficeDrawable) {
        this.clusterStyle = clusterStyle;
        this.poiStyle = poiStyle;
        this.poiBigStyle = poiBigStyle;
        this.imageATMDrawable = imageATMDrawable;
        this.imageOfficeDrawable = imageOfficeDrawable;
    }

    public int getClusterStyle() {
        return clusterStyle;
    }

    public void setClusterStyle(int clusterStyle) {
        this.clusterStyle = clusterStyle;
    }

    public int getPoiStyle() {
        return poiStyle;
    }

    public void setPoiStyle(int poiStyle) {
        this.poiStyle = poiStyle;
    }

    public int getPoiBigStyle() {
        return poiBigStyle;
    }

    public void setPoiBigStyle(int poiBigStyle) {
        this.poiBigStyle = poiBigStyle;
    }

    public Drawable getImageATMDrawable() {
        return imageATMDrawable;
    }

    public void setImageATMDrawable(Drawable imageATMDrawable) {
        this.imageATMDrawable = imageATMDrawable;
    }

    public Drawable getImageOfficeDrawable() {
        return imageOfficeDrawable;
    }

    public void setImageOfficeDrawable(Drawable imageOfficeDrawable) {
        this.imageOfficeDrawable = imageOfficeDrawable;
    }
}

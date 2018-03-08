package sngular.com.atmsmap.presentation.model;

import sngular.com.awesomemap.model.PoiItem;

/**
 * Created by alberto.hernandez on 11/04/2016.
 */
public class POI {
    private Presentation mPresentation;
    private PoiItem mPoiItem;

    public POI(Presentation presentation, PoiItem poiItem) {
        this.mPresentation = presentation;
        this.mPoiItem = poiItem;
    }

    public Presentation getPresentation() {
        return mPresentation;
    }

    public void setPresentation(Presentation mPresentation) {
        this.mPresentation = mPresentation;
    }

    public PoiItem getPoiItem() {
        return mPoiItem;
    }

    public void setPoiItem(PoiItem mPoiItem) {
        this.mPoiItem = mPoiItem;
    }
}

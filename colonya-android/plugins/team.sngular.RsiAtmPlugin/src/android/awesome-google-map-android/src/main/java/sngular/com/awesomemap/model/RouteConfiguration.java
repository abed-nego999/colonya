package sngular.com.awesomemap.model;


import sngular.com.awesomemap.net.DirectionsService;

/**
 * Created by julio.molinera on 22/06/2015.
 */
public class RouteConfiguration {

    private DirectionsService.ROUTE_MODE mRouteMode;
    private int mLineWidth;
    private int mLineColor;

    public RouteConfiguration(DirectionsService.ROUTE_MODE mRouteMode) {
        this.mRouteMode = mRouteMode;

        this.mLineColor = android.R.color.black;
        this.mLineWidth = 5;
    }

    public int getLineWidth() {
        return mLineWidth;
    }

    public void setLineWidth(int mLineWidth) {
        if (mLineWidth > 5 && mLineWidth < 50) {
            this.mLineWidth = mLineWidth;
        }
    }

    public int getLineColor() {
        return mLineColor;
    }

    public void setLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
    }

    public DirectionsService.ROUTE_MODE getmRouteMode() {
        return mRouteMode;
    }

    public void setmRouteMode(DirectionsService.ROUTE_MODE mRouteMode) {
        this.mRouteMode = mRouteMode;
    }
}

package sngular.com.awesomemap.net;

import sngular.com.awesomemap.net.model.LatLon;
import sngular.com.awesomemap.net.model.Route;

/**
 * Created by julio.molinera on 15/06/2015.
 */
public interface DirectionsService {
    enum ROUTE_MODE {WALKING, DRIVING, BICYCLING, TRANSIT}

    Route getRoute(LatLon origin, LatLon destination, ROUTE_MODE mode);
}

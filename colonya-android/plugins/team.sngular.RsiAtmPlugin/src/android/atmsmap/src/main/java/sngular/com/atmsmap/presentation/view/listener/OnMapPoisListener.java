package sngular.com.atmsmap.presentation.view.listener;

import java.util.List;
import java.util.Map;

import sngular.com.atmsmap.presentation.model.POI;
import sngular.com.atmsmap.presentation.model.PresentationATM;
import sngular.com.atmsmap.presentation.model.PresentationOffice;
import sngular.com.awesomemap.model.PoiItem;

/**
 * Created by alberto.hernandez on 22/04/2016.
 */
public interface OnMapPoisListener {
    void addPoisCluster(List<PoiItem> poisList, Map<String, POI> poisMap);

    void addATMDistanceCommission(PresentationATM atm);

    void addOfficeDistanceCommission(PresentationOffice office);
}

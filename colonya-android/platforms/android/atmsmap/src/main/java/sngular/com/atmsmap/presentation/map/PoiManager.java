package sngular.com.atmsmap.presentation.map;

import com.google.android.gms.maps.model.LatLng;

import sngular.com.atmsmap.R;
import sngular.com.atmsmap.presentation.model.PresentationATM;
import sngular.com.atmsmap.presentation.model.PresentationOffice;
import sngular.com.awesomemap.model.PoiItem;

/**
 * Created by alberto.hernandez on 25/04/2016.
 */
public class PoiManager {
    private PoiManager() {
        //Empty Constructor
    }

    public static PoiItem createATMPoi(PresentationATM atm) {
        PoiItem poiItem = new PoiItem(atm.getLatitude(), atm.getLongitude());
        poiItem.setId(atm.getCodigoCajeros());
        poiItem.setIndicadorComision(atm.getIndicadorComision());
        poiItem.setCommission(atm.getCommission());

        switch (atm.getType()) {
            case ATM_SAME_ENTITY:
                poiItem.setImageResource(R.drawable.ic_poi_verde_cero);
                poiItem.setOverResource(R.drawable.ic_poi_verde_cero_big);
                break;

            case ATM_SAME_GROUP_WITHOUT_COMMISSION:
                poiItem.setImageResource(R.drawable.ic_poi_azul_cero);
                poiItem.setOverResource(R.drawable.ic_poi_azul_cero_big);
                break;

            case ATM_SAME_GROUP_WITH_COMMISSION:
                poiItem.setImageResource(R.drawable.ic_poi_azul);
                poiItem.setOverResource(R.drawable.ic_poi_azul_big);
                break;

            case ATM_DIFFERENT_GROUP_WITHOUT_COMMISSION:
                poiItem.setImageResource(R.drawable.ic_poi_gris_cero);
                poiItem.setOverResource(R.drawable.ic_poi_gris_cero_big);
                break;

            case ATM_DIFFERENT_GROUP_WITH_COMMISSION:
                poiItem.setImageResource(R.drawable.ic_poi_gris);
                poiItem.setOverResource(R.drawable.ic_poi_gris_big);
                break;
        }

        return poiItem;
    }

    public static PoiItem createOfficePoi(PresentationOffice office) {
        PoiItem poiItem = new PoiItem(office.getLatitude(), office.getLongitude());
        poiItem.setId(office.getCodigoOficina());
        poiItem.setImageResource(R.drawable.ic_poi_oficina);
        poiItem.setOverResource(R.drawable.ic_poi_oficina_big);
        poiItem.setCommission(0.0f);
        poiItem.setIndicadorComision(null);
        return poiItem;
    }

    public static PoiItem createSearchPoi(LatLng position) {
        PoiItem poiItem = new PoiItem(position.latitude, position.longitude);
        poiItem.setImageResource(R.drawable.ic_poi_busqueda);
        poiItem.setCommission(0.0f);
        return poiItem;
    }
}

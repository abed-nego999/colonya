package sngular.com.awesomemap.listener;

import com.google.android.gms.location.places.PlaceBuffer;

/**
 * Created by julio.molinera on 24/06/2015.
 */
public interface PlaceClickListener {
    //Interfaz necesasia para saber cuando se ha pulsado un lugar en el AddressSearcher
     void placeClick(PlaceBuffer place);
}

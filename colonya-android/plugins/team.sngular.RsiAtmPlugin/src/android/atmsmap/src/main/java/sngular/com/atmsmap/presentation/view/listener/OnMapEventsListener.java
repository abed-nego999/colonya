package sngular.com.atmsmap.presentation.view.listener;

import java.util.List;

import sngular.com.atmsmap.presentation.model.Presentation;
import sngular.com.atmsmap.presentation.model.PresentationATM;
import sngular.com.atmsmap.presentation.model.PresentationOffice;

/**
 * Created by alberto.hernandez on 06/04/2016.
 */
public interface OnMapEventsListener {

    void onMarkerTapATM(PresentationATM atm);

    void onMarkerTapOffice(PresentationOffice office);

    void onMapClick();

    void onError(Throwable ex);

    void onErrorStrings(String title, String subtitle);

    void showHelp();

    void onATMDistanceCommission(PresentationATM lastATM);

    void onOfficeDistanceCommission(PresentationOffice lastOffice);

    void onClusterList(List<Presentation> clusterList);
}

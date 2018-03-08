package sngular.com.atmsmap.presentation.view.listener;

import sngular.com.atmsmap.presentation.model.PresentationATM;
import sngular.com.atmsmap.presentation.model.PresentationOffice;

/**
 * Created by luissedano on 21/4/16.
 */
public interface OnRecyclerItemSelectedListener {

    void onRecyclerATMItemSelected(PresentationATM presentationATM);

    void onRecyclerOfficeItemSelected(PresentationOffice presentationOffice);
}

package sngular.com.atmsmap.data.listener;

import sngular.com.atmsmap.data.model.ErrorEntity;
import sngular.com.atmsmap.data.model.office.OfficeFullResponseEntity;

/**
 * Created by alberto.hernandez on 22/04/2016.
 */
public interface OnOfficesRequest {
    void onResponse(OfficeFullResponseEntity officeFullResponseEntity);

    void onErrorResponse(ErrorEntity error);
}

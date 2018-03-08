package sngular.com.atmsmap.data.listener;

import sngular.com.atmsmap.data.model.ErrorEntity;
import sngular.com.atmsmap.data.model.atm.ATMFullResponseEntity;

/**
 * Created by alberto.hernandez on 22/04/2016.
 */
public interface OnATMsRequest {
    void onResponse(ATMFullResponseEntity atmFullResponseEntity);

    void onErrorResponse(ErrorEntity error);
}

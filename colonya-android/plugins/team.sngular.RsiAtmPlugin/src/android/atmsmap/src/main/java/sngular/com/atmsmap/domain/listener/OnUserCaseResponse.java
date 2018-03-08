package sngular.com.atmsmap.domain.listener;

import java.util.List;

import sngular.com.atmsmap.domain.model.ATMDTO;
import sngular.com.atmsmap.domain.model.ErrorDTO;
import sngular.com.atmsmap.domain.model.OfficeDTO;

/**
 * Created by alberto.hernandez on 22/04/2016.
 */
public interface OnUserCaseResponse {
    void onATMListResponse(List<ATMDTO> atmDtoList);

    void onOfficeListResponse(List<OfficeDTO> officeDtoList);

    void onResponseError(ErrorDTO errorDTO);
}

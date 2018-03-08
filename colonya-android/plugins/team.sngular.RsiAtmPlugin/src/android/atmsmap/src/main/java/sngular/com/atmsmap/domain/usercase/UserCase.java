package sngular.com.atmsmap.domain.usercase;

import android.util.Log;

import java.util.List;

import javax.inject.Singleton;

import sngular.com.atmsmap.data.listener.OnATMsRequest;
import sngular.com.atmsmap.data.listener.OnOfficesRequest;
import sngular.com.atmsmap.data.model.ErrorEntity;
import sngular.com.atmsmap.data.model.atm.ATMFullResponseEntity;
import sngular.com.atmsmap.data.model.office.OfficeFullResponseEntity;
import sngular.com.atmsmap.data.rest.GetATMsService;
import sngular.com.atmsmap.data.rest.GetOfficesService;
import sngular.com.atmsmap.domain.listener.OnUserCaseResponse;
import sngular.com.atmsmap.domain.model.ATMDTO;
import sngular.com.atmsmap.domain.model.ErrorDTO;
import sngular.com.atmsmap.domain.model.OfficeDTO;
import sngular.com.atmsmap.domain.model.mapper.ATMDTOMapper;
import sngular.com.atmsmap.domain.model.mapper.ErrorDTOMapper;
import sngular.com.atmsmap.domain.model.mapper.OfficeDTOMapper;
import sngular.com.atmsmap.presentation.model.map.ScreenCenterAndRadius;

/**
 * Created by alberto.hernandez on 21/04/2016.
 */
@Singleton
public class UserCase implements UserCaseService {
    private static final String TAG = UserCase.class.getSimpleName();

    private GetATMsService mGetATMs;
    private GetOfficesService mGetOffices;

    public UserCase(GetATMsService getATMs, GetOfficesService getOffices) {
        mGetATMs = getATMs;
        mGetOffices = getOffices;
    }

    @Override
    public void getATMs(ScreenCenterAndRadius screenCenterAndRadius, final OnUserCaseResponse onUserCaseResponse) {
        Log.d(TAG, "getATMs");

        OnATMsRequest onATMsRequest = new OnATMsRequest() {
            @Override
            public void onResponse(ATMFullResponseEntity atmFullResponseEntity) {
                Log.d(TAG, "onResponse: " + atmFullResponseEntity.getRespuesta().getNumeroRegistros());
                if (Integer.valueOf(atmFullResponseEntity.getRespuesta().getNumeroRegistros()) > 0) {
                    ATMDTOMapper mapper = new ATMDTOMapper();
                    List<ATMDTO> atmDtoList = mapper.modelToData(atmFullResponseEntity.getRespuesta().getListaCajeros());
                    onUserCaseResponse.onATMListResponse(atmDtoList);
                }
            }

            @Override
            public void onErrorResponse(ErrorEntity error) {
                ErrorDTOMapper mapper = new ErrorDTOMapper();
                ErrorDTO errorDTO = mapper.modelToData(error);
                onUserCaseResponse.onResponseError(errorDTO);
            }
        };
        mGetATMs.getAtms(screenCenterAndRadius, onATMsRequest);
//        mGetATMs.getMockAtms(screenCenterAndRadius, onATMsRequest);

    }

    @Override
    public void getOffices(ScreenCenterAndRadius screenCenterAndRadius, final OnUserCaseResponse onUserCaseResponse) {
        Log.d("UserCase", "getOffices");

        OnOfficesRequest onOfficesRequest = new OnOfficesRequest() {
            @Override
            public void onResponse(OfficeFullResponseEntity officeFullResponseEntity) {
                Log.d(TAG, "onResponse: " + officeFullResponseEntity.getRespuesta().getNumeroRegistros());
                if (Integer.valueOf(officeFullResponseEntity.getRespuesta().getNumeroRegistros()) > 0) {
                    OfficeDTOMapper mapper = new OfficeDTOMapper();
                    List<OfficeDTO> officeDtoList = mapper.modelToData(officeFullResponseEntity.getRespuesta().getListaLocalizacion());
                    onUserCaseResponse.onOfficeListResponse(officeDtoList);
                }
            }

            @Override
            public void onErrorResponse(ErrorEntity error) {
                ErrorDTOMapper mapper = new ErrorDTOMapper();
                ErrorDTO errorDTO = mapper.modelToData(error);
                onUserCaseResponse.onResponseError(errorDTO);
            }
        };
        mGetOffices.getOffices(screenCenterAndRadius, onOfficesRequest);
//        mGetOffices.getMockOffices(screenCenterAndRadius, onOfficesRequest);
    }
}

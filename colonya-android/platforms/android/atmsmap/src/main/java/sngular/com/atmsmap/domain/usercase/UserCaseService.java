package sngular.com.atmsmap.domain.usercase;

import sngular.com.atmsmap.domain.listener.OnUserCaseResponse;
import sngular.com.atmsmap.presentation.model.map.ScreenCenterAndRadius;

/**
 * Created by alberto.hernandez on 21/04/2016.
 */
public interface UserCaseService {
    void getATMs(ScreenCenterAndRadius screenCenterAndRadius, OnUserCaseResponse onUserCaseResponse);

    void getOffices(ScreenCenterAndRadius screenCenterAndRadius, OnUserCaseResponse onUserCaseResponse);
}

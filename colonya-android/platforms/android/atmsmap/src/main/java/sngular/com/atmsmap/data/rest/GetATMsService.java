package sngular.com.atmsmap.data.rest;

import sngular.com.atmsmap.data.listener.OnATMsRequest;
import sngular.com.atmsmap.presentation.model.map.ScreenCenterAndRadius;

/**
 * Created by alberto.hernandez on 22/04/2016.
 */
public interface GetATMsService {
    void getAtms(ScreenCenterAndRadius screenCenterAndRadius, final OnATMsRequest onATMsRequest);

    void getMockAtms(ScreenCenterAndRadius screenCenterAndRadius, OnATMsRequest onATMsRequest);
}

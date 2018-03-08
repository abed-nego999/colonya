package sngular.com.atmsmap.data.rest;

import sngular.com.atmsmap.data.listener.OnOfficesRequest;
import sngular.com.atmsmap.presentation.model.map.ScreenCenterAndRadius;

/**
 * Created by alberto.hernandez on 22/04/2016.
 */
public interface GetOfficesService {
    void getOffices(ScreenCenterAndRadius screenCenterAndRadius, final OnOfficesRequest onOfficesRequest);

    void getMockOffices(ScreenCenterAndRadius screenCenterAndRadius, OnOfficesRequest onOfficesRequest);
}

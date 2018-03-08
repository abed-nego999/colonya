package sngular.com.atmsmap.presentation.model.mapper;


import android.location.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sngular.com.atmsmap.domain.model.OfficeDTO;
import sngular.com.atmsmap.presentation.model.PresentationOffice;
import sngular.com.atmsmap.presentation.model.map.ScreenCenterAndRadius;
import sngular.com.atmsmap.presentation.model.mapper.base.Mapper;
import sngular.com.awesomemap.utils.LocationUtils;

public class PresentationOfficeMapper implements Mapper<List<OfficeDTO>, List<PresentationOffice>> {

    @Override
    public List<PresentationOffice> modelToData(List<OfficeDTO> model, String entityCode,
                                                ScreenCenterAndRadius location) {

        ArrayList<PresentationOffice> presentationOfficeList = new ArrayList<>();
        for (OfficeDTO officeDto : model) {

            Location targetLocation = new Location("");
            targetLocation.setLatitude(Float.valueOf(officeDto.getLatitud()));
            targetLocation.setLongitude(Float.valueOf(officeDto.getLongitud()));

            Location originLocation = new Location("");
            originLocation.setLatitude(location.getCenterScreenLatitude());
            originLocation.setLongitude(location.getCenterScreenLongitude());

            Float distanciaFloat = originLocation.distanceTo(targetLocation) / 1000;
            String distance = LocationUtils.getDistanceToShow(String.valueOf(distanciaFloat));

            PresentationOffice presentationOffice = new PresentationOffice(officeDto.getCodigoEntidad(),
                    officeDto.getCodigoOficina(), officeDto.getNombreEntidad(), officeDto.getDireccion(),
                    officeDto.getCodigoPostal(), officeDto.getCodigoProvincia(), officeDto.getTelefono(),
                    officeDto.getIndCajero(), distance, officeDto.getNombreLocalidad(),
                    officeDto.getNombreProvincia(), officeDto.getResultado(), officeDto.getHorario(),
                    officeDto.getFestivos(), officeDto.getMail(), officeDto.getNombreOficina(),
                    distanciaFloat, Float.valueOf(officeDto.getLatitud()), Float.valueOf(officeDto.getLongitud()));

            presentationOfficeList.add(presentationOffice);
        }
        Collections.sort(presentationOfficeList, new CustomComparator());
        return presentationOfficeList;
    }

    public class CustomComparator implements Comparator<PresentationOffice> {
        @Override
        public int compare(PresentationOffice o1, PresentationOffice o2) {
            return (Float.valueOf(o1.getDistanciaFloat())).compareTo(o2.getDistanciaFloat());
        }
    }
}

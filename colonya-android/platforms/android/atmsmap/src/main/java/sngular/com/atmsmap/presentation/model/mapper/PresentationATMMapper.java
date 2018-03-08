package sngular.com.atmsmap.presentation.model.mapper;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sngular.com.atmsmap.app.constant.ATM_Type;
import sngular.com.atmsmap.domain.model.ATMDTO;
import sngular.com.atmsmap.presentation.model.PresentationATM;
import sngular.com.atmsmap.presentation.model.map.ScreenCenterAndRadius;
import sngular.com.atmsmap.presentation.model.mapper.base.Mapper;
import sngular.com.awesomemap.utils.LocationUtils;

public class PresentationATMMapper implements Mapper<List<ATMDTO>, List<PresentationATM>> {

    final String ATM_SAME_GROUP_ID = "X";
    final String ATM_DIFFERENT_GROUP_ID = "Y";

    @Override
    public List<PresentationATM> modelToData(List<ATMDTO> model, String entityCode, ScreenCenterAndRadius location) {

        ArrayList<PresentationATM> presentationATMList = new ArrayList<>();
        for (ATMDTO atmdto : model) {
            ATM_Type atmType = ATM_Type.ATM_SAME_ENTITY;
            float commission = 0;

            if (entityCode.equalsIgnoreCase(atmdto.getCodigoEntidad())) {
                atmType = ATM_Type.ATM_SAME_ENTITY;
            }else if (atmdto.getIndicadorCajero().equalsIgnoreCase(ATM_SAME_GROUP_ID)) {
                if (!"0".equalsIgnoreCase(atmdto.getComision())) {
                    atmType = ATM_Type.ATM_SAME_GROUP_WITH_COMMISSION;
                    commission = Float.valueOf(atmdto.getComision());
                } else {
                    atmType = ATM_Type.ATM_SAME_GROUP_WITHOUT_COMMISSION;
                }
            }else if (atmdto.getIndicadorCajero().equalsIgnoreCase(ATM_DIFFERENT_GROUP_ID)) {
                if (!"0".equalsIgnoreCase(atmdto.getComision())) {
                    atmType = ATM_Type.ATM_DIFFERENT_GROUP_WITH_COMMISSION;
                    commission = Float.valueOf(atmdto.getComision());
                } else {
                    atmType = ATM_Type.ATM_DIFFERENT_GROUP_WITHOUT_COMMISSION;
                }
            }

            Float distanciaFloat = Float.valueOf(atmdto.getDistancia());
            String distance = LocationUtils.getDistanceToShow(atmdto.getDistancia());

            PresentationATM presentationATM = new PresentationATM(atmdto.getCodigoEntidad(),
                    atmdto.getNombreEntidad(), atmdto.getCodigoCajeros(), atmdto.getDireccion(),
                    atmdto.getCodigoPostal(), atmdto.getIndicadorCajero(), atmdto.getNombreLocalidad(),
                    atmdto.getIndicadorComision(), distance, atmType, distanciaFloat, commission,
                    Float.valueOf(atmdto.getLatitud()), Float.valueOf(atmdto.getLongitud()));

            presentationATMList.add(presentationATM);
        }
        Collections.sort(presentationATMList, new CustomComparator());
        return presentationATMList;
    }

    public class CustomComparator implements Comparator<PresentationATM> {
        @Override
        public int compare(PresentationATM o1, PresentationATM o2) {
            return (Float.valueOf(o1.getDistanciaFloat())).compareTo(o2.getDistanciaFloat());
        }
    }
}

package sngular.com.atmsmap.domain.model.mapper;

import java.util.ArrayList;
import java.util.List;

import sngular.com.atmsmap.data.model.atm.ATMEntity;
import sngular.com.atmsmap.domain.model.ATMDTO;
import sngular.com.atmsmap.domain.model.mapper.base.Mapper;


public class ATMDTOMapper implements Mapper<List<ATMEntity>, List<ATMDTO>> {
    @Override
    public List<ATMDTO> modelToData(List<ATMEntity> model) {
        List<ATMDTO> atmDtoList = new ArrayList<>();

        for (ATMEntity atmEntity : model) {
            ATMDTO atmdto = new ATMDTO();
            atmdto.setCodigoCajeros(atmEntity.getCodigoCajeros());
            atmdto.setCodigoEntidad(atmEntity.getCodigoEntidad());
            atmdto.setCodigoPostal(atmEntity.getCodigoPostal());
            atmdto.setComision(atmEntity.getComision());
            atmdto.setDireccion(atmEntity.getDireccion());
            atmdto.setDistancia(atmEntity.getDistancia());
            atmdto.setIndicadorCajero(atmEntity.getIndicadorCajero());
            atmdto.setIndicadorComision(atmEntity.getIndicadorComision());
            atmdto.setLatitud(atmEntity.getLatitud());
            atmdto.setLongitud(atmEntity.getLongitud());
            atmdto.setNombreEntidad(atmEntity.getNombreEntidad());
            atmdto.setNombreLocalidad(atmEntity.getNombreLocalidad());
            atmDtoList.add(atmdto);
        }

        return atmDtoList;
    }
}

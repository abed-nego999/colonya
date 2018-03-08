package sngular.com.atmsmap.domain.model.mapper;

import sngular.com.atmsmap.data.model.ErrorEntity;
import sngular.com.atmsmap.domain.model.ErrorDTO;
import sngular.com.atmsmap.domain.model.mapper.base.Mapper;

/**
 * Created by alberto.hernandez on 25/04/2016.
 */
public class ErrorDTOMapper implements Mapper<ErrorEntity, ErrorDTO> {
    @Override
    public ErrorDTO modelToData(ErrorEntity model) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCodigoMostrar(model.getCodigoMostrar());
        errorDTO.setMensajeMostrar(model.getMensajeMostrar());
        errorDTO.setSolucion(model.getSolucion());
        return errorDTO;
    }
}

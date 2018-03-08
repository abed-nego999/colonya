package sngular.com.atmsmap.domain.model.mapper;

import java.util.ArrayList;
import java.util.List;

import sngular.com.atmsmap.data.model.office.OfficeEntity;
import sngular.com.atmsmap.domain.model.OfficeDTO;
import sngular.com.atmsmap.domain.model.mapper.base.Mapper;


public class OfficeDTOMapper implements Mapper<List<OfficeEntity>, List<OfficeDTO>> {
    @Override
    public List<OfficeDTO> modelToData(List<OfficeEntity> model) {
        List<OfficeDTO> officeDtoList = new ArrayList<>();

        for (OfficeEntity officeEntity : model) {
            OfficeDTO officeDto = new OfficeDTO();
            officeDto.setCodigoEntidad(officeEntity.getCodigoEntidad());
            officeDto.setCodigoOficina(officeEntity.getCodigoOficina());
            officeDto.setNombreEntidad(officeEntity.getNombreEntidad());
            officeDto.setDireccion(officeEntity.getDireccion());
            officeDto.setCodigoPostal(officeEntity.getCodigoPostal());
            officeDto.setCodigoProvincia(officeEntity.getCodigoProvincia());
            officeDto.setMail(officeEntity.getMail());
            officeDto.setTelefono(officeEntity.getTelefono());
            officeDto.setIndCajero(officeEntity.getIndCajero());
            officeDto.setLatitud(officeEntity.getLatitud());
            officeDto.setLongitud(officeEntity.getLongitud());
            officeDto.setNombreOficina(officeEntity.getNombreOficina());
            officeDto.setNombreLocalidad(officeEntity.getNombreLocalidad());
            officeDto.setNombreProvincia(officeEntity.getNombreProvincia());
            officeDto.setResultado(officeEntity.getResultado());
            officeDto.setHorario(officeEntity.getHorario());
            officeDto.setFestivos(officeEntity.getFestivos());

            officeDtoList.add(officeDto);
        }

        return officeDtoList;
    }
}

package sngular.com.atmsmap.data.model.office;

import sngular.com.atmsmap.data.model.ErrorEntity;

/**
 * Created by alberto.hernandez on 25/04/2016.
 */
public class OfficeFullResponseEntity {
    String codigoRetorno;
    OfficeResponseEntity Respuesta;
    ErrorEntity Errores;

    public String getCodigoRetorno() {
        return codigoRetorno;
    }

    public void setCodigoRetorno(String codigoRetorno) {
        this.codigoRetorno = codigoRetorno;
    }

    public OfficeResponseEntity getRespuesta() {
        return Respuesta;
    }

    public void setRespuesta(OfficeResponseEntity respuesta) {
        Respuesta = respuesta;
    }

    public ErrorEntity getErrores() {
        return Errores;
    }

    public void setErrores(ErrorEntity errores) {
        Errores = errores;
    }
}

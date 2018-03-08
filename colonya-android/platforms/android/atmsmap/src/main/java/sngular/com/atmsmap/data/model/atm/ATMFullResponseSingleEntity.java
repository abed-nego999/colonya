package sngular.com.atmsmap.data.model.atm;

import sngular.com.atmsmap.data.model.ErrorEntity;

/**
 * Created by alberto.hernandez on 25/04/2016.
 */
public class ATMFullResponseSingleEntity {
    String codigoRetorno;
    ATMResponseSingleEntity Respuesta;
    ErrorEntity Errores;

    public String getCodigoRetorno() {
        return codigoRetorno;
    }

    public void setCodigoRetorno(String codigoRetorno) {
        this.codigoRetorno = codigoRetorno;
    }

    public ATMResponseSingleEntity getRespuesta() {
        return Respuesta;
    }

    public void setRespuesta(ATMResponseSingleEntity respuesta) {
        Respuesta = respuesta;
    }

    public ErrorEntity getErrores() {
        return Errores;
    }

    public void setErrores(ErrorEntity errores) {
        Errores = errores;
    }
}

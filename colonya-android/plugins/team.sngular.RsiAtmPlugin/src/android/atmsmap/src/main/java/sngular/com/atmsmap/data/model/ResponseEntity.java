package sngular.com.atmsmap.data.model;

/**
 * Created by alberto.hernandez on 25/04/2016.
 */
public class ResponseEntity {
    String codigoRetorno;
    ResponseCountEntities Respuesta;
    ErrorEntity Errores;
    ErrorOfficeEntity errores;

    public String getCodigoRetorno() {
        return codigoRetorno;
    }

    public void setCodigoRetorno(String codigoRetorno) {
        this.codigoRetorno = codigoRetorno;
    }

    public ResponseCountEntities getRespuesta() {
        return Respuesta;
    }

    public void setRespuesta(ResponseCountEntities respuesta) {
        Respuesta = respuesta;
    }

    public ErrorEntity getErrores() {
        return Errores;
    }

    public void setErrores(ErrorEntity Errores) {
        this.Errores = Errores;
    }

    public ErrorOfficeEntity getOfficeErrores() {
        return errores;
    }

    public void setOfficeErrores(ErrorOfficeEntity errores) {
        this.errores = errores;
    }
}

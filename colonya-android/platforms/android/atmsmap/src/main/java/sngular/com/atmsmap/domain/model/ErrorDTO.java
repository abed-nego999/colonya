package sngular.com.atmsmap.domain.model;

/**
 * Created by alberto.hernandez on 25/04/2016.
 */
public class ErrorDTO {
    //TODO Cambiar los nombre terminología inglesa
    String codigoMostrar, mensajeMostrar, solucion;

    public String getCodigoMostrar() {
        return codigoMostrar;
    }

    public void setCodigoMostrar(String codigoMostrar) {
        this.codigoMostrar = codigoMostrar;
    }

    public String getMensajeMostrar() {
        return mensajeMostrar;
    }

    public void setMensajeMostrar(String mensajeMostrar) {
        this.mensajeMostrar = mensajeMostrar;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }
}

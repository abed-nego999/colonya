package sngular.com.atmsmap.data.model;

/**
 * Created by alberto.hernandez on 09/05/2016.
 */
public class ErrorOfficeEntity {
    @com.google.gson.annotations.SerializedName("ns1:codigoMostrar")
    XMLEntity codigoMostrar;
    @com.google.gson.annotations.SerializedName("ns1:mensajeMostrar")
    XMLEntity mensajeMostrar;
    @com.google.gson.annotations.SerializedName("ns1:solucion")
    XMLEntity solucion;

    public XMLEntity getCodigoMostrar() {
        return codigoMostrar;
    }

    public void setCodigoMostrar(XMLEntity codigoMostrar) {
        this.codigoMostrar = codigoMostrar;
    }

    public XMLEntity getMensajeMostrar() {
        return mensajeMostrar;
    }

    public void setMensajeMostrar(XMLEntity mensajeMostrar) {
        this.mensajeMostrar = mensajeMostrar;
    }

    public XMLEntity getSolucion() {
        return solucion;
    }

    public void setSolucion(XMLEntity solucion) {
        this.solucion = solucion;
    }
}

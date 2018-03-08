package sngular.com.atmsmap.presentation.model;

import sngular.com.atmsmap.app.constant.ATM_Type;

/**
 * Created by alberto.hernandez on 11/04/2016.
 */


public  class PresentationATM implements Presentation {
    //TODO Cambiar los nombre terminología inglesa
    String codigoEntidad, nombreEntidad, codigoCajeros, direccion, codigoPostal, indicadorCajero,
            nombreLocalidad, indicadorComision, distancia;
    ATM_Type type;
    float distanciaFloat, commission, latitude, longitude;

    public PresentationATM(String codigoEntidad, String nombreEntidad, String codigoCajeros, String direccion,
                           String codigoPostal, String indicadorCajero, String nombreLocalidad,
                           String indicadorComision, String distancia, ATM_Type type, float distanciaFloat,
                           float commission, float latitude, float longitude) {
        this.codigoEntidad = codigoEntidad;
        this.nombreEntidad = nombreEntidad;
        this.codigoCajeros = codigoCajeros;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.indicadorCajero = indicadorCajero;
        this.nombreLocalidad = nombreLocalidad;
        this.indicadorComision = indicadorComision;
        this.distancia = distancia;
        this.type = type;
        this.distanciaFloat = distanciaFloat;
        this.commission = commission;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public String getCodigoCajeros() {
        return codigoCajeros;
    }

    public void setCodigoCajeros(String codigoCajeros) {
        this.codigoCajeros = codigoCajeros;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getIndicadorCajero() {
        return indicadorCajero;
    }

    public void setIndicadorCajero(String indicadorCajero) {
        this.indicadorCajero = indicadorCajero;
    }

    public String getNombreLocalidad() {
        return nombreLocalidad;
    }

    public void setNombreLocalidad(String nombreLocalidad) {
        this.nombreLocalidad = nombreLocalidad;
    }

    public String getIndicadorComision() {
        return indicadorComision;
    }

    public void setIndicadorComision(String indicadorComision) {
        this.indicadorComision = indicadorComision;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public ATM_Type getType() {
        return type;
    }

    public void setType(ATM_Type type) {
        this.type = type;
    }

    public float getDistanciaFloat() {
        return distanciaFloat;
    }

    public void setDistanciaFloat(float distanciaFloat) {
        this.distanciaFloat = distanciaFloat;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}

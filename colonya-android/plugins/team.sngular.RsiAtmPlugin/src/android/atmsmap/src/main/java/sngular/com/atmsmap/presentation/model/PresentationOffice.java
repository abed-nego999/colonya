package sngular.com.atmsmap.presentation.model;

import android.support.annotation.Nullable;


/**
 * Created by alberto.hernandez on 11/04/2016.
 */

public class PresentationOffice implements Presentation {
    //TODO Cambiar los nombre terminología inglesa
    String codigoEntidad, codigoOficina, nombreEntidad, direccion, codigoPostal, codigoProvincia, telefono,
            indCajero, distancia, nombreLocalidad, nombreProvincia, resultado, horario, festivos;
    @Nullable
    String mail;

    @Nullable
    String nombreOficina;

    float distanciaFloat, latitude, longitude;

    public PresentationOffice(String codigoEntidad, String codigoOficina, String nombreEntidad,
                              String direccion, String codigoPostal, String codigoProvincia, String telefono,
                              String indCajero, String distancia, String nombreLocalidad, String nombreProvincia,
                              String resultado, String horario, String festivos, String mail, String nombreOficina,
                              float distanciaFloat, float latitude, float longitude) {
        this.codigoEntidad = codigoEntidad;
        this.codigoOficina = codigoOficina;
        this.nombreEntidad = nombreEntidad;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.codigoProvincia = codigoProvincia;
        this.telefono = telefono;
        this.indCajero = indCajero;
        this.distancia = distancia;
        this.nombreLocalidad = nombreLocalidad;
        this.nombreProvincia = nombreProvincia;
        this.resultado = resultado;
        this.horario = horario;
        this.festivos = festivos;
        this.mail = mail;
        this.nombreOficina = nombreOficina;
        this.distanciaFloat = distanciaFloat;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getCodigoOficina() {
        return codigoOficina;
    }

    public void setCodigoOficina(String codigoOficina) {
        this.codigoOficina = codigoOficina;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
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

    public String getCodigoProvincia() {
        return codigoProvincia;
    }

    public void setCodigoProvincia(String codigoProvincia) {
        this.codigoProvincia = codigoProvincia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIndCajero() {
        return indCajero;
    }

    public void setIndCajero(String indCajero) {
        this.indCajero = indCajero;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getNombreLocalidad() {
        return nombreLocalidad;
    }

    public void setNombreLocalidad(String nombreLocalidad) {
        this.nombreLocalidad = nombreLocalidad;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getFestivos() {
        return festivos;
    }

    public void setFestivos(String festivos) {
        this.festivos = festivos;
    }

    @Nullable
    public String getMail() {
        return mail;
    }

    public void setMail(@Nullable String mail) {
        this.mail = mail;
    }

    @Nullable
    public String getNombreOficina() {
        return nombreOficina;
    }

    public void setNombreOficina(@Nullable String nombreOficina) {
        this.nombreOficina = nombreOficina;
    }

    public float getDistanciaFloat() {
        return distanciaFloat;
    }

    public void setDistanciaFloat(float distanciaFloat) {
        this.distanciaFloat = distanciaFloat;
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

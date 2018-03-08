package sngular.com.atmsmap.data.model.office;

/**
 * Created by alberto.hernandez on 25/04/2016.
 */
public class OfficeResponseSingleEntity {
    String numeroRegistros;
    OfficeEntity ListaLocalizacion;

    public String getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(String numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public OfficeEntity getListaLocalizacion() {
        return ListaLocalizacion;
    }

    public void setListaLocalizacion(OfficeEntity listaLocalizacion) {
        ListaLocalizacion = listaLocalizacion;
    }
}

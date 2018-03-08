package sngular.com.atmsmap.data.model.office;

import java.util.List;

/**
 * Created by alberto.hernandez on 25/04/2016.
 */
public class OfficeResponseEntity {
    String numeroRegistros;
    List<OfficeEntity> ListaLocalizacion;

    public String getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(String numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public List<OfficeEntity> getListaLocalizacion() {
        return ListaLocalizacion;
    }

    public void setListaLocalizacion(List<OfficeEntity> listaLocalizacion) {
        ListaLocalizacion = listaLocalizacion;
    }
}

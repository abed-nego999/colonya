package sngular.com.atmsmap.data.model.atm;

import java.util.List;

/**
 * Created by alberto.hernandez on 25/04/2016.
 */
public class ATMResponseEntity {
    String numeroRegistros;
    List<ATMEntity> ListaCajeros;

    public String getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(String numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public List<ATMEntity> getListaCajeros() {
        return ListaCajeros;
    }

    public void setListaCajeros(List<ATMEntity> listaCajeros) {
        ListaCajeros = listaCajeros;
    }
}

package sngular.com.atmsmap.data.model.atm;

/**
 * Created by alberto.hernandez on 25/04/2016.
 */
public class ATMResponseSingleEntity {
    String numeroRegistros;
    ATMEntity ListaCajeros;

    public String getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(String numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public ATMEntity getListaCajeros() {
        return ListaCajeros;
    }

    public void setListaCajeros(ATMEntity listaCajeros) {
        ListaCajeros = listaCajeros;
    }
}

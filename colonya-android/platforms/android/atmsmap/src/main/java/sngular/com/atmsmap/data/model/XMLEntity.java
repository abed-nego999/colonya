package sngular.com.atmsmap.data.model;

/**
 * Created by alberto.hernandez on 09/05/2016.
 */
public class XMLEntity {

    @com.google.gson.annotations.SerializedName("@xmlns:ns1")
    String title;

    @com.google.gson.annotations.SerializedName("#text")
    String text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

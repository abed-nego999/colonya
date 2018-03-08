package sngular.com.atmsmap.presentation.info;

import android.content.Context;

/**
 * Created by alberto.hernandez on 26/04/2016.
 */
public interface InfoRSIService {
    void setEntity(String entity);

    void setEntityHelpTitle(String entityHelpTitle);

    void setGoogleMapsKeys(String googleMapsWebKey, String googleMapsKey, Context baseContext);

    void setEntityHelpGroups(boolean showGroupAtms);
}

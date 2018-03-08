package sngular.com.atmsmap.presentation.model.mapper.base;

import sngular.com.atmsmap.presentation.model.map.ScreenCenterAndRadius;

/**
 * Created by Fabio on 24/3/15.
 * fabio.santana@medianet.es
 */
public interface Mapper<M, D> {
    D modelToData(M model, String entity, ScreenCenterAndRadius location);
}

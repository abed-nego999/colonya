package sngular.com.atmsmap.domain.model.mapper.base;

public interface Mapper<M, D> {
    D modelToData(M model);
}

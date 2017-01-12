package com.languagelearn.repository;

import java.io.Serializable;
import java.util.Collection;

public interface InternalRepository<T, ID extends Serializable> {

    <S extends T> S save(S entity);

    <S extends T> Collection<S> save(Collection<S> entities);

    <S extends T> Collection<S> save(Collection<S> entities, boolean flush);

    T findById(ID id);

    boolean exists(ID id);

    void delete(ID id);

    <S extends T> void delete(S entity);

    void delete(Collection<? extends T> entities);

    <S extends T> S save(S entity, boolean flush);

    void executeUpdateSQL(String sql);

}

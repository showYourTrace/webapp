package com.languagelearn.object;

import java.util.Collection;

public interface DecodeEntity<M, T> {
    T decode(M object);
    Collection<T> decode(Collection<M> objectList);
}

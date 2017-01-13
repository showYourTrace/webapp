package com.showyourtrace.object;

import java.util.Collection;

public interface EncodeEntity<M, T> {
    T encode(M entity);
    Collection<T> encode(Collection<M> entityCollection);
}

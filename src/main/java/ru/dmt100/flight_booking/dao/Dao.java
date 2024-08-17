package ru.dmt100.flight_booking.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<U, K, I, T1, T2> {
    Optional<T2> save(U u, T1 t1);

    Optional<T2> find(U u, K k);

    List<T2> findAll(U u, I i);

    Optional<T2> update(U u, T1 t1);

    boolean delete(U u, K k);

    boolean deleteList(U u, List<K> k);

}

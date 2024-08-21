package ru.dmt100.flight_booking.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<Long, Integer, K, T, TDto, TResponse> {
    Optional<TResponse> save(Long userId, TDto tDto);

    Optional<TResponse> find(Long userId, K key);

    List<TDto> findAll(Long userId, Integer limit);

    Optional<TResponse> update(Long userId, TDto tDto);

    boolean delete(Long userId, K key);

    boolean deleteList(Long userId, List<K> key);

}

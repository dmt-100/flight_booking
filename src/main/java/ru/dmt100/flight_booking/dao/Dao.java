package ru.dmt100.flight_booking.dao;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Dao<Long, Integer, K, T, TDto, TResponse> {
    Optional<TResponse> save(Long userId, TDto tDto);

    Optional<TResponse> find(Long userId, K key);

    List<TDto> find(Long userId, OffsetDateTime startTime, OffsetDateTime endTime, Integer limit);

    Optional<TResponse> update(Long userId, TDto tDto);

    boolean delete(Long userId, K key);

    Map<K, Boolean> deleteList(Long userId, List<K> key);

}

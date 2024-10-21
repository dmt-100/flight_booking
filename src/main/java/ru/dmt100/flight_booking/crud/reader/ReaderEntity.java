package ru.dmt100.flight_booking.crud.reader;

import java.sql.Connection;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ReaderEntity<K, T, TDto> {
    Optional<T> load(Connection con, K key);

    List<TDto> load(Connection con,
                 OffsetDateTime startTime,
                 OffsetDateTime endTime,
                 Integer limit);
}

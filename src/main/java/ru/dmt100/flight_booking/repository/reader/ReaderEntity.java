package ru.dmt100.flight_booking.repository.reader;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface ReaderEntity<K, T> {
    Optional<T> load(Connection con, K key);
    List<T> loadAll(Connection con, Integer limit);
}

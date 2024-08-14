package ru.dmt100.flight_booking.dao;

import java.sql.Connection;
import java.util.Optional;

public interface Fetcher<T, K> {
    Optional<T> fetch(Connection con, K key);
}

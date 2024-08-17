package ru.dmt100.flight_booking.repository.persistent;

import java.sql.Connection;

public interface PersistentEntity<T> {
    void save(Connection con, T t);
}

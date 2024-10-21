package ru.dmt100.flight_booking.crud.persistent;

import java.sql.Connection;

public interface PersistentEntity<T> {
    void save(Connection con, T t);
}

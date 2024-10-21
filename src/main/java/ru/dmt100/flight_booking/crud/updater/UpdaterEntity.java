package ru.dmt100.flight_booking.crud.updater;

import java.sql.Connection;
import java.util.Optional;

public interface UpdaterEntity<T1, T2> {
    Optional<T2> update(Connection con, T1 t1);
}

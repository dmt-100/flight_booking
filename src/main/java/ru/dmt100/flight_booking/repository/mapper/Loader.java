package ru.dmt100.flight_booking.repository.mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface Loader<T> {
    Optional<T> getOptional(Connection con, ResultSet rs) throws SQLException;
    T get(Connection con, ResultSet rs) throws SQLException;
}

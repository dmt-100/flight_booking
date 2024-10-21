package ru.dmt100.flight_booking.repository.mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Optional;

public interface Loader<T, TDto> {
    Optional<T> getOptional(Connection con, ResultSet rs);
    TDto get(Connection con, ResultSet rs);
}

package ru.dmt100.flight_booking.repository.mapper;

import java.sql.PreparedStatement;

public interface Persistent<T> {
    void insertToDb(PreparedStatement ps, T t);
}

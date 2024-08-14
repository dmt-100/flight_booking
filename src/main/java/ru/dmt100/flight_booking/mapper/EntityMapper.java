package ru.dmt100.flight_booking.mapper;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public interface EntityMapper<T> {
    T map(Connection con, ResultSet rs) throws SQLException;
}

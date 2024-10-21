package ru.dmt100.flight_booking.sql;

@FunctionalInterface
public interface Query {
    String getQuery(String query);
}

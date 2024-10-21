package ru.dmt100.flight_booking.sql.provider;

@FunctionalInterface
public interface QueryProvider {
    QueryProvider getQuery(String query);
}

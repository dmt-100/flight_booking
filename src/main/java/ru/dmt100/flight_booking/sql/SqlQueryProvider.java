package ru.dmt100.flight_booking.sql;

public interface SqlQueryProvider {
    String getQuery(String queryType);
}

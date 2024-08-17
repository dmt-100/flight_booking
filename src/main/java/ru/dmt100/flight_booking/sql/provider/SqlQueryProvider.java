package ru.dmt100.flight_booking.sql.provider;

public interface SqlQueryProvider {
    String getQuery(String queryType);
}

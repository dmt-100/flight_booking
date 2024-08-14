package ru.dmt100.flight_booking.booking.provider;

import ru.dmt100.flight_booking.sql.GeneralSqlQueryProvider;
import ru.dmt100.flight_booking.sql.SqlQueryProvider;

public class BookingSqlQueryProvider implements SqlQueryProvider {
    private final GeneralSqlQueryProvider sqlQuery;

    public BookingSqlQueryProvider(GeneralSqlQueryProvider sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    @Override
    public String getQuery(String queryType) {
        return sqlQuery.getQuery(queryType);
    }


}

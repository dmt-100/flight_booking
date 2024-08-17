package ru.dmt100.flight_booking.statistic.sql.provider;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.dmt100.flight_booking.sql.provider.SqlQueryProvider;
import ru.dmt100.flight_booking.statistic.sql.StatisticSql;

public class StatisticSqlQueryProvider implements SqlQueryProvider {

    private final StatisticSql sqlQuery;

    public StatisticSqlQueryProvider(@Qualifier("statSql")StatisticSql sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    @Override
    public String getQuery(String queryType) {
        switch (queryType) {
            //from bookings
            case "STAT_BOOKING_AMOUNT_BY_DATE":
                return sqlQuery.getBOOKING_AMOUNT_BY_DATE();
            case "STAT_BOOKING_AMOUNT_SUMMARY_BY_WEEK":
                return sqlQuery.getBOOKING_AMOUNT_SUMMARY_BY_WEEK();
            case "STAT_TOTAL_REVENUE_BY_BOOKINGS_BY_AIRPORTS":
                return sqlQuery.getTOTAL_REVENUE_BY_BOOKINGS_BY_AIRPORTS();
            case "STAT_CLASSIFICATION_BY_BOOKINGS":
                return sqlQuery.getCLASSIFICATION_BY_BOOKINGS();

            default:
                throw new IllegalArgumentException("Unknown query type: " + queryType);
        }
    }
}

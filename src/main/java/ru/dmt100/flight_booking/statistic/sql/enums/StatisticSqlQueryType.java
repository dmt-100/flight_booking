package ru.dmt100.flight_booking.statistic.sql.enums;

public enum StatisticSqlQueryType {
    STAT_BOOKING_AMOUNT_BY_DATE("STAT_BOOKING_AMOUNT_BY_DATE"),
    STAT_BOOKING_AMOUNT_SUMMARY_BY_WEEK("STAT_BOOKING_AMOUNT_SUMMARY_BY_WEEK"),
    STAT_TOTAL_REVENUE_BY_BOOKINGS_BY_AIRPORTS("STAT_TOTAL_REVENUE_BY_BOOKINGS_BY_AIRPORTS"),
    STAT_CLASSIFICATION_BY_BOOKINGS("STAT_CLASSIFICATION_BY_BOOKINGS");

    private final String queryKey;

    StatisticSqlQueryType(String queryKey) {
        this.queryKey = queryKey;
    }

    public String getQueryKey() {
        return queryKey;
    }
}

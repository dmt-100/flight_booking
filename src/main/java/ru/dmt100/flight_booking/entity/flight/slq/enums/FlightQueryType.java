package ru.dmt100.flight_booking.entity.flight.slq.enums;

import ru.dmt100.flight_booking.sql.QueryType;

public enum FlightQueryType implements QueryType {
    CHECKING_FLIGHT_ID("CHECKING_FLIGHT_ID"),
    NEW_FLIGHT("NEW_FLIGHT"),
    FLIGHT_BY_FLIGHT_ID("FLIGHT_BY_FLIGHT_ID"),
    ALL_FLIGHTS("ALL_FLIGHTS"),
    ALL_TICKETS_ID_BY_FLIGHT_ID("ALL_TICKETS_ID_BY_FLIGHT_ID"),
    STAT_FLIGHT_COUNT_BY_STATUS("STAT_FLIGHT_COUNT_BY_STATUS"),
    STAT_FLIGHT_COUNT_BY_MONTH("STAT_FLIGHT_COUNT_BY_MONTH"),
    STAT_DELAYED_FLIGHTS_WITH_PASSENGERS("STAT_DELAYED_FLIGHTS_WITH_PASSENGERS"),
    STAT_FLIGHTS_DELAYED_MORE_THAN_TWO_HOURS("STAT_FLIGHTS_DELAYED_MORE_THAN_TWO_HOURS"),
    STAT_MOST_POPULAR_ROUTES("STAT_MOST_POPULAR_ROUTES"),
    STAT_AVG_DELAY_BY_DAY_OF_WEEK("STAT_AVG_DELAY_BY_DAY_OF_WEEK");

    private final String queryKey;

    FlightQueryType(String queryKey) {
        this.queryKey = queryKey;
    }

    @Override
    public String getQuery() {
        return queryKey;
    }

}

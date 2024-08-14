package ru.dmt100.flight_booking.sql;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GeneralSqlQueryProvider implements SqlQueryProvider {
    private final SqlQuery sqlQuery;

    @Override
    public String getQuery(String queryType) {
        switch (queryType) {
            case "BOOKING_BY_BOOK_REF":
                return sqlQuery.getBOOKING_BY_BOOK_REF();

            case "FLIGHT_BY_FLIGHT_ID":
                return sqlQuery.getFLIGHT_BY_FLIGHT_ID();

            case "TICKETS_BY_BOOK_REF":
                return sqlQuery.getTICKETS_BY_BOOK_REF();

            case "BOARDING_PASS_BY_BOARDING_NO":
                return sqlQuery.getBOARDING_PASSES_BY_TICKET_NO();




            case "AIRPORT_BY_CODE":
                return sqlQuery.getAIRPORT_BY_CODE();
            case "AIRCRAFT_BY_CODE":
                return sqlQuery.getAIRCRAFT_BY_CODE();
            default:
                throw new IllegalArgumentException("Unknown query type: " + queryType);
        }
    }

}

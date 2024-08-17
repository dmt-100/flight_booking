package ru.dmt100.flight_booking.sql.provider;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dmt100.flight_booking.sql.SqlQuery;

@Component
@AllArgsConstructor
public class GeneralSqlQueryProvider implements SqlQueryProvider {
    private final SqlQuery sqlQuery;

    @Override
    public String getQuery(String queryType) {
        switch (queryType) {

            case "TICKETS_BY_BOOK_REF":
                return sqlQuery.getTICKETS_BY_BOOK_REF();
            case "DROP_BOARDING_PASSES_FLIGHT_ID_SEAT_NO_UNIQUE":
                return sqlQuery.getDROP_BOARDING_PASSES_FLIGHT_ID_SEAT_NO_UNIQUE();
            case "ADD_BOARDING_PASSES_UNIQUE_CONSTRAINT":
                return sqlQuery.getADD_BOARDING_PASSES_UNIQUE_CONSTRAINT();
            case "DROP_TICKETS_BOOK_REF_FKEY":
                return sqlQuery.getDROP_TICKETS_BOOK_REF_FKEY();
            case "ADD_TICKETS_BOOK_REF_FKEY":
                return sqlQuery.getADD_TICKETS_BOOK_REF_FKEY();
            case "DROP_TICKET_FLIGHTS_TICKET_NO_FKEY":
                return sqlQuery.getDROP_TICKET_FLIGHTS_TICKET_NO_FKEY();
            case "ADD_TICKET_FLIGHTS_TICKET_NO_FKEY":
                return sqlQuery.getADD_TICKET_FLIGHTS_TICKET_NO_FKEY();
            case "DROP_BOARDING_PASSES_TICKET_NO_FKEY":
                return sqlQuery.getDROP_BOARDING_PASSES_TICKET_NO_FKEY();
            case "ADD_BOARDING_PASSES_TICKET_NO_FKEY":
                return sqlQuery.getADD_BOARDING_PASSES_TICKET_NO_FKEY();
            case "CHECKING_BOARDING_PASS":
                return sqlQuery.getCHECKING_BOARDING_PASS();
            case "CHECKING_BOARDING_PASS_NO":
                return sqlQuery.getCHECKING_BOARDING_PASS_NO();
            case "CHECKING_FLIGHT_ID":
                return sqlQuery.getCHECKING_FLIGHT_ID();
            case "CHECKING_TICKET_NO":
                return sqlQuery.getCHECKING_TICKET_NO();
            case "TICKET_NO_FLIGHT_ID_FROM_BOARDING_PASSES":
                return sqlQuery.getTICKET_NO_FLIGHT_ID_FROM_BOARDING_PASSES();
            case "NEW_BOARDING_PASS":
                return sqlQuery.getNEW_BOARDING_PASS();
            case "ALL_BOARDING_PASSES":
                return sqlQuery.getALL_BOARDING_PASSES();
            case "BOARDING_PASS_BY_BOARDING_NO":
                return sqlQuery.getBOARDING_PASS_BY_BOARDING_NO();
            case "UPDATE_BOARDING_PASS":
                return sqlQuery.getUPDATE_BOARDING_PASS();
            case "UPDATE_BOARDING_PASS_SEAT":
                return sqlQuery.getUPDATE_BOARDING_PASS_SEAT();
            case "DELETE_BOARDING_PASS":
                return sqlQuery.getDELETE_BOARDING_PASS();

            case "PASSENGERS_INFO_BY_BOOK_REF":
                return sqlQuery.getPASSENGERS_INFO_BY_BOOK_REF();
            case "PASSENGERS_INFO_BY_FLIGHT_ID":
                return sqlQuery.getPASSENGERS_INFO_BY_FLIGHT_ID();

            case "DELETE_BOARDING_PASSES_BY_BOOK_REF":
                return sqlQuery.getDELETE_BOARDING_PASSES_BY_BOOK_REF();
            case "DELETE_TICKETS_BY_BOOK_REF":
                return sqlQuery.getDELETE_TICKETS_BY_BOOK_REF();

            case "STAT_TOTAL_SPENT_BY_PASSENGER":
                return sqlQuery.getSTAT_TOTAL_SPENT_BY_PASSENGER();

            case "ALL_FLIGHTS":
                return sqlQuery.getALL_FLIGHTS();
            case "FLIGHT_BY_FLIGHT_ID":
                return sqlQuery.getFLIGHT_BY_FLIGHT_ID();
            case "NEW_FLIGHT":
                return sqlQuery.getNEW_FLIGHT();
            case "ALL_TICKETS_ID_BY_FLIGHT_ID":
                return sqlQuery.getALL_TICKETS_ID_BY_FLIGHT_ID();
            case "STAT_FLIGHT_COUNT_BY_STATUS":
                return sqlQuery.getSTAT_FLIGHT_COUNT_BY_STATUS();
            case "STAT_FLIGHT_COUNT_BY_MONTH":
                return sqlQuery.getSTAT_FLIGHT_COUNT_BY_MONTH();
            case "STAT_DELAYED_FLIGHTS_WITH_PASSENGERS":
                return sqlQuery.getSTAT_DELAYED_FLIGHTS_WITH_PASSENGERS();
            case "STAT_FLIGHTS_DELAYED_MORE_THAN_TWO_HOURS":
                return sqlQuery.getSTAT_FLIGHTS_DELAYED_MORE_THAN_TWO_HOURS();
            case "STAT_MOST_POPULAR_ROUTES":
                return sqlQuery.getSTAT_MOST_POPULAR_ROUTES();
            case "STAT_AVG_DELAY_BY_DAY_OF_WEEK":
                return sqlQuery.getSTAT_AVG_DELAY_BY_DAY_OF_WEEK();
            case "NEW_TICKET":
                return sqlQuery.getNEW_TICKET();
            case "ALL_TICKETS":
                return sqlQuery.getALL_TICKETS();
            case "ALL_TICKETS_WITH_LIMIT":
                return sqlQuery.getALL_TICKETS_WITH_LIMIT();
            case "TICKET_BY_TICKET_NO":
                return sqlQuery.getTICKET_BY_TICKET_NO();
            case "UPDATE_TICKET":
                return sqlQuery.getUPDATE_TICKET();
            case "DELETE_TICKET":
                return sqlQuery.getDELETE_TICKET();
            case "COUNT_TICKETS_ON_SCHEDULED_FLIGHTS_BY_TIME_RANGE":
                return sqlQuery.getCOUNT_TICKETS_ON_SCHEDULED_FLIGHTS_BY_TIME_RANGE();
            case "AIRPORT_BY_CODE":
                return sqlQuery.getAIRPORT_BY_CODE();
            case "AIRCRAFT_BY_CODE":
                return sqlQuery.getAIRCRAFT_BY_CODE();
            default:
                throw new IllegalArgumentException("Unknown query type: " + queryType);
        }
    }

}

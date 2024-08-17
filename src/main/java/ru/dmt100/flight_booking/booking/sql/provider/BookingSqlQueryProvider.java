package ru.dmt100.flight_booking.booking.sql.provider;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.dmt100.flight_booking.booking.sql.BookingSql;
import ru.dmt100.flight_booking.sql.provider.SqlQueryProvider;

@AllArgsConstructor
@NoArgsConstructor
public class BookingSqlQueryProvider implements SqlQueryProvider {
    private BookingSql bookingSql;

    @Override
    public String getQuery(String queryType) {

        switch (queryType) {
            case "BOOKING_BY_BOOK_REF":
                return bookingSql.getBOOKING_BY_BOOK_REF();
            case "CHECKING_BOOK_REF":
                return bookingSql.getCHECKING_BOOK_REF();
            case "CHECKING_FLIGHT_ID":
                return bookingSql.getCHECKING_FLIGHT_ID();
                //dao
            case "NEW_BOOKING":
                return bookingSql.getNEW_BOOKING();
            case "BOOKINGS_BY_FLIGHT_ID":
                return bookingSql.getBOOKINGS_BY_FLIGHT_ID();
            case "ALL_BOOKINGS":
                return bookingSql.getALL_BOOKINGS();
            case "UPDATE_BOOKING":
                return bookingSql.getUPDATE_BOOKING();
            case "DELETE_BOOKING_BY_BOOK_REF":
                return bookingSql.getDELETE_BOOKING_BY_BOOK_REF();

            default:
                throw new IllegalArgumentException("Unknown query type: " + queryType);
        }
    }

}

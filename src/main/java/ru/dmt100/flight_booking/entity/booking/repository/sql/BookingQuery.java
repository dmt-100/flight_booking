package ru.dmt100.flight_booking.entity.booking.repository.sql;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class BookingQuery {
    private final String CHECKING_BOOK_REF = """
        select book_ref
        from bookings
        where book_ref = ?
    """;
    private final String CHECKING_FLIGHT_ID = """
        select flight_id
        from flights
        where flight_id = ?
    """;
    private final String NEW_BOOKING = """
        insert into bookings (book_ref, book_date, total_amount)
        values (?, ?, ?)
    """;
    private final String BOOKING_BY_BOOK_REF = """
        select book_ref, book_date, total_amount
        from bookings b
        where b.book_ref = ?
    """;
    private final String BOOKINGS_BY_FLIGHT_ID = """
        select b.book_ref, b.book_date, b.total_amount
        from bookings b
        join tickets t on b.book_ref = t.book_ref
        join ticket_flights tf on t.ticket_no = tf.ticket_no
        where tf.flight_id = ?
    """;
    private final String ALL_BOOKINGS_BY_DATE_RANGE = """
    select book_ref, book_date, total_amount
    from bookings
    where book_date between ? and ?
    order by book_date
    limit ?;
    """;
    private final String ALL_BOOKINGS = """
        select book_ref, book_date, total_amount
        from bookings;
    """;
    private final String UPDATE_BOOKING = """
        update bookings
        set book_date = ?, total_amount = ?
        where book_ref = ?
    """;
    private final String DELETE_BOOKING_BY_BOOK_REF = """
        delete from bookings
        where book_ref = ?
        """;

//    @Override
//    public String getQuery(String query) {
//        return switch (query) {
//            case "CHECKING_BOOK_REF" -> CHECKING_BOOK_REF;
//            case "CHECKING_FLIGHT_ID" -> CHECKING_FLIGHT_ID;
//            case "NEW_BOOKING" -> NEW_BOOKING;
//            case "BOOKING_BY_BOOK_REF" -> BOOKING_BY_BOOK_REF;
//            case "BOOKINGS_BY_FLIGHT_ID" -> BOOKINGS_BY_FLIGHT_ID;
//            case "ALL_BOOKINGS_BY_DATE_RANGE" -> ALL_BOOKINGS_BY_DATE_RANGE;
//            case "ALL_BOOKINGS" -> ALL_BOOKINGS;
//            case "UPDATE_BOOKING" -> UPDATE_BOOKING;
//            case "DELETE_BOOKING_BY_BOOK_REF" -> DELETE_BOOKING_BY_BOOK_REF;
//            default -> throw new IllegalArgumentException("Unknown query: " + query);
//        };
//    }
}

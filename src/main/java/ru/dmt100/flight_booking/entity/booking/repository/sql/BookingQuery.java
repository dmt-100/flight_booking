package ru.dmt100.flight_booking.entity.booking.repository.sql;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import ru.dmt100.flight_booking.sql.QueryType;

@Getter
@Repository("bookingQuery")
public class BookingQuery implements QueryType {
    public static final QueryType CHECKING_BOOK_REF = () -> """
        select book_ref
        from bookings
        where book_ref = ?
    """;
    public static final QueryType CHECKING_FLIGHT_ID = () -> """
        select flight_id
        from flights
        where flight_id = ?
    """;
    public static final QueryType NEW_BOOKING = () -> """
        insert into bookings (book_ref, book_date, total_amount)
        values (?, ?, ?)
    """;
    public static final QueryType BOOKING_BY_BOOK_REF = () -> """
        select book_ref, book_date, total_amount
        from bookings b
        where b.book_ref = ?
    """;
    public static final QueryType BOOKINGS_BY_FLIGHT_ID = () -> """
        select b.book_ref, b.book_date, b.total_amount
        from bookings b
        join tickets t on b.book_ref = t.book_ref
        join ticket_flights tf on t.ticket_no = tf.ticket_no
        where tf.flight_id = ?
    """;
    public static final QueryType ALL_BOOKINGS = () -> """
        select book_ref, book_date, total_amount
        from bookings;
    """;
    public static final QueryType UPDATE_BOOKING = () -> """
        update bookings
        set book_date = ?, total_amount = ?
        where book_ref = ?
    """;
    public static final QueryType DELETE_BOOKING_BY_BOOK_REF = () -> """
            delete from bookings
            where book_ref = ?
            """;


    @Override
    public String getQuery() {
        return null;
    }
}

package ru.dmt100.flight_booking.booking.sql;

import org.springframework.stereotype.Component;

@Component
public class BookingSql {
    // BOOKINGS
    public String getCHECKING_BOOK_REF() {
        return CHECKING_BOOK_REF;
    }

    public String getCHECKING_FLIGHT_ID() {
        return CHECKING_FLIGHT_ID;
    }

    public String getNEW_BOOKING() {
        return NEW_BOOKING;
    }

    public String getBOOKING_BY_BOOK_REF() {
        return BOOKING_BY_BOOK_REF;
    }

    public String getBOOKINGS_BY_FLIGHT_ID() {
        return BOOKINGS_BY_FLIGHT_ID;
    }

    public String getALL_BOOKINGS() {
        return BOOKINGS_ALL;
    }

    public String getUPDATE_BOOKING() {
        return UPDATE_BOOKING;
    }

    public String getDELETE_BOOKING_BY_BOOK_REF() {
        return DELETE_BOOKING_BY_BOOK_REF;
    }


    // ==================================================================================== BOOKINGS
    // checks if a booking with a specific book_ref exists in the bookings table.
    private final String CHECKING_BOOK_REF = """
            select book_ref 
            from bookings
            where book_ref = ?
            """;
    // Check flight by ID
    private final String CHECKING_FLIGHT_ID = """
            select flight_id 
            from flights 
            where flight_id = ?
            """;

    // creates a new booking
    private final String NEW_BOOKING = """
            insert into bookings (book_ref, book_date, total_amount)
            values (?, ?, ?)
            """;

    // booking by book_ref.
    private final String BOOKING_BY_BOOK_REF = """
            select book_ref, book_date, total_amount
            from bookings b
            where b.book_ref = ?
            """;

    // all bookings by flight ID.
    private final String BOOKINGS_BY_FLIGHT_ID = """
            select b.book_ref, b.book_date, b.total_amount
            from bookings b
            join tickets t on b.book_ref = t.book_ref
            join ticket_flights tf on t.ticket_no = tf.ticket_no
            where tf.flight_id = ?
            """;

    // all bookings.
    private final String BOOKINGS_ALL = """
            select book_ref, book_date, total_amount
            from bookings;
            """;

    // updates the booking
    private final String UPDATE_BOOKING = """
            update bookings
            set book_date = ?, total_amount = ?
            where book_ref = ?
            """;


    // deletes by book_ref.
    private final String DELETE_BOOKING_BY_BOOK_REF = """
            delete 
            from bookings
            where book_ref = ?;
            """;


}

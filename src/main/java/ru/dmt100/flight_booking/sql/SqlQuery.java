package ru.dmt100.flight_booking.sql;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SqlQuery {

    private final String DROP_TICKETS_BOOK_REF_FKEY = """
                ALTER TABLE tickets
                DROP CONSTRAINT tickets_book_ref_fkey;
            """;

    private final String ADD_TICKETS_BOOK_REF_FKEY = """
                ALTER TABLE tickets
                ADD CONSTRAINT tickets_book_ref_fkey
                FOREIGN KEY (book_ref)
                REFERENCES bookings(book_ref)
                ON DELETE CASCADE;
            """;

    private final String DROP_TICKET_FLIGHTS_TICKET_NO_FKEY = """
                ALTER TABLE ticket_flights
                DROP CONSTRAINT ticket_flights_ticket_no_fkey;
            """;

    private final String ADD_TICKET_FLIGHTS_TICKET_NO_FKEY = """
                ALTER TABLE ticket_flights
                ADD CONSTRAINT ticket_flights_ticket_no_fkey
                FOREIGN KEY (ticket_no)
                REFERENCES tickets(ticket_no)
                ON DELETE CASCADE;
            """;

    private final String DROP_BOARDING_PASSES_TICKET_NO_FKEY = """
                ALTER TABLE boarding_passes
                DROP CONSTRAINT boarding_passes_ticket_no_fkey;
            """;

    private final String ADD_BOARDING_PASSES_TICKET_NO_FKEY = """
                ALTER TABLE boarding_passes
                ADD CONSTRAINT boarding_passes_ticket_no_fkey
                FOREIGN KEY (ticket_no)
                REFERENCES tickets(ticket_no)
                ON DELETE CASCADE;
            """;

// BOOKINGS

    private final String IS_BOOKING_PRESENT = """
            SELECT book_ref FROM bookings
            WHERE book_ref = ?
            """;

    private final String IS_FLIGHT_PRESENT = """
            SELECT flight_id FROM flights
            WHERE flight_id = ?
            """;


    private final String CREATE_BOOKING = """
            INSERT INTO bookings (book_ref, book_date, total_amount)
            VALUES (?, ?, ?)
            """;

    private final String BOOKING_BY_BOOK_REF = """
            SELECT book_ref, book_date, total_amount
            FROM bookings b
            WHERE b.book_ref = ?
            """;

    private final String PASSENGERS_INFO_BY_BOOK_REF = """
            SELECT t.ticket_no, t.passenger_id, t.passenger_name, t.contact_data
            FROM tickets t
            JOIN bookings b ON t.book_ref = b.book_ref
            WHERE t.book_ref = ?""";

    private final String PASSENGERS_INFO_BY_FLIGHT_ID = """
            SELECT t.book_ref, t.ticket_no, t.passenger_id, t.passenger_name, t.contact_data
            FROM tickets t
            JOIN ticket_flights tf ON t.ticket_no = tf.ticket_no
            JOIN flights f ON tf.flight_id = f.flight_id
            WHERE tf.flight_id = ?
            ORDER BY t.ticket_no""";

    private final String BOOKINGS_BY_FLIGHT_ID = """
            SELECT b.book_ref, b.book_date, b.total_amount
            FROM bookings b
            JOIN tickets t ON b.book_ref = t.book_ref
            JOIN ticket_flights tf ON t.ticket_no = tf.ticket_no
            WHERE tf.flight_id = ?
            """;

    private final String ALL_BOOKINGS = """
            SELECT book_ref, book_date, total_amount
            FROM bookings;
            """;

    private final String UPDATE_BOOKING = """
            UPDATE bookings
            SET book_date = ?, total_amount = ?
            WHERE book_ref = ?
            """;

    private final String DELETE_BOARDING_PASSES_BY_BOOK_REF = """
            DELETE FROM boarding_passes
            WHERE ticket_no IN (
                SELECT ticket_no
                FROM tickets
                WHERE book_ref = ?
            );
            """;

    private final String DELETE_TICKETS_BY_BOOK_REF = """
            DELETE FROM tickets
            WHERE book_ref = ?;
            """;

    private final String DELETE_BOOKING_BY_BOOK_REF = """
            DELETE FROM bookings
            WHERE book_ref = ?;
            """;
// BOOKING Service

    private final String STATISTICS_BOOKING_AMOUNT_BY_DATE = """
            SELECT
            	DATE(b.book_date) AS booking_date,
            	COUNT(b.book_ref) AS total_bookings,
            	SUM(b.total_amount) AS total_revenue,
            	AVG(b.total_amount) AS avg_booking_amount
            FROM bookings b
            GROUP BY booking_date
            ORDER BY total_revenue DESC;
            """;

    private final String STATISTICS_BOOKING_AMOUNT_SUMMARY_BY_WEEK = """
            SELECT
                DATE_TRUNC('week', b.book_date) AS booking_week,
                EXTRACT(WEEK FROM b.book_date) AS week_of_year,
                COUNT(b.book_ref) AS total_bookings,
                SUM(b.total_amount) AS total_revenue,
                ROUND(AVG(b.total_amount)) AS avg_booking_amount
            FROM bookings b
            GROUP BY booking_week, week_of_year
            ORDER BY total_revenue DESC;
            """;


// TICKETS

    private final String IS_TICKET_PRESENT = """
            SELECT ticket_no FROM tickets WHERE ticket_no = ?
            """;

    private final String NEW_TICKET = """
            INSERT INTO tickets (
            ticket_no,
            book_ref,
            passenger_id,
            passenger_name,
            contact_data)
            VALUES (?, ?, ?, ?, ?)
            """;

    private final String ALL_TICKETS = """
            SELECT
                ticket_no,
                book_ref,
                passenger_id,
                passenger_name,
                contact_data
            FROM tickets
            """;

    private final String TICKETS_BY_BOOK_REF =
            ALL_TICKETS +
                    " WHERE book_ref = ?";

    private final String TICKET_BY_TICKET_NO =
            ALL_TICKETS + """
                    WHERE ticket_no = ?
                    """;

    private final String UPDATE_TICKET = """
            UPDATE tickets
            SET book_ref = ?, passenger_id = ?, passenger_name = ?, contact_data  = ?
            WHERE ticket_no = ?
            """;

    private final String DELETE_TICKET = """
            DELETE FROM tickets WHERE ticket_no = ?
            """;


}










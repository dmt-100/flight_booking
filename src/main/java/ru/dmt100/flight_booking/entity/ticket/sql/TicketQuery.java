package ru.dmt100.flight_booking.entity.ticket.sql;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component("ticketQuery")
public class TicketQuery {

    private final String CHECKING_TICKET_NO = """
        select ticket_no
        from tickets
        where ticket_no = ?
        """;

    private final String TICKETS_BY_BOOK_REF = """
        select
        ticket_no,
        book_ref,
        passenger_id,
        passenger_name,
        contact_data
        from tickets
        where book_ref = ?
        """;

    private final String TICKETS_NOS_BY_BOOK_REF = """
        select
        ticket_no
        from tickets
        where book_ref = ?
        """;

    private final String NEW_TICKET = """
        insert into tickets (
        ticket_no,
        book_ref,
        passenger_id,
        passenger_name,
        contact_data)
        values (?, ?, ?, ?, ?)
        """;

    private final String ALL_TICKETS = """
        select
        ticket_no,
        book_ref,
        passenger_id,
        passenger_name,
        contact_data
        from tickets
        """;

    private final String ALL_TICKETS_WITH_LIMIT = """
        select
        ticket_no,
        book_ref,
        passenger_id,
        passenger_name,
        contact_data
        from tickets
        limit ?
        """;

    private final String TICKET_BY_TICKET_NO = """
        select
        ticket_no,
        book_ref,
        passenger_id,
        passenger_name,
        contact_data
        from tickets
        where ticket_no = ?
        """;

    private final String UPDATE_TICKET = """
        update tickets
        set book_ref = ?, passenger_id = ?, passenger_name = ?, contact_data  = ?
        where ticket_no = ?
        """;

    private final String DELETE_TICKET = """
        delete from tickets where ticket_no = ?
        """;

    private final String DELETE_TICKETS_BY_BOOK_REF = """
        delete from tickets
        where book_ref = ?;
        """;

    private final String PASSENGERS_INFO_BY_BOOK_REF = """
        select
        t.ticket_no, t.passenger_id, t.passenger_name, t.contact_data
        from tickets t
        join bookings b on t.book_ref = b.book_ref
        where t.book_ref = ?
        """;

    private final String PASSENGERS_INFO_BY_FLIGHT_ID = """
        select
        t.ticket_no, t.book_ref, t.passenger_id, t.passenger_name, t.contact_data
        from tickets t
        join ticket_flights tf on t.ticket_no = tf.ticket_no
        join flights f on tf.flight_id = f.flight_id
        where tf.flight_id = ?
        order by t.ticket_no
        """;

    private final String STAT_TOTAL_SPENT_BY_PASSENGER = """
        select
        t.passenger_id,
        t.passenger_name,
        sum(tf.amount) as total_spent
        from tickets t
        join ticket_flights tf on t.ticket_no = tf.ticket_no
        group by t.passenger_id, t.passenger_name
        order by total_spent desc
        limit ?
        """;

    private final String COUNT_TICKETS_ON_SCHEDULED_FLIGHTS_BY_TIME_RANGE = """
        select
        f.flight_no,
        f.scheduled_departure,
        f.scheduled_arrival,
        f.status,
        count(t.ticket_no) as ticket_count
        from
        tickets t
        join
        ticket_flights tf on t.ticket_no = tf.ticket_no
        join
        flights f on tf.flight_id = f.flight_id
        where
        f.status = 'Scheduled'
        and f.scheduled_departure between ? and ?
        group by
        f.flight_no,
        f.scheduled_departure,
        f.scheduled_arrival,
        f.status
        """;

//    @Override
//    public String getQuery(String query) {
//        return switch (query) {
//            case "CHECKING_TICKET_NO" -> CHECKING_TICKET_NO;
//            case "TICKETS_BY_BOOK_REF" -> TICKETS_BY_BOOK_REF;
//            case "TICKETS_NOS_BY_BOOK_REF" -> TICKETS_NOS_BY_BOOK_REF;
//            case "NEW_TICKET" -> NEW_TICKET;
//            case "ALL_TICKETS" -> ALL_TICKETS;
//            case "ALL_TICKETS_WITH_LIMIT" -> ALL_TICKETS_WITH_LIMIT;
//            case "TICKET_BY_TICKET_NO" -> TICKET_BY_TICKET_NO;
//            case "UPDATE_TICKET" -> UPDATE_TICKET;
//            case "DELETE_TICKET" -> DELETE_TICKET;
//            case "DELETE_TICKETS_BY_BOOK_REF" -> DELETE_TICKETS_BY_BOOK_REF;
//            case "PASSENGERS_INFO_BY_BOOK_REF" -> PASSENGERS_INFO_BY_BOOK_REF;
//            case "PASSENGERS_INFO_BY_FLIGHT_ID" -> PASSENGERS_INFO_BY_FLIGHT_ID;
//            case "STAT_TOTAL_SPENT_BY_PASSENGER" -> STAT_TOTAL_SPENT_BY_PASSENGER;
//            case "COUNT_TICKETS_ON_SCHEDULED_FLIGHTS_BY_TIME_RANGE" -> COUNT_TICKETS_ON_SCHEDULED_FLIGHTS_BY_TIME_RANGE;
//            default -> throw new IllegalArgumentException("Unknown query: " + query);
//        };
//    }
}
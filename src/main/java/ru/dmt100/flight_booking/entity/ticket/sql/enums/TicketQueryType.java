//package ru.dmt100.flight_booking.entity.ticket.sql.enums;
//
//import ru.dmt100.flight_booking.sql.QueryType;
//
//public enum TicketQueryType implements QueryType {
//    CHECKING_TICKET_NO("""
//        select ticket_no
//        from tickets
//        where ticket_no = ?
//    """),
//    TICKETS_BY_BOOK_REF("""
//        select
//            ticket_no,
//            book_ref,
//            passenger_id,
//            passenger_name,
//            contact_data
//        from tickets
//        where book_ref = ?
//    """),
//    NEW_TICKET("""
//        insert into tickets (
//            ticket_no,
//            book_ref,
//            passenger_id,
//            passenger_name,
//            contact_data)
//        values (?, ?, ?, ?, ?)
//    """),
//    ALL_TICKETS("""
//        select
//            ticket_no,
//            book_ref,
//            passenger_id,
//            passenger_name,
//            contact_data
//        from tickets
//    """),
//    ALL_TICKETS_WITH_LIMIT("""
//        select
//            ticket_no,
//            book_ref,
//            passenger_id,
//            passenger_name,
//            contact_data
//        from tickets
//        limit ?
//    """),
//    TICKET_BY_TICKET_NO("""
//        select
//            ticket_no,
//            book_ref,
//            passenger_id,
//            passenger_name,
//            contact_data
//        from tickets
//        where ticket_no = ?
//    """),
//    UPDATE_TICKET("""
//        update tickets
//        set book_ref = ?, passenger_id = ?, passenger_name = ?, contact_data  = ?
//        where ticket_no = ?
//    """),
//    DELETE_TICKET("""
//        delete from tickets where ticket_no = ?
//    """),
//    DELETE_TICKETS_BY_BOOK_REF("""
//        delete from tickets
//        where book_ref = ?;
//    """),
//    PASSENGERS_INFO_BY_BOOK_REF("""
//        select
//            t.ticket_no, t.passenger_id, t.passenger_name, t.contact_data
//        from tickets t
//        join bookings b on t.book_ref = b.book_ref
//        where t.book_ref = ?
//    """),
//    PASSENGERS_INFO_BY_FLIGHT_ID("""
//        select
//            t.ticket_no, t.book_ref, t.passenger_id, t.passenger_name, t.contact_data
//        from tickets t
//        join ticket_flights tf on t.ticket_no = tf.ticket_no
//        join flights f on tf.flight_id = f.flight_id
//        where tf.flight_id = ?
//        order by t.ticket_no
//    """),
//    STAT_TOTAL_SPENT_BY_PASSENGER("""
//        select
//            t.passenger_id,
//            t.passenger_name,
//            sum(tf.amount) as total_spent
//        from tickets t
//        join ticket_flights tf on t.ticket_no = tf.ticket_no
//        group by t.passenger_id, t.passenger_name
//        order by total_spent desc
//        limit ?
//    """),
//    COUNT_TICKETS_ON_SCHEDULED_FLIGHTS_BY_TIME_RANGE("""
//        select
//            f.flight_no,
//            f.scheduled_departure,
//            f.scheduled_arrival,
//            f.status,
//            count(t.ticket_no) as ticket_count
//        from
//            tickets t
//        join
//            ticket_flights tf on t.ticket_no = tf.ticket_no
//        join
//            flights f on tf.flight_id = f.flight_id
//        where
//            f.status = 'Scheduled'
//            and f.scheduled_departure between ? and ?
//        group by
//            f.flight_no,
//            f.scheduled_departure,
//            f.scheduled_arrival,
//            f.status
//    """);
//
//    private final String query;
//
//    TicketQueryType(String query) {
//        this.query = query;
//    }
//
//    @Override
//    public String getQuery() {
//        return query;
//    }
//}
//

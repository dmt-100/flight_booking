package ru.dmt100.flight_booking.sql;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SqlQuery {
    // drops the foreign key constraint on the book_ref column in the tickets table.
    private final String DROP_TICKETS_BOOK_REF_FKEY = """
                alter table tickets
                drop constraint tickets_book_ref_fkey;
            """;

    // adds a foreign key constraint on the book_ref column in the tickets table, referencing the bookings table.
    private final String ADD_TICKETS_BOOK_REF_FKEY = """
                alter table tickets
                add constraint tickets_book_ref_fkey
                foreign key (book_ref)
                references bookings(book_ref)
                on delete cascade;
            """;

    // drops the foreign key constraint on the ticket_no column in the ticket_flights table.
    private final String DROP_TICKET_FLIGHTS_TICKET_NO_FKEY = """
                alter table ticket_flights
                drop constraint ticket_flights_ticket_no_fkey;
            """;

    // adds a foreign key constraint on the ticket_no column in the ticket_flights table, referencing the tickets table.
    private final String ADD_TICKET_FLIGHTS_TICKET_NO_FKEY = """
                alter table ticket_flights
                add constraint ticket_flights_ticket_no_fkey
                foreign key (ticket_no)
                references tickets(ticket_no)
                on delete cascade;
            """;

    // drops the foreign key constraint on the ticket_no column in the boarding_passes table.
    private final String DROP_BOARDING_PASSES_TICKET_NO_FKEY = """
                alter table boarding_passes
                drop constraint boarding_passes_ticket_no_fkey;
            """;

    // adds a foreign key constraint on the ticket_no column in the boarding_passes table, referencing the tickets table.
    private final String ADD_BOARDING_PASSES_TICKET_NO_FKEY = """
                alter table boarding_passes
                add constraint boarding_passes_ticket_no_fkey
                foreign key (ticket_no)
                references tickets(ticket_no)
                on delete cascade;
            """;

    // BOOKINGS
    // checks if a booking with a specific book_ref exists in the bookings table.
    private final String CHECKING_BOOK_REF = """
            select book_ref from bookings
            where book_ref = ?
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

    // passenger information by book_ref
    private final String PASSENGERS_INFO_BY_BOOK_REF = """
            select t.ticket_no, t.passenger_id, t.passenger_name, t.contact_data
            from tickets t
            join bookings b on t.book_ref = b.book_ref
            where t.book_ref = ?""";

    // passenger information by flight ID.
    private final String PASSENGERS_INFO_BY_FLIGHT_ID = """
            select t.ticket_no, t.book_ref, t.passenger_id, t.passenger_name, t.contact_data
            from tickets t
            join ticket_flights tf on t.ticket_no = tf.ticket_no
            join flights f on tf.flight_id = f.flight_id
            where tf.flight_id = ?
            order by t.ticket_no""";

    // all bookings by flight ID.
    private final String BOOKINGS_BY_FLIGHT_ID = """
            select b.book_ref, b.book_date, b.total_amount
            from bookings b
            join tickets t on b.book_ref = t.book_ref
            join ticket_flights tf on t.ticket_no = tf.ticket_no
            where tf.flight_id = ?
            """;

    // all bookings.
    private final String ALL_BOOKINGS = """
            select book_ref, book_date, total_amount
            from bookings;
            """;

    // updates the booking
    private final String UPDATE_BOOKING = """
            update bookings
            set book_date = ?, total_amount = ?
            where book_ref = ?
            """;

    // deletes all boarding passes bybook_ref.
    private final String DELETE_BOARDING_PASSES_BY_BOOK_REF = """
            delete from boarding_passes
            where ticket_no in (
                select ticket_no
                from tickets
                where book_ref = ?
            );
            """;

    // deletes all tickets by book_ref.
    private final String DELETE_TICKETS_BY_BOOK_REF = """
            delete from tickets
            where book_ref = ?;
            """;

    // deletes by book_ref.
    private final String DELETE_BOOKING_BY_BOOK_REF = """
            delete from bookings
            where book_ref = ?;
            """;

    // Booking statistics
    // 1. Booking statistics grouped by date
    private final String STAT_BOOKING_AMOUNT_BY_DATE = """
            select
               date(b.book_date) as booking_date,
               count(b.book_ref) as total_bookings,
               sum(b.total_amount) as total_revenue,
               avg(b.total_amount) as avg_booking_amount
            from bookings b
            group by booking_date
            order by total_revenue desc;
            """;

    // 2. Booking statistics grouped by week
    private final String STAT_BOOKING_AMOUNT_SUMMARY_BY_WEEK = """
            select
                date_trunc('week', b.book_date) as booking_week,
                extract(week from b.book_date) as week_of_year,
                count(b.book_ref) as total_bookings,
                sum(b.total_amount) as total_revenue,
                round(avg(b.total_amount)) as avg_booking_amount
            from bookings b
            group by booking_week, week_of_year
            order by total_revenue desc;
            """;

    // 3. Total amount spent by each passenger
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

    // 4. Number of bookings and total revenue for each flight, including flight information.
    private final String STAT_TOTAL_REVENUE_BY_BOOKINGS_BY_AIRPORTS = """
            select
                f.flight_no,
                f.departure_airport,
                f.arrival_airport,
                count(b.book_ref) as total_bookings,
                sum(b.total_amount) as total_revenue
            from bookings b
            join tickets t on b.book_ref = t.book_ref
            join ticket_flights tf on t.ticket_no = tf.ticket_no
            join flights f on tf.flight_id = f.flight_id
            group by f.flight_no, f.departure_airport, f.arrival_airport
            order by total_bookings desc;
            """;

    // 5. Classification by summary of bookings amount with dates
    private final String STAT_CLASSIFICATION_BY_BOOKINGS = """
            select
               count(book_ref) as book_count,
               case
                  when total_amount > 275000 then 'more then 275000'
                  when total_amount between 200000 and 2750000 then 'from 200000 to 275000'
                  when total_amount between 150000 and 200000 then 'from 150000 to 200000'
                  when total_amount between 100000 and 150000 then 'from 100000 to 150000'
                  when total_amount between 75000 and 100000 then 'from 75000 to 100000'
                  when total_amount between 50000 and 75000 then 'from 50000 to 75000'
                  when total_amount between 40000 and 50000 then 'from 40000 to 50000'
                  when total_amount between 30000 and 40000 then 'from 30000 to 40000'
                  when total_amount between 20000 and 30000 then 'from 20000 to 30000'
                  else 'lower 20000'
               end as cost_category
            from bookings b
            where book_date between ? and ?
            group by cost_category
            order by book_count desc;
            """;

    // TICKETS
    // checks if a ticket exists
    private final String CHECKING_TICKET_NO = """
            select ticket_no from tickets where ticket_no = ?
            """;

    // creates a new ticket
    private final String NEW_TICKET = """
            insert into tickets (
            ticket_no,
            book_ref,
            passenger_id,
            passenger_name,
            contact_data)
            values (?, ?, ?, ?, ?)
            """;

    // all tickets
    private final String ALL_TICKETS = """
            select
                ticket_no,
                book_ref,
                passenger_id,
                passenger_name,
                contact_data
            from tickets
            """;

    // retrieves all tickets by bookRef
    private final String TICKETS_BY_BOOK_REF =
            ALL_TICKETS +
                    " where book_ref = ?";

    // retrieves ticket
    private final String TICKET_BY_TICKET_NO =
            ALL_TICKETS + """
                    where ticket_no = ?
                    """;

    // updates the ticket
    private final String UPDATE_TICKET = """
            update tickets
            set book_ref = ?, passenger_id = ?, passenger_name = ?, contact_data  = ?
            where ticket_no = ?
            """;

    // deletes a ticket
    private final String DELETE_TICKET = """
            delete from tickets where ticket_no = ?
            """;

    // Ticket statistics
    // Counts tickets on scheduled flights by time range
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

    // FLIGHTS
    // Check flight by ID
    private final String CHECKING_FLIGHT_ID = """
            select flight_id from flights where flight_id = ?
            """;

    // Select all flights
    private final String ALL_FLIGHTS = """
            select
                flight_id,
                flight_no,
                scheduled_departure,
                scheduled_arrival,
                departure_airport,
                arrival_airport,
                status,
                aircraft_code,
                actual_departure,
                actual_arrival
            from flights
            """;

    // Select flight by ID
    private final String FLIGHT_BY_FLIGHT_ID =
            ALL_FLIGHTS + """
                 where flight_id = ?
                """;

    // Insert new flight
    private final String NEW_FLIGHT = """
        insert into flights (
            flight_id,
            flight_no,
            scheduled_departure,
            scheduled_arrival,
            departure_airport,
            arrival_airport,
            status,
            aircraft_code,
            actual_departure,
            actual_arrival)
        values (nextval('flight_id_seq'), ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    // Select airport by code
    private final String AIRPORT_BY_CODE = """
            select
                airport_code,
                airport_name ->> 'en' as airport_name,
                city ->> 'en' as city,
                coordinates,
                timezone
            from airports_data
            where airport_code = ?;
            """;

    // Select aircraft by code
    private final String AIRCRAFT_BY_CODE = """
            select
                aircraft_code,
                model ->> 'en' as model,
                range
            from demo.bookings.aircrafts_data
            where aircraft_code = ?;
            """;

    // Flight statistics
    // 1. Number of flights by status
    private final String STAT_FLIGHT_COUNT_BY_STATUS = """
        select status, count(*) as flight_count
        from flights
        group by status;
        """;

    // 2. Number of flights by months
    private final String STAT_FLIGHT_COUNT_BY_MONTH = """
        select date_trunc('month', scheduled_departure) as month,
               count(*) as flight_count
        from flights
        group by date_trunc('month', scheduled_departure)
        order by month;
        """;

    // 3. List of all flights with delayed status and number of passengers
    private final String STAT_DELAYED_FLIGHTS_WITH_PASSENGERS = """
        select f.flight_no, count(tf.ticket_no) as passenger_count
        from flights f
        join ticket_flights tf on f.flight_id = tf.flight_id
        where f.status = 'Delayed'
        group by f.flight_no;
        """;

    // 4. List of flights delayed by more than two hours
    private final String STAT_FLIGHTS_DELAYED_MORE_THAN_TWO_HOURS = """
        select flight_id,
               scheduled_departure,
               actual_departure,
               extract(epoch from (actual_departure - scheduled_departure)) / 3600 as delay_hours
        from flights
        where extract(epoch from (actual_departure - scheduled_departure)) / 3600 > 2;
        """;

    // 5. Most popular routes (by number of flights)
    private final String STAT_MOST_POPULAR_ROUTES = """
        select departure_airport, arrival_airport,
               count(*) as route_count
        from flights
        group by departure_airport, arrival_airport
        order by route_count desc
        limit 10;
        """;

    // 6. Average flight delays by day of the week
    private final String STAT_AVG_DELAY_BY_DAY_OF_WEEK = """
        select to_char(scheduled_departure, 'Day') as day_of_week,
               avg(extract(epoch from (actual_departure - scheduled_departure)) / 3600) as average_delay_hours
        from flights
        where actual_departure is not null
        group by to_char(scheduled_departure, 'Day')
        order by average_delay_hours desc;
        """;
}

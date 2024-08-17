package ru.dmt100.flight_booking.sql;

import org.springframework.stereotype.Component;

@Component
public class SqlQuery {


    public String getDROP_BOARDING_PASSES_FLIGHT_ID_SEAT_NO_UNIQUE() {
        return DROP_BOARDING_PASSES_FLIGHT_ID_SEAT_NO_UNIQUE;
    }

    public String getADD_BOARDING_PASSES_UNIQUE_CONSTRAINT() {
        return ADD_BOARDING_PASSES_UNIQUE_CONSTRAINT;
    }

    public String getDROP_TICKETS_BOOK_REF_FKEY() {
        return DROP_TICKETS_BOOK_REF_FKEY;
    }

    public String getADD_TICKETS_BOOK_REF_FKEY() {
        return ADD_TICKETS_BOOK_REF_FKEY;
    }

    public String getDROP_TICKET_FLIGHTS_TICKET_NO_FKEY() {
        return DROP_TICKET_FLIGHTS_TICKET_NO_FKEY;
    }

    public String getADD_TICKET_FLIGHTS_TICKET_NO_FKEY() {
        return ADD_TICKET_FLIGHTS_TICKET_NO_FKEY;
    }

    public String getDROP_BOARDING_PASSES_TICKET_NO_FKEY() {
        return DROP_BOARDING_PASSES_TICKET_NO_FKEY;
    }

    public String getADD_BOARDING_PASSES_TICKET_NO_FKEY() {
        return ADD_BOARDING_PASSES_TICKET_NO_FKEY;
    }

    public String getCHECKING_BOARDING_PASS() {
        return CHECKING_BOARDING_PASS;
    }

    public String getCHECKING_BOARDING_PASS_NO() {
        return CHECKING_BOARDING_PASS_NO;
    }


    public String getCHECKING_FLIGHT_ID() {
        return CHECKING_FLIGHT_ID;
    }

    public String getCHECKING_TICKET_NO() {
        return CHECKING_TICKET_NO;
    }

    public String getTICKET_NO_FLIGHT_ID_FROM_BOARDING_PASSES() {
        return TICKET_NO_FLIGHT_ID_FROM_BOARDING_PASSES;
    }

    public String getNEW_BOARDING_PASS() {
        return NEW_BOARDING_PASS;
    }

    public String getALL_BOARDING_PASSES() {
        return ALL_BOARDING_PASSES;
    }

    public String getBOARDING_PASS_BY_BOARDING_NO() {
        return BOARDING_PASS_BY_BOARDING_NO;
    }

    public String getUPDATE_BOARDING_PASS() {
        return UPDATE_BOARDING_PASS;
    }

    public String getUPDATE_BOARDING_PASS_SEAT() {
        return UPDATE_BOARDING_PASS_SEAT;
    }

    public String getDELETE_BOARDING_PASS() {
        return DELETE_BOARDING_PASS;
    }



    public String getPASSENGERS_INFO_BY_BOOK_REF() {
        return PASSENGERS_INFO_BY_BOOK_REF;
    }

    public String getPASSENGERS_INFO_BY_FLIGHT_ID() {
        return PASSENGERS_INFO_BY_FLIGHT_ID;
    }


    public String getDELETE_BOARDING_PASSES_BY_BOOK_REF() {
        return DELETE_BOARDING_PASSES_BY_BOOK_REF;
    }

    public String getDELETE_TICKETS_BY_BOOK_REF() {
        return DELETE_TICKETS_BY_BOOK_REF;
    }



    public String getSTAT_TOTAL_SPENT_BY_PASSENGER() {
        return STAT_TOTAL_SPENT_BY_PASSENGER;
    }



    public String getALL_FLIGHTS() {
        return ALL_FLIGHTS;
    }

    public String getFLIGHT_BY_FLIGHT_ID() {
        return FLIGHT_BY_FLIGHT_ID;
    }

    public String getNEW_FLIGHT() {
        return NEW_FLIGHT;
    }

    public String getALL_TICKETS_ID_BY_FLIGHT_ID() {
        return ALL_TICKETS_ID_BY_FLIGHT_ID;
    }

    public String getSTAT_FLIGHT_COUNT_BY_STATUS() {
        return STAT_FLIGHT_COUNT_BY_STATUS;
    }

    public String getSTAT_FLIGHT_COUNT_BY_MONTH() {
        return STAT_FLIGHT_COUNT_BY_MONTH;
    }

    public String getSTAT_DELAYED_FLIGHTS_WITH_PASSENGERS() {
        return STAT_DELAYED_FLIGHTS_WITH_PASSENGERS;
    }

    public String getSTAT_FLIGHTS_DELAYED_MORE_THAN_TWO_HOURS() {
        return STAT_FLIGHTS_DELAYED_MORE_THAN_TWO_HOURS;
    }

    public String getSTAT_MOST_POPULAR_ROUTES() {
        return STAT_MOST_POPULAR_ROUTES;
    }

    public String getSTAT_AVG_DELAY_BY_DAY_OF_WEEK() {
        return STAT_AVG_DELAY_BY_DAY_OF_WEEK;
    }

    public String getNEW_TICKET() {
        return NEW_TICKET;
    }

    public String getALL_TICKETS() {
        return ALL_TICKETS;
    }

    public String getALL_TICKETS_WITH_LIMIT() {
        return ALL_TICKETS_WITH_LIMIT;
    }

    public String getTICKETS_BY_BOOK_REF() {
        return TICKETS_BY_BOOK_REF;
    }

    public String getTICKET_BY_TICKET_NO() {
        return TICKET_BY_TICKET_NO;
    }

    public String getUPDATE_TICKET() {
        return UPDATE_TICKET;
    }

    public String getDELETE_TICKET() {
        return DELETE_TICKET;
    }

    public String getCOUNT_TICKETS_ON_SCHEDULED_FLIGHTS_BY_TIME_RANGE() {
        return COUNT_TICKETS_ON_SCHEDULED_FLIGHTS_BY_TIME_RANGE;
    }

    public String getAIRPORT_BY_CODE() {
        return AIRPORT_BY_CODE;
    }

    public String getAIRCRAFT_BY_CODE() {
        return AIRCRAFT_BY_CODE;
    }

    // drops the unique constraint on flight_id and seat_no in boarding_passes table
    private final String DROP_BOARDING_PASSES_FLIGHT_ID_SEAT_NO_UNIQUE = """
                alter table boarding_passes
                drop constraint boarding_passes_flight_id_seat_no_key;
            """;

    // adds a unique constraint on ticket_no, flight_id, boarding_no, and seat_no in boarding_passes table
    private final String ADD_BOARDING_PASSES_UNIQUE_CONSTRAINT = """
                alter table boarding_passes
                add constraint unique_boarding_passes
                unique (ticket_no, flight_id, boarding_no, seat_no);
            """;

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

    // Re-adds a foreign key constraint on the ticket_no column in the boarding_passes table, referencing the tickets table.
    private final String ADD_BOARDING_PASSES_TICKET_NO_FKEY = """
                alter table boarding_passes
                add constraint boarding_passes_ticket_no_fkey
                foreign key (ticket_no)
                references tickets(ticket_no)
                on delete cascade;
            """;

//=========================================================================================================


    // Validation
    // SQL query for checking the existence of a boarding pass with a composite key
    private final String CHECKING_BOARDING_PASS = """
            select 1 
            from boarding_passes 
            where ticket_no = ? and flight_id = ?;
            """;

    // checks if a boarding pass with a specific ticket number exists in the boarding_passes table.
    private final String CHECKING_BOARDING_PASS_NO = """
            select boarding_no 
            from boarding_passes 
            where ticket_no = ?
            """;


    // Check flight by ID
    private final String CHECKING_FLIGHT_ID = """
            select flight_id 
            from flights 
            where flight_id = ?
            """;

    // ==================================================================================== BOARDING_PASS
    // creates a new boarding pass
    private final String TICKET_NO_FLIGHT_ID_FROM_BOARDING_PASSES = """
            select ticket_no, flight_id from boarding_passes bp
            where ticket_no = ?
            """;
    private final String NEW_BOARDING_PASS = """
            insert into boarding_passes (ticket_no, flight_id, boarding_no, seat_no)
            values (?, ?, ?, ?)
            """;

    // selects all boarding passes
    private final String ALL_BOARDING_PASSES = """
            select ticket_no, flight_id, boarding_no, seat_no 
            from boarding_passes 
            limit ?
            """;

    // selects a boarding pass by ticket number
    private final String BOARDING_PASS_BY_BOARDING_NO = """
            select ticket_no, flight_id, boarding_no, seat_no from boarding_passes
            where ticket_no = ?
            """;

    // updates a boarding pass by ticket number
    private final String UPDATE_BOARDING_PASS = """
            update boarding_passes
            set flight_id = ?, boarding_no = ?, seat_no = ?
            where ticket_no = ?
            """;

    private final String UPDATE_BOARDING_PASS_SEAT = """
                    update boarding_passes
                    set seat_no = ?
                    where flight_id = ? and boarding_no = ?
            """;

    // deletes a boarding pass by ticket number
    private final String DELETE_BOARDING_PASS = """
            delete from boarding_passes where ticket_no = ? and flight_id = ?
            """;



    // ==================================================================================== FLIGHTS
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

    // Flight service
    private final String ALL_TICKETS_ID_BY_FLIGHT_ID = """
            select t.ticket_no from flights f
                    join ticket_flights tf on tf.flight_id = f.flight_id
                    join tickets t on tf.ticket_no = t.ticket_no
            where f.flight_id = ?;
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


    // ==================================================================================== TICKETS
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

    // checks if a ticket exists
    private final String CHECKING_TICKET_NO = """
            select ticket_no from tickets where ticket_no = ?
            """;


    // deletes all tickets by book_ref.
    private final String DELETE_TICKETS_BY_BOOK_REF = """
            delete from tickets
            where book_ref = ?;
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

    // deletes all boarding passes bybook_ref.
    private final String DELETE_BOARDING_PASSES_BY_BOOK_REF = """
            delete from boarding_passes
            where ticket_no in (
                select ticket_no
                from tickets
                where book_ref = ?
            );
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
}

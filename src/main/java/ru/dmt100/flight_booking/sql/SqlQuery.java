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





    // ==================================================================================== FLIGHTS


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


}

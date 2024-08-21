package ru.dmt100.flight_booking.entity.flight.slq.query;

import org.springframework.stereotype.Component;

@Component
public class FlightSqlQuery {

    public String getCHECKING_FLIGHT_ID() {
        return CHECKING_FLIGHT_ID;
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

    // Check flight by ID
    private final String CHECKING_FLIGHT_ID = """
            select flight_id 
            from flights 
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

}

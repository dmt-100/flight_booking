package ru.dmt100.flight_booking.statistic.sql;

import org.springframework.stereotype.Component;

//@Getter
@Component("statisticSql")
public class StatisticSqlQuery {

    public String getTOTAL_REVENUE_BY_BOOKINGS_BY_AIRPORTS() {
        return TOTAL_REVENUE_BY_BOOKINGS_BY_AIRPORTS;
    }

    public String getCLASSIFICATION_BY_BOOKINGS() {
        return CLASSIFICATION_BY_BOOKINGS;
    }

    public String getTOTAL_SPENT_BY_PASSENGER() {
        return TOTAL_SPENT_BY_PASSENGER;
    }

    public String getBOOKING_AMOUNT_BY_DATE() {
        return BOOKING_AMOUNT_BY_DATE;
    }

    public String getBOOKING_AMOUNT_SUMMARY_BY_WEEK() {
        return BOOKING_AMOUNT_SUMMARY_BY_WEEK;
    }


    // Booking service
    // 1. Booking statistics grouped by date
    private final String BOOKING_AMOUNT_BY_DATE = """
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
    private final String BOOKING_AMOUNT_SUMMARY_BY_WEEK = """
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
    private final String TOTAL_SPENT_BY_PASSENGER = """
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
    private final String TOTAL_REVENUE_BY_BOOKINGS_BY_AIRPORTS = """
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
    private final String CLASSIFICATION_BY_BOOKINGS = """
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
}

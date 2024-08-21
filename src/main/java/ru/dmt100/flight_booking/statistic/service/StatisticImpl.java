package ru.dmt100.flight_booking.statistic.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.dmt100.flight_booking.entity.booking.model.stats.*;
import ru.dmt100.flight_booking.statistic.sql.StatisticSqlQuery;
import ru.dmt100.flight_booking.util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service("statisticImpl")
public class StatisticImpl implements Statistic {
    private final StatisticSqlQuery sqlQuery;
//    private final StatisticFetcherImpl bookingFetcher;


    public StatisticImpl(@Qualifier("statisticSql") StatisticSqlQuery sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    @Override
    public List<DailyBookingStats> getDailyBookingStats(Long userId) {
        List<DailyBookingStats> bookingStatByDates = new ArrayList<>();

        try (var con = ConnectionManager.open();
             var stmt = con.prepareStatement(sqlQuery.getBOOKING_AMOUNT_BY_DATE())) {

            var rs = stmt.executeQuery();
            while (rs.next()) {
                LocalDate date = rs.getTimestamp("booking_date").toLocalDateTime().toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH);
                String month = date.format(formatter);
                Long totalBookings = rs.getLong("total_bookings");
                Long totalRevenue = rs.getLong("total_revenue");
                Long avgBookingAmount = rs.getLong("avg_booking_amount");

                bookingStatByDates.add(new DailyBookingStats(
                        date, month, totalBookings, totalRevenue, avgBookingAmount));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookingStatByDates;
    }

    @Override
    public List<WeeklyBookingStats> getWeeklyBookingStats(Long userId) {
        List<WeeklyBookingStats> bookingStatByWeeks = new ArrayList<>();

        try (var con = ConnectionManager.open();
             var stmt = con.prepareStatement(sqlQuery.getBOOKING_AMOUNT_SUMMARY_BY_WEEK())) {

            var rs = stmt.executeQuery();
            while (rs.next()) {
                Integer weekOfTheYear = rs.getInt("week_of_year");
                Long totalBookings = rs.getLong("total_bookings");
                Long totalRevenue = rs.getLong("total_revenue");
                Long avgBookingAmount = rs.getLong("avg_booking_amount");

                bookingStatByWeeks.add(new WeeklyBookingStats(
                        weekOfTheYear, totalBookings, totalRevenue, avgBookingAmount
                ));

                new WeeklyBookingStats(weekOfTheYear, totalBookings, totalRevenue, avgBookingAmount);          }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookingStatByWeeks;
    }

    @Override
    public List<TotalAmountSpentByPassenger> getTotalAmountSpentByPassenger(Long userId, int limit) {
        List<TotalAmountSpentByPassenger> totalAmountSpentByPassengers = new ArrayList<>();

        try (var con = ConnectionManager.open();
             var stmt = con.prepareStatement(sqlQuery.getTOTAL_SPENT_BY_PASSENGER())) {

            stmt.setInt(1, limit);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                Long passengerId = Long.valueOf(rs.getString("passenger_id").replace(" ", ""));
                String passengerName = rs.getString("passenger_name");
                BigDecimal totalSpent = rs.getBigDecimal("total_spent");

                totalAmountSpentByPassengers.add(new TotalAmountSpentByPassenger(passengerId, passengerName, totalSpent));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalAmountSpentByPassengers;
    }

    @Override
    public List<RevenueByBookingsByAirport> getTotalRevenueByBookingsByAirport(Long userId) {
        List<RevenueByBookingsByAirport> infos = new ArrayList<>();

        try (var con = ConnectionManager.open();
             var stmt = con.prepareStatement(sqlQuery.getTOTAL_REVENUE_BY_BOOKINGS_BY_AIRPORTS())) {

            var rs = stmt.executeQuery();
            while (rs.next()) {
                String flightNo = rs.getString("flight_no");
                String departureAirport = rs.getString("departure_airport");
                String arrivalAirport = rs.getString("arrival_airport");
                Long totalBookings = rs.getLong("total_bookings");
                BigDecimal totalRevenue = rs.getBigDecimal("total_revenue");

                infos.add(new RevenueByBookingsByAirport(
                        flightNo, departureAirport, arrivalAirport, totalBookings, totalRevenue));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return infos;
    }

    @Override
    public List<SummaryBookCountWithClassification> getSummaryClassification(Long userId, LocalDate startDate, LocalDate endDate) {
        List<SummaryBookCountWithClassification> infos = new ArrayList<>();
        SummaryBookCountWithClassification info;

        try (var con = ConnectionManager.open();
             var stmt = con.prepareStatement(sqlQuery.getCLASSIFICATION_BY_BOOKINGS())) {

            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));

            var rs = stmt.executeQuery();
            while (rs.next()) {
                Integer bookCount = rs.getInt("book_count");
                String costCategory = rs.getString("cost_category");

                info = new SummaryBookCountWithClassification(
                        bookCount, costCategory);
                infos.add(info);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return infos;
    }
}

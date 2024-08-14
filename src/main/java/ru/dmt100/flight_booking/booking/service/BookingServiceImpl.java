package ru.dmt100.flight_booking.booking.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmt100.flight_booking.booking.fetcher.BookingFetcherImpl;
import ru.dmt100.flight_booking.booking.model.dto.BookingLiteDto;
import ru.dmt100.flight_booking.booking.model.dto.stats.*;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.sql.SqlQuery;
import ru.dmt100.flight_booking.util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@AllArgsConstructor
@Service("bookingServiceImpl")
public class BookingServiceImpl implements BookingService {
    private final SqlQuery sqlQuery;
    private final BookingFetcherImpl bookingFetcher;

    public List<BookingLiteDto> findBookingsByFlightId(Long userId, Long flightId) {
        List<BookingLiteDto> BookingLiteDtos = new ArrayList<>();
        Optional<BookingLiteDto> bookingDtoResponse;

        try (var con = ConnectionManager.open();
             var checkStmt = con.prepareStatement(sqlQuery.getCHECKING_FLIGHT_ID());
             var stmt = con.prepareStatement(sqlQuery.getBOOKINGS_BY_FLIGHT_ID())) {

            checkStmt.setLong(1, flightId);
            var checkResultSet = checkStmt.executeQuery();
            if (!checkResultSet.next()) {
                throw new NotFoundException("Flight " + flightId + ", does not exist");
            } else {
                stmt.setLong(1, flightId);
                var rs = stmt.executeQuery();

                while (rs.next()) {
                    bookingDtoResponse = bookingFetcher.fetch(con, rs.getString(1));
                    bookingDtoResponse.ifPresent(BookingLiteDtos::add);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return BookingLiteDtos;
    }

    @Override
    public List<DailyBookingStats> getDailyBookingStats(Long userId) {
        List<DailyBookingStats> bookingStatByDates = new ArrayList<>();

        try (var con = ConnectionManager.open();
        var stmt = con.prepareStatement(sqlQuery.getSTAT_BOOKING_AMOUNT_BY_DATE())) {

            var rs = stmt.executeQuery();
            while (rs.next()) {
                LocalDate date = rs.getTimestamp("booking_date").toLocalDateTime().toLocalDate();
                String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
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
        var stmt = con.prepareStatement(sqlQuery.getSTAT_BOOKING_AMOUNT_SUMMARY_BY_WEEK())) {

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
             var stmt = con.prepareStatement(sqlQuery.getSTAT_TOTAL_SPENT_BY_PASSENGER())) {

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
             var stmt = con.prepareStatement(sqlQuery.getSTAT_TOTAL_REVENUE_BY_BOOKINGS_BY_AIRPORTS())) {

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
             var stmt = con.prepareStatement(sqlQuery.getSTAT_CLASSIFICATION_BY_BOOKINGS())) {

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

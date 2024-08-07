package ru.dmt100.flight_booking.booking.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmt100.flight_booking.booking.dao.BookingDaoImpl;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.booking.model.dto.BookingStatisticsDateDto;
import ru.dmt100.flight_booking.booking.model.dto.BookingStatisticsWeekDto;
import ru.dmt100.flight_booking.booking.model.dto.PassengerInfo;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.sql.SqlQuery;
import ru.dmt100.flight_booking.util.ConnectionManager;

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
    private final BookingDaoImpl bookingDao;
    private final SqlQuery sqlQuery;

    @Override
    public List<PassengerInfo> findPassengersInfoByBookingId(Long userId, String bookRef) {
        List<PassengerInfo> passengerInfos = new ArrayList<>();

        try (var con = ConnectionManager.open();
             var stmt = con.prepareStatement(sqlQuery.getPASSENGERS_INFO_BY_BOOK_REF())) {

            stmt.setString(1, bookRef);
            try (var rs = stmt.executeQuery()) {

                if (!rs.next()) {
                    throw new NotFoundException("Booking " + bookRef + ", does not exist");
                }
                while (rs.next()) {
                    Long ticketNo = rs.getLong("ticket_no");
                    String passengerId = rs.getString("passenger_id");
                    String passengerName = rs.getString("passenger_name");
                    String contactData = rs.getString("contact_data");

                    PassengerInfo info = new PassengerInfo(ticketNo, passengerId, passengerName, contactData);

                    passengerInfos.add(info);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return passengerInfos;
    }

    @Override
    public List<PassengerInfo> findPassengersInfoByFlightId(Long userId, Long flightId) {
        List<PassengerInfo> passengerInfos = new ArrayList<>();

        try (var con = ConnectionManager.open();
             var stmt = con.prepareStatement(sqlQuery.getPASSENGERS_INFO_BY_FLIGHT_ID())) {

            stmt.setLong(1, flightId);
            try (var rs = stmt.executeQuery()) {

                if (!rs.next()) {
                    throw new NotFoundException("Flight " + flightId + ", does not exist");
                }
                while (rs.next()) {
                    String bookRef = rs.getString("book_ref");
                    Long ticketNo = rs.getLong("ticket_no");
                    String passengerId = rs.getString("passenger_id");
                    String passengerName = rs.getString("passenger_name");
                    String contactData = rs.getString("contact_data");

                    PassengerInfo info = new PassengerInfo(bookRef, ticketNo, passengerId, passengerName, contactData);

                    passengerInfos.add(info);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return passengerInfos;
    }




    @Override
    public List<BookingDtoResponse> getBookingsByFlightId(Long userId, Long flightId) {
        List<BookingDtoResponse> bookingDtoResponses = new ArrayList<>();
        Optional<BookingDtoResponse> bookingDtoResponse;

        try (var con = ConnectionManager.open();
             var checkStmt = con.prepareStatement(sqlQuery.getIS_FLIGHT_PRESENT());
             var stmt = con.prepareStatement(sqlQuery.getBOOKINGS_BY_FLIGHT_ID())) {

            checkStmt.setLong(1, flightId);
            var checkResultSet = checkStmt.executeQuery();
            if (!checkResultSet.next()) {
                throw new NotFoundException("Flight " + flightId + ", does not exist");
            } else {
                stmt.setLong(1, flightId);
                var rs = stmt.executeQuery();

                while (rs.next()) {
                    bookingDtoResponse = bookingDao.find(userId, rs.getString(1));
                    bookingDtoResponse.ifPresent(bookingDtoResponses::add);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookingDtoResponses;
    }

    @Override
    public List<PassengerInfo> getAllBookingsByDate(Long userId) {
        List<PassengerInfo> passengerInfos = new ArrayList<>();

        try (var con = ConnectionManager.open();
             var stmt = con.prepareStatement(sqlQuery.getPASSENGERS_INFO_BY_BOOK_REF())) {

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Long ticketNo = rs.getLong("ticket_no");
                    String passengerId = rs.getString("passenger_id");
                    String passengerName = rs.getString("passenger_name");
                    String contactData = rs.getString("contact_data");

                    PassengerInfo info = new PassengerInfo(ticketNo, passengerId, passengerName, contactData);

                    passengerInfos.add(info);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return passengerInfos;
    }

    @Override
    public List<BookingStatisticsDateDto> getStats_RevenueByDate(Long userId) {
        List<BookingStatisticsDateDto> bookingStatByDates = new ArrayList<>();
        BookingStatisticsDateDto bsd;

        try (var con = ConnectionManager.open();
        var stmt = con.prepareStatement(sqlQuery.getSTATISTICS_BOOKING_AMOUNT_BY_DATE())) {

            var rs = stmt.executeQuery();
            while (rs.next()) {
                LocalDate date = rs.getTimestamp("booking_date").toLocalDateTime().toLocalDate();
                String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                Long totalBookings = rs.getLong("total_bookings");
                Long totalRevenue = rs.getLong("total_revenue");
                Long avgBookingAmount = rs.getLong("avg_booking_amount");

                bsd = new BookingStatisticsDateDto(date, month, totalBookings, totalRevenue, avgBookingAmount);

                bookingStatByDates.add(bsd);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookingStatByDates;
    }

    @Override
    public List<BookingStatisticsWeekDto> getStats_RevenueByWeek(Long userId) {
        List<BookingStatisticsWeekDto> bookingStatByWeeks = new ArrayList<>();
        BookingStatisticsWeekDto bsw;

        try (var con = ConnectionManager.open();
        var stmt = con.prepareStatement(sqlQuery.getSTATISTICS_BOOKING_AMOUNT_SUMMARY_BY_WEEK())) {

            var rs = stmt.executeQuery();
            while (rs.next()) {
                Integer weekOfTheYear = rs.getInt("week_of_year");
                Long totalBookings = rs.getLong("total_bookings");
                Long totalRevenue = rs.getLong("total_revenue");
                Long avgBookingAmount = rs.getLong("avg_booking_amount");

                bsw = new BookingStatisticsWeekDto(weekOfTheYear, totalBookings, totalRevenue, avgBookingAmount);
                bookingStatByWeeks.add(bsw);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bookingStatByWeeks;
    }
}

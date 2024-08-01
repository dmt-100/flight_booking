package ru.dmt100.flight_booking.booking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmt100.flight_booking.booking.model.Booking;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.booking.model.dto.PassengerInfo;
import ru.dmt100.flight_booking.exception.AlreadyExistException;
import ru.dmt100.flight_booking.exception.InsertException;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.ticket.dto.TicketLiteDtoResponse;
import ru.dmt100.flight_booking.util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private static final String BOOKING_PRESENCE = """
            SELECT book_ref FROM bookings WHERE book_ref = ?
            """;

    private static final String FLIGHT_PRESENCE = """
            SELECT flight_id FROM flights WHERE flight_id = ?
            """;

    private static final String TICKETS_BY_BOOKING_ID = """
            SELECT * FROM tickets WHERE book_ref = ?
            """;
    private static final String NEW_BOOKING = """
            INSERT INTO bookings (book_ref, book_date, total_amount) VALUES (?, ?, ?) 
            """;

    private static final String BOOKING_BY_BOOK_REF = """
            SELECT * FROM bookings b
            WHERE b.book_ref = ?
            """;

    private static final String PASSENGERS_BY_BOOKING = """
            SELECT t.ticket_no, t.passenger_id, t.passenger_name, t.contact_data
            FROM tickets t
            JOIN bookings b ON t.book_ref = b.book_ref 
            WHERE t.book_ref = ?""";

    private static final String BOOKINGS_BY_FLIGHT_ID = """
            SELECT b.*
            FROM bookings b
            JOIN tickets t ON b.book_ref = t.book_ref
            JOIN ticket_flights tf ON t.ticket_no = tf.ticket_no
            WHERE tf.flight_id = ?
            """;

    private static final String ALL_BOOKINGS = """
            SELECT * FROM bookings;
            """;

    private static final String UPDATE_BOOKING = """
            UPDATE bookings
            SET book_date = ?, total_amount = ?
            WHERE book_ref = ?
            """;


    @Override
    public BookingDtoResponse save(Long userId, Booking booking) {
        var bookRef = booking.getBookRef();

        try (var connection = new ConnectionManager().open();
             var checkStatement = connection.prepareStatement(BOOKING_PRESENCE);
             var statement = connection.prepareStatement(NEW_BOOKING)) {

            checkStatement.setString(1, bookRef);
            var checkResultSet = checkStatement.executeQuery();

            if (checkResultSet.next()) {
                throw new AlreadyExistException("Booking " + bookRef + ", already exist");
            }

            Timestamp timestamp = Timestamp.from(booking.getBookDate().toInstant());

            statement.setString(1, bookRef);
            statement.setTimestamp(2, timestamp);
            statement.setBigDecimal(3, booking.getTotalAmount());
            int row = statement.executeUpdate();
            if (row == 0) {
                throw new InsertException("Failed to insert a booking.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return findBookingById(userId, booking.getBookRef());
    }

    @Override
    public BookingDtoResponse findBookingById(Long userId, String bookRef) {
        BookingDtoResponse booking = null;

        try (var connection = new ConnectionManager().open()) {
            if (isBookingExist(connection, bookRef)) {
                booking = getBooking(true, bookRef);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booking;
    }

    @Override
    public List<PassengerInfo> findPassengersInfoByBookingId(Long userId, String bookRef) {
        List<PassengerInfo> passengerInfos = new ArrayList<>();

        try (var connection = new ConnectionManager().open();
             var statement = connection.prepareStatement(PASSENGERS_BY_BOOKING)) {

            if (isBookingExist(connection, bookRef)) {
                statement.setString(1, bookRef);
                try (var rs = statement.executeQuery()) {

                    while (rs.next()) {
                        Long ticketNo = rs.getLong("ticket_no");
                        String passengerId = rs.getString("passenger_id");
                        String passengerName = rs.getString("passenger_name");
                        String contactData = rs.getString("contact_data");

                        PassengerInfo info = new PassengerInfo(ticketNo, passengerId, passengerName, contactData);

                        passengerInfos.add(info);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return passengerInfos;
    }


    @Override
    public List<BookingDtoResponse> findBookingsByFlightId(Long userId, Long flightId, boolean isTickets) {
        long timeStart = System.currentTimeMillis();
        List<BookingDtoResponse> bookingDtoResponses = new ArrayList<>();
        BookingDtoResponse bookingDtoResponse;

        try (var connection = new ConnectionManager().open();
             var checkStatement = connection.prepareStatement(FLIGHT_PRESENCE);
             var statement = connection.prepareStatement(BOOKINGS_BY_FLIGHT_ID)) {

            checkStatement.setLong(1, flightId);
            var checkResultSet = checkStatement.executeQuery();
            if (!checkResultSet.next()) {
                throw new NotFoundException("Flight " + flightId + ", does not exist");
            } else {
                statement.setLong(1, flightId);
                var rs = statement.executeQuery();

                while (rs.next()) {
                    bookingDtoResponse = getBooking(isTickets, rs.getString(1));
                    bookingDtoResponses.add(bookingDtoResponse);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(System.currentTimeMillis() - timeStart);
        return bookingDtoResponses;
    }

    @Override
    public List<BookingDtoResponse> findAll(Long userId, Integer limit) {
        long timeStart = System.currentTimeMillis();
        List<BookingDtoResponse> bookingDtoResponses = new ArrayList<>();
        BookingDtoResponse bookingDtoResponse;

        try (var connection = new ConnectionManager().open();
             var statement = connection.prepareStatement(ALL_BOOKINGS);
             var resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                bookingDtoResponse = new BookingDtoResponse();
                bookingDtoResponse.setBookRef(resultSet.getString("book_ref"));
                bookingDtoResponse.setBookDate(resultSet.getTimestamp("book_date")
                        .toInstant().atZone(ZoneId.systemDefault()));
                bookingDtoResponse.setTotalAmount(resultSet.getBigDecimal("total_amount"));

                bookingDtoResponses.add(bookingDtoResponse);



                if (bookingDtoResponses.size() >= limit)
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(System.currentTimeMillis() - timeStart);
        return bookingDtoResponses;
    }

    @Override
    public BookingDtoResponse update(Long userId, String bookRef, Booking booking) {
        BookingDtoResponse bookingDtoResponse;

        try (var connection = new ConnectionManager().open();
             var checkStatement = connection.prepareStatement(BOOKING_PRESENCE);
             var statement = connection.prepareStatement(UPDATE_BOOKING)) {

            checkStatement.setString(1, bookRef);
            var checkResultSet = checkStatement.executeQuery();
            if (!checkResultSet.next()) {
                throw new NotFoundException("Booking " + bookRef + ", does not exist");
            } else {

                Timestamp timestamp = Timestamp.from(booking.getBookDate().toInstant());

                statement.setTimestamp(1, timestamp);
                statement.setBigDecimal(2, booking.getTotalAmount());
                statement.setString(3, bookRef);
                int row = statement.executeUpdate();
                if (row == 0) {
                    throw new InsertException("Failed to update a booking.");
                } else {
                    bookingDtoResponse  = getBooking(true, bookRef);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookingDtoResponse;
    }

    @Override
    public ResponseEntity<?> delete(Long userId, String bookRef) {
        return null;
    }


    public BookingDtoResponse getBooking(boolean withTickets, String bookRef) {
        BookingDtoResponse booking = new BookingDtoResponse();
        Set<TicketLiteDtoResponse> tickets = new HashSet<>();
        TicketLiteDtoResponse ticket;

        String bookingRef;
        ZonedDateTime zonedDateTime;
        BigDecimal totalAmount;

        try (Connection connection = new ConnectionManager().open();
             var bookingStatement = connection.prepareStatement(BOOKING_BY_BOOK_REF);
             var ticketsStatement = connection.prepareStatement(TICKETS_BY_BOOKING_ID)) {

            bookingStatement.setString(1, bookRef);

            try (var bookingRs = bookingStatement.executeQuery()) {

                // Get bookings
                while (bookingRs.next()) {
                    bookingRef = bookingRs.getString("book_ref");
                    zonedDateTime = bookingRs
                            .getTimestamp("book_date")
                            .toInstant()
                            .atZone(ZoneId.systemDefault());
                    totalAmount = bookingRs.getBigDecimal("total_amount");

                    booking.setBookRef(bookingRef);
                    booking.setBookDate(zonedDateTime);
                    booking.setTotalAmount(totalAmount);
                }
                if (withTickets) { // Get all tickets of this booking if true
                    ticketsStatement.setString(1, bookRef);
                    try (var ticketsRs = ticketsStatement.executeQuery()) {
                        while (ticketsRs.next()) {
                            Long ticketNo = ticketsRs.getLong("ticket_no");
                            Long passengerId = Long.valueOf(ticketsRs.getString("passenger_id")
                                    .replace(" ", ""));
                            String passengerName = ticketsRs.getString("passenger_name");
                            String contactData = ticketsRs.getString("contact_data");

                            ticket = new TicketLiteDtoResponse(
                                    ticketNo,
                                    bookRef,
                                    passengerId,
                                    passengerName,
                                    contactData);

                            tickets.add(ticket);
                        }
                    }
                    booking.setTickets(tickets);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booking;
    }

    public boolean isBookingExist(Connection connection, String bookRef) {
        try (var checkStatement = connection.prepareStatement(BOOKING_PRESENCE)) {

            checkStatement.setString(1, bookRef);
            var checkResultSet = checkStatement.executeQuery();
            if (!checkResultSet.next()) {
                throw new NotFoundException("Booking " + bookRef + ", does not exist");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

}
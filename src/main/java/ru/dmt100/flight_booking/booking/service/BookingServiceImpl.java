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
import ru.dmt100.flight_booking.util.MapConverter;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

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

    private static final String UPDATING_BOOKING = """
            UPDATE bookings
            SET book_date = ?, total_amount = ?
            WHERE book_ref = ?
            """;

    private static final String DELETING_BOOKING = """
         DELETE FROM boarding_passes
         WHERE ticket_no IN (
             SELECT ticket_no
             FROM tickets
             WHERE book_ref = ?
         );
         
         DELETE FROM tickets
         WHERE book_ref = ?;
         
         DELETE FROM bookings
         WHERE book_ref = ?;
            """;

    @Override
    public BookingDtoResponse save(Long userId, Booking booking) {
        var bookRef = booking.getBookRef();

        try (var connection = new ConnectionManager().open();
             var statement = connection.prepareStatement(NEW_BOOKING)) {

            if (isBookingPresence(bookRef)) {
                throw new AlreadyExistException("Booking " + bookRef + ", already exist");
            }

            statement.setString(1, bookRef);
            statement.setTimestamp(2, Timestamp.from(booking.getBookDate().toInstant()));
            statement.setBigDecimal(3, booking.getTotalAmount());

            var row = statement.executeUpdate();
            if (row == 0) {
                throw new InsertException("Failed to insert a booking.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getBooking(userId, true, booking.getBookRef());
    }

    @Override
    public BookingDtoResponse getBooking(Long userId, boolean withTickets, String bookRef) {
        try (Connection connection = new ConnectionManager().open()) {

            BookingDtoResponse booking = fetchBooking(connection, bookRef);

            if (withTickets) {
                booking.setTickets(getTickets(connection, bookRef));
            }
            return booking;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PassengerInfo> findPassengersInfoByBookingId(Long userId, String bookRef) {
        List<PassengerInfo> passengerInfos = new ArrayList<>();

        try (var connection = new ConnectionManager().open();
             var statement = connection.prepareStatement(PASSENGERS_BY_BOOKING)) {

            statement.setString(1, bookRef);
            try (var rs = statement.executeQuery()) {

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
                    bookingDtoResponse = getBooking(userId, isTickets, rs.getString(1));
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
    public List<BookingDtoResponse> findAll(Long userId, Integer limit, boolean isTickets) {
        long timeStart = System.currentTimeMillis();
        List<BookingDtoResponse> bookingDtoResponses = new ArrayList<>();
        BookingDtoResponse bookingDtoResponse;

        try (var connection = new ConnectionManager().open();
             var statement = connection.prepareStatement(ALL_BOOKINGS);
             var resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                bookingDtoResponse = new BookingDtoResponse();
                String bookRef = resultSet.getString("book_ref");
                bookingDtoResponse.setBookRef(bookRef);
                bookingDtoResponse.setBookDate(resultSet.getTimestamp("book_date")
                        .toInstant().atZone(ZoneId.systemDefault()));
                bookingDtoResponse.setTotalAmount(resultSet.getBigDecimal("total_amount"));

                if (isTickets) {
                    bookingDtoResponse.setTickets(getTickets(connection, bookRef));
                }
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
             var statement = connection.prepareStatement(UPDATING_BOOKING)) {

            if (!isBookingPresence(bookRef)) {
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
                    bookingDtoResponse = getBooking(userId, true, bookRef);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookingDtoResponse;
    }

    @Override
    public ResponseEntity<?> delete(Long userId, String bookRef) {

        try (var connection = new ConnectionManager().open();
             var statement = connection.prepareStatement(DELETING_BOOKING)) {

            if (!isBookingPresence(bookRef)) {
                throw new NotFoundException("Booking " + bookRef + ", does not exist");
            }

            statement.setString(1, bookRef);
            statement.setString(2, bookRef);
            statement.setString(3, bookRef);
            var deletedRowsCount = statement.executeUpdate();
            if (deletedRowsCount == 0) {
                throw new RuntimeException("Failed to delete booking " + bookRef);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.noContent().build();
    }


    private BookingDtoResponse fetchBooking(Connection connection, String bookRef) {
        try (var bookingStatement = connection.prepareStatement(BOOKING_BY_BOOK_REF)) {
            bookingStatement.setString(1, bookRef);
            try (var rs = bookingStatement.executeQuery()) {

                if (!rs.next()) {
                    throw new NotFoundException("Booking " + bookRef + ", does not exist");
                } else {
                    String bookingRef = rs.getString("book_ref");
                    ZonedDateTime zonedDateTime = rs
                            .getTimestamp("book_date")
                            .toInstant()
                            .atZone(ZoneId.systemDefault());
                    BigDecimal totalAmount = rs.getBigDecimal("total_amount");
                    return new BookingDtoResponse(bookingRef, zonedDateTime, totalAmount, new HashSet<>());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<TicketLiteDtoResponse> getTickets(Connection connection, String bookRef) {

        Set<TicketLiteDtoResponse> tickets = new HashSet<>();
        TicketLiteDtoResponse ticket;

        try (var statement = connection.prepareStatement(TICKETS_BY_BOOKING_ID)) {
            statement.setString(1, bookRef);
            var rs = statement.executeQuery();
            while (rs.next()) {
                String ticketNo = rs.getString("ticket_no");
                Long passengerId = Long.valueOf(rs.getString("passenger_id")
                        .replace(" ", ""));
                String passengerName = rs.getString("passenger_name");
                String contactDataString = rs.getString("contact_data");
                Map<String, String> contactData = new MapConverter().convertToEntityAttribute(contactDataString);
                ticket = new TicketLiteDtoResponse(
                        ticketNo,
                        bookRef,
                        passengerId,
                        passengerName,
                        contactData);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    private boolean isBookingPresence(String bookRef) {
        try (var connection = new ConnectionManager().open();
             var statement = connection.prepareStatement(BOOKING_PRESENCE)) {

            statement.setString(1, bookRef);
            var rs = statement.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
package ru.dmt100.flight_booking.booking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmt100.flight_booking.booking.model.Booking;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.booking.model.dto.BookingLiteDtoResponse;
import ru.dmt100.flight_booking.booking.model.dto.PassengerInfo;
import ru.dmt100.flight_booking.exception.AlreadyExistException;
import ru.dmt100.flight_booking.exception.SaveException;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.exception.UpdateException;
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

    private static final String DELETE_BOARDING_PASSES = """
            DELETE FROM boarding_passes
            WHERE ticket_no IN (
                SELECT ticket_no
                FROM tickets
                WHERE book_ref = ?
            );
            """;

    private static final String DELETE_TICKETS = """
            DELETE FROM tickets
            WHERE book_ref = ?;
            """;

    private static final String DELETE_BOOKINGS = """
            DELETE FROM bookings
            WHERE book_ref = ?;
            """;

    @Override
    public BookingDtoResponse save(Long userId, Booking booking) {
        BookingDtoResponse bookingDtoResponse;
        var bookRef = booking.getBookRef();

        try (var con = ConnectionManager.get();
             var stmt = con.prepareStatement(NEW_BOOKING)) {

            if (isBookingPresence(con, bookRef)) {
                throw new AlreadyExistException("Booking " + bookRef + ", already exist");
            }

            stmt.setString(1, bookRef);
            stmt.setTimestamp(2, Timestamp.from(booking.getBookDate().toInstant()));
            stmt.setBigDecimal(3, booking.getTotalAmount());

            var row = stmt.executeUpdate();
            if (row == 0) {
                throw new SaveException("Failed to insert a booking.");
            }
            bookingDtoResponse = fetchBooking(con, booking.getBookRef());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookingDtoResponse;
    }

    public BookingDtoResponse get(Long userId, boolean withTickets, String bookRef) {
        BookingDtoResponse booking;

        try (Connection con = ConnectionManager.get()) {
            booking = fetchBooking(con, bookRef);

            if (withTickets) {
                booking.setTickets(getTickets(con, bookRef));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booking;
    }

    @Override
    public List<PassengerInfo> findPassengersInfoByBookingId(Long userId, String bookRef) {
        List<PassengerInfo> passengerInfos = new ArrayList<>();

        try (var con = ConnectionManager.get();
             var stmt = con.prepareStatement(PASSENGERS_BY_BOOKING)) {

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
    public List<BookingDtoResponse> findBookingsByFlightId(Long userId, Long flightId, boolean isTickets) {
        long timeStart = System.currentTimeMillis();
        List<BookingDtoResponse> bookingDtoResponses = new ArrayList<>();
        BookingDtoResponse bookingDtoResponse;

        try (var con = ConnectionManager.get();
             var checkStmt = con.prepareStatement(FLIGHT_PRESENCE);
             var stmt = con.prepareStatement(BOOKINGS_BY_FLIGHT_ID)) {

            checkStmt.setLong(1, flightId);
            var checkResultSet = checkStmt.executeQuery();
            if (!checkResultSet.next()) {
                throw new NotFoundException("Flight " + flightId + ", does not exist");
            } else {
                stmt.setLong(1, flightId);
                var rs = stmt.executeQuery();

                while (rs.next()) {
                    bookingDtoResponse = get(userId, isTickets, rs.getString(1));
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
    public List<BookingLiteDtoResponse> findAll(Long userId) {
        long timeStart = System.currentTimeMillis();
        List<BookingLiteDtoResponse> bookings = new ArrayList<>();
        BookingLiteDtoResponse booking;

        try (var con = ConnectionManager.get();
             var stmt = con.prepareStatement(ALL_BOOKINGS);
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                booking = new BookingLiteDtoResponse();
                String bookRef = rs.getString("book_ref");
                booking.setBookRef(bookRef);
                booking.setBookDate(rs.getTimestamp("book_date")
                        .toInstant().atZone(ZoneId.systemDefault()));
                booking.setTotalAmount(rs.getBigDecimal("total_amount"));

                bookings.add(booking);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(System.currentTimeMillis() - timeStart);
        return bookings;
    }

    @Override
    public BookingDtoResponse update(Long userId, String bookRef, Booking booking) {
        BookingDtoResponse bookingDtoResponse;

        try (var con = ConnectionManager.get();
             var stmt = con.prepareStatement(UPDATING_BOOKING)) {

            if (!isBookingPresence(con, bookRef)) {
                throw new NotFoundException("Booking " + bookRef + ", does not exist");
            } else {

                Timestamp timestamp = Timestamp.from(booking.getBookDate().toInstant());

                stmt.setTimestamp(1, timestamp);
                stmt.setBigDecimal(2, booking.getTotalAmount());
                stmt.setString(3, bookRef);
                int row = stmt.executeUpdate();
                if (row == 0) {
                    throw new UpdateException("Failed to update a booking.");
                } else {
                    bookingDtoResponse = get(userId, true, bookRef);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookingDtoResponse;
    }

    @Override
    public ResponseEntity<?> delete(Long userId, String bookRef) {
        try (var con = ConnectionManager.get();
             var stmt1 = con.prepareStatement(DELETE_BOARDING_PASSES);
             var stmt2 = con.prepareStatement(DELETE_TICKETS);
             var stmt3 = con.prepareStatement(DELETE_BOOKINGS)) {

            if (!isBookingPresence(con, bookRef)) {
                throw new NotFoundException("Booking " + bookRef + ", does not exist");
            }

            stmt1.setString(1, bookRef);
            int deletedBoardingPasses = stmt1.executeUpdate();

            stmt2.setString(1, bookRef);
            int deletedTickets = stmt2.executeUpdate();

            stmt3.setString(1, bookRef);
            int deletedBookings = stmt3.executeUpdate();

            if (deletedBoardingPasses == 0 && deletedTickets == 0 && deletedBookings == 0) {
                throw new RuntimeException("Failed to delete booking " + bookRef);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.noContent().build();
    }


    private BookingDtoResponse fetchBooking(Connection con, String bookRef) {
        try (var stmt = con.prepareStatement(BOOKING_BY_BOOK_REF)) {

            stmt.setString(1, bookRef);

            try (var rs = stmt.executeQuery()) {

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

    private Set<TicketLiteDtoResponse> getTickets(Connection con, String bookRef) {

        Set<TicketLiteDtoResponse> tickets = new HashSet<>();
        TicketLiteDtoResponse ticket;

        try (var stmt = con.prepareStatement(TICKETS_BY_BOOKING_ID)) {
            stmt.setString(1, bookRef);
            var rs = stmt.executeQuery();
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

    public boolean isBookingPresence(Connection con, String bookRef) {
        try (var stmt = con.prepareStatement(BOOKING_PRESENCE)) {

            stmt.setString(1, bookRef);
            var rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
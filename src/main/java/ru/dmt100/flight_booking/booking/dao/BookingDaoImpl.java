package ru.dmt100.flight_booking.booking.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmt100.flight_booking.booking.model.Booking;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.dao.Dao;
import ru.dmt100.flight_booking.exception.*;
import ru.dmt100.flight_booking.ticket.dto.TicketLiteDtoResponse;
import ru.dmt100.flight_booking.util.ConnectionManager;
import ru.dmt100.flight_booking.util.MapConverter;
import ru.dmt100.flight_booking.sql.SqlQuery;

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
@AllArgsConstructor
@Primary
public class BookingDaoImpl implements Dao<Long, String, Booking, BookingDtoResponse> {
    SqlQuery sqlQuery;

    @Override
    public Optional<BookingDtoResponse> save(Long userId, Booking booking) {
        Optional<BookingDtoResponse> bookingDtoResponse;
        var bookRef = booking.getBookRef();

        try (var con = ConnectionManager.open();
             var stmt = con.prepareStatement(sqlQuery.getCREATE_BOOKING())) {

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

    public Optional<BookingDtoResponse> find(Long userId, String bookRef) {
        Optional<BookingDtoResponse> booking;

        try (Connection con = ConnectionManager.open()) {

            booking = Optional.ofNullable(fetchBooking(con, bookRef)
                    .orElseThrow(() -> new NotFoundException("Booking " + bookRef + ", does not exist")));;

            booking.ifPresent(b -> b.setTickets(getTicketsByBookRef(con, bookRef)));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booking;
    }


    @Override
    public List<BookingDtoResponse> findAll(Long userId) {
        long timeStart = System.currentTimeMillis();
        List<BookingDtoResponse> bookings = new ArrayList<>();
        BookingDtoResponse booking;

        try (var con = ConnectionManager.open();
             var stmt = con.prepareStatement(sqlQuery.getALL_BOOKINGS());
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                booking = new BookingDtoResponse();
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
    public Optional<BookingDtoResponse> update(Long userId, Booking booking) {
        Optional<BookingDtoResponse> bookingDtoResponse;
        String bookRef = booking.getBookRef();

        try (var con = ConnectionManager.open();
             var stmt = con.prepareStatement(sqlQuery.getUPDATE_BOOKING())) {

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
                    bookingDtoResponse = find(userId, bookRef);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookingDtoResponse;
    }

    @Override
    public boolean delete(Long userId, String bookRef) {
        try (var con = ConnectionManager.open();
             var stmt1 = con.prepareStatement(sqlQuery.getDELETE_BOARDING_PASSES_BY_BOOK_REF());
             var stmt2 = con.prepareStatement(sqlQuery.getDELETE_TICKETS_BY_BOOK_REF());
             var stmt3 = con.prepareStatement(sqlQuery.getDELETE_BOOKING_BY_BOOK_REF())) {

            if (!isBookingPresence(con, bookRef)) {
                throw new NotFoundException("Booking: " + bookRef + ", does not exist");
            }

            stmt1.setString(1, bookRef);
            int deletedBoardingPasses = stmt1.executeUpdate();

            stmt2.setString(1, bookRef);
            int deletedTickets = stmt2.executeUpdate();

            stmt3.setString(1, bookRef);
            int deletedBookings = stmt3.executeUpdate();

            if (deletedBoardingPasses == 0 && deletedTickets == 0 && deletedBookings == 0) {
                throw new DeleteException("Failed to delete booking: " + bookRef);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean deleteList(Long userId, List<String> bookRefs) {
        for (String bookRef : bookRefs) {
            delete(userId, bookRef);
        }
        return true;
    }

    private Optional<BookingDtoResponse> fetchBooking(Connection con, String bookRef) {
        try (var stmt = con.prepareStatement(sqlQuery.getBOOKING_BY_BOOK_REF())) {

            stmt.setString(1, bookRef);

            try (var rs = stmt.executeQuery()) {

                if (!rs.next()) {
                    throw new NotFoundException("Booking " + bookRef + ", does not exist");
                } else {
                    BookingDtoResponse bookingDtoResponse;
                    String bookingRef = rs.getString("book_ref");
                    ZonedDateTime zonedDateTime = rs
                            .getTimestamp("book_date")
                            .toInstant()
                            .atZone(ZoneId.systemDefault());
                    BigDecimal totalAmount = rs.getBigDecimal("total_amount");
                    bookingDtoResponse = new BookingDtoResponse(
                            bookingRef, zonedDateTime, totalAmount, new HashSet<>());

                    return Optional.of(bookingDtoResponse);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<TicketLiteDtoResponse> getTicketsByBookRef(Connection con, String bookRef) {

        Set<TicketLiteDtoResponse> tickets = new HashSet<>();
        TicketLiteDtoResponse ticket;

        try (var stmt = con.prepareStatement(sqlQuery.getTICKETS_BY_BOOK_REF())) {
            stmt.setQueryTimeout(1);
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
        try (var stmt = con.prepareStatement(sqlQuery.getIS_BOOKING_PRESENT())) {

            stmt.setString(1, bookRef);
            var rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
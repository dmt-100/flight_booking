package ru.dmt100.flight_booking.ticket.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmt100.flight_booking.booking.service.BookingServiceImpl;
import ru.dmt100.flight_booking.ticket.dto.TicketDtoResponse;
import ru.dmt100.flight_booking.ticket.model.Ticket;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TicketServiceImpl implements TicketService {
    public BookingServiceImpl bookingServiceImpl;

    private static final String CHECK_TICKET_EXISTS = """
            SELECT ticket_no FROM tickets WHERE ticket_no = ?
            """;

    private static final String GET_BOOKING_BY_BOOKING_REF = """
            SELECT * FROM bookings
            WHERE book_ref = ?
            """;

    private static final String GET_TICKET_FLIGHT_BY_TICKET_NO = """
            SELECT * FROM ticket_flights 
            WHERE ticket_no = ?
            """;

    private static final String CREATE_TICKET = """
           INSERT INTO tickets (
           ticket_no, 
           book_ref, 
           passenger_id, 
           passenger_name,
           contact_data
           ) VALUES (?, ?, ?, ?, ?) 
           """;
    @Override
    public ResponseEntity<?> save(Long userId, Ticket ticket) {
//        var ticketNo = ticket.getTicketNo().toString();
//        var bookRef = ticket.getBookRef();
//
//        // check for presence in database
//        try(var connection = ConnectionManager.open();
//            var checkStatement = connection.prepareStatement(CHECK_TICKET_EXISTS)) {
//
//            checkStatement.setString(1, ticketNo);
//            var checkResultSet = checkStatement.executeQuery();
//
//            if (checkResultSet.next()) {
//                throw new AlreadyExistException("Ticket " + ticketNo + ", already exist");
//            } else {
//
//                //get booking instance
//                try(var bookingStatement = connection.prepareStatement(GET_BOOKING_BY_BOOKING_REF)) {
//                    bookingStatement.setString(1, bookRef);
//                    var bookingResultSet = bookingStatement.executeQuery();
//
//                    if (bookingResultSet.next()) {
//                        throw new AlreadyExistException("Booking " + bookRef + ", already exist");
//                    } else {
//                        Booking booking = new Booking();
//
//                        String bRef = bookingResultSet.getString("book_ref");
//                        ZonedDateTime zonedDateTime = bookingResultSet
//                                .getTimestamp("book_date")
//                                .toInstant()
//                                .atZone(ZoneId.systemDefault());
//                        BigDecimal totalAmount = bookingResultSet.getBigDecimal("total_amount");
//                        booking.setBookRef(bRef);
//                        booking.setBookDate(zonedDateTime);
//                        booking.setTotalAmount(totalAmount);
//
//
//                        //get ticketFlight instance
//                        try (var ticketFlightStatement =
//                                     connection.prepareStatement(GET_TICKET_FLIGHT_BY_TICKET_NO)) {
//                            ticketFlightStatement.setString(1, ticketNo);
//                            var ticketFlightResultSet = bookingStatement.executeQuery();
//
//                            if (ticketFlightResultSet.next()) {
//                                throw new AlreadyExistException("TicketFlight " + ticketNo + ", already exist");
//                            } else {
//
//                            }
//                        }
//                    }
//
//                }
//
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } ;
//        return ResponseEntity.ok().build();

        return null;
    }

    @Override
    public TicketDtoResponse findTicketById(Long userId, Long ticketNo) {
        return new TicketDtoResponse();
    }
}

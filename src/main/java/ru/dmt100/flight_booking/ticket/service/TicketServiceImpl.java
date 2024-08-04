package ru.dmt100.flight_booking.ticket.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmt100.flight_booking.booking.model.Booking;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.exception.AlreadyExistException;
import ru.dmt100.flight_booking.exception.InsertException;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.ticket.dto.TicketDtoResponse;
import ru.dmt100.flight_booking.ticket.dto.TicketLiteDtoResponse;
import ru.dmt100.flight_booking.ticket.model.Ticket;
import ru.dmt100.flight_booking.util.ConnectionManager;
import ru.dmt100.flight_booking.util.MapConverter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TicketServiceImpl implements TicketService {


    private static final String TICKET_PRESENCE = """
            SELECT ticket_no FROM tickets WHERE ticket_no = ?
            """;

    private static final String NEW_TICKET = """
            INSERT INTO tickets (
            ticket_no, 
            book_ref, 
            passenger_id,
            passenger_name,
            contact_data) 
            VALUES (?, ?, ?, ?, ?) 
            """;

    private static final String ALL_TICKETS = """
            SELECT * FROM tickets;
            """;

    private static final String TICKET_BY_TICKET_NO = """
            SELECT * FROM tickets
            WHERE ticket_no = ?
            """;

    @Override
    public TicketLiteDtoResponse save(Long userId, Ticket ticket) {
        var ticketNo = ticket.getTicketNo();
        var bookRef = ticket.getBookRef();
        var passengerId = formatPassengerId(ticket.getPassengerId().toString());
        var passengerName = ticket.getPassengerName();
        var contactData = new MapConverter().convertToDatabaseColumn(ticket.getContactData());

        try (var con = ConnectionManager.get()) {

            if (isTicketPresence(con, ticketNo)) {
                throw new AlreadyExistException("Booking " + bookRef + ", already exist");
            }
            try (var stmt = con.prepareStatement(NEW_TICKET)) {

                stmt.setString(1, ticketNo);
                stmt.setString(2, bookRef);
                stmt.setString(3, passengerId);
                stmt.setString(4, passengerName);
                stmt.setObject(5, contactData, java.sql.Types.OTHER);

                var row = stmt.executeUpdate();
                if (row == 0) {
                    throw new InsertException("Failed to insert a ticket.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getTicketLiteDtoResponse(userId, ticketNo);
    }
        @Override
        public TicketLiteDtoResponse getTicketLiteDtoResponse (Long userId, String ticketNo){
            TicketLiteDtoResponse ticketLiteDtoResponse;

            try (var con = ConnectionManager.get();
                 var stmt = con.prepareStatement(TICKET_BY_TICKET_NO)) {

                stmt.setString(1, ticketNo);
                var rs = stmt.executeQuery();
                if (!rs.next()) {
                    throw new NotFoundException("Ticket " + ticketNo + ", does not exist");
                } else {
                    String bookRef = rs.getString("book_ref");
                    Long passengerId = Long.parseLong(rs.getString("passenger_id")
                            .replaceAll("\\s", ""));
                    String passengerName = rs.getString("passenger_name");
                    String contactDataJson = rs.getString("contact_data");

                    // Converting JSON string to Map
                    Map<String, String> contactData = new MapConverter().convertToEntityAttribute(contactDataJson);

                    ticketLiteDtoResponse = new TicketLiteDtoResponse(
                            ticketNo, bookRef, passengerId, passengerName, contactData);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return ticketLiteDtoResponse;
        }

        @Override
        public List<TicketLiteDtoResponse> findAllTicketsLite (Long userId){
            List<TicketLiteDtoResponse> tickets = new ArrayList<>();
            TicketLiteDtoResponse ticket;
            try (var con = ConnectionManager.get();
                 var stmt = con.prepareStatement(ALL_TICKETS)) {

                var rs = stmt.executeQuery();
                while (rs.next()) {
                    String ticketNo = rs.getString("ticket_no");
                    String bookRef = rs.getString("book_ref");
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

        @Override
        public BookingDtoResponse updateTicketLite (Long userId, String bookRef, Booking booking){
            return null;
        }

        @Override
        public ResponseEntity<?> delete (Long userId, String bookRef){
            return null;
        }


        @Override
        public TicketDtoResponse getTicketDtoResponse (Long userId, Long ticketNo){
            return null;
        }

        @Override
        public TicketDtoResponse getTicketById (Long userId, Long ticketNo){
            return new TicketDtoResponse();
        }

        private boolean isTicketPresence (Connection con, String ticketNo){
            try (var stmt = con.prepareStatement(TICKET_PRESENCE)) {

                stmt.setString(1, ticketNo);
                var rs = stmt.executeQuery();

                return rs.next();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        private String formatPassengerId (String passengerNo){
            if (passengerNo.length() > 4) {
                return passengerNo.substring(0, 4) + " " + passengerNo.substring(4);
            } else {
                return passengerNo;
            }
        }
    }

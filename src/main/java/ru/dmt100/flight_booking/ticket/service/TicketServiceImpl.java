package ru.dmt100.flight_booking.ticket.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmt100.flight_booking.booking.service.BookingServiceImpl;
import ru.dmt100.flight_booking.exception.InsertException;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.ticket.dto.TicketDtoResponse;
import ru.dmt100.flight_booking.ticket.dto.TicketLiteDtoResponse;
import ru.dmt100.flight_booking.ticket.model.Ticket;
import ru.dmt100.flight_booking.util.ConnectionManager;
import ru.dmt100.flight_booking.util.MapConverter;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TicketServiceImpl implements TicketService {

    public BookingServiceImpl bookingServiceImpl;

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

    private static final String TICKET_BY_TICKET_NO = """
            SELECT * FROM tickets
            WHERE ticket_no = ?
            """;

    private static final String GET_TICKET_FLIGHT_BY_TICKET_NO = """
            SELECT * FROM ticket_flights 
            WHERE ticket_no = ?
            """;

    @Override
    public TicketLiteDtoResponse save(Long userId, Ticket ticket) {
        var ticketNo = ticket.getTicketNo();
        var bookRef = ticket.getBookRef();
        var passengerId = formatPassengerId(ticket.getPassengerId().toString());
        var passengerName = ticket.getPassengerName();
        var contactData = new MapConverter().convertToDatabaseColumn(ticket.getContactData());

        try (var connection = new ConnectionManager().open();
             var statement = connection.prepareStatement(NEW_TICKET)) {

            statement.setString(1, ticketNo);
            statement.setString(2, bookRef);
            statement.setString(3, passengerId);
            statement.setString(4, passengerName);
            statement.setObject(5, contactData, java.sql.Types.OTHER);

            var row = statement.executeUpdate();
            if (row == 0) {
                throw new InsertException("Failed to insert a ticket.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getTicketLiteDtoResponse(userId, ticketNo);
    }

    @Override
    public TicketLiteDtoResponse getTicketLiteDtoResponse(Long userId, String ticketNo) {
        TicketLiteDtoResponse ticketLiteDtoResponse;
        try (var connection = new ConnectionManager().open();
             var statement = connection.prepareStatement(TICKET_BY_TICKET_NO)) {

            var ticketNoAsString =  String.format("%013d", ticketNo);
            statement.setString(1, ticketNoAsString);
            var rs = statement.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException("Ticket " + ticketNo + ", does not exist");
            } else {
                String bookRef = rs.getString("book_ref");
                Long passengerId = Long.parseLong(rs.getString("passenger_id")
                        .replaceAll("\\s", ""));
                String passengerName = rs.getString("passenger_name");
                // Извлечение JSON-строки из result set
                String contactDataJson = rs.getString("contact_data");

                // Преобразование JSON-строки в Map
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
    public TicketDtoResponse getTicketDtoResponse(Long userId, Long ticketNo) {
        return null;
    }

    @Override
    public TicketDtoResponse findTicketById(Long userId, Long ticketNo) {
        return new TicketDtoResponse();
    }

    private boolean isTicketPresence(Long ticketNo) {
        try (var connection = new ConnectionManager().open();
             var statement = connection.prepareStatement(TICKET_PRESENCE);
             var rs = statement.executeQuery()) {

            statement.setLong(1, ticketNo);
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String formatPassengerId(String passengerNo) {
        if (passengerNo.length() > 4) {
            return passengerNo.substring(0, 4) + " " + passengerNo.substring(4);
        } else {
            return passengerNo;
        }
    }
}

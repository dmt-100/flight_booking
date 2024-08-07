package ru.dmt100.flight_booking.ticket.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmt100.flight_booking.booking.dao.BookingDaoImpl;
import ru.dmt100.flight_booking.exception.AlreadyExistException;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.exception.SaveException;
import ru.dmt100.flight_booking.exception.UpdateException;
import ru.dmt100.flight_booking.sql.SqlQuery;
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
import java.util.stream.IntStream;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TicketServiceImpl implements TicketService {

    private final BookingDaoImpl bookingService;

    private final SqlQuery sqlQuery;

    @Override
    public TicketLiteDtoResponse save(Long userId, Ticket ticket) {
        var ticketNo = String.format("%013d", Long.parseLong(ticket.getTicketNo()));
        var bookRef = ticket.getBookRef();
        var passengerId = formatPassengerId(ticket.getPassengerId().toString());
        var passengerName = ticket.getPassengerName();
        var contactData = new MapConverter().convertToDatabaseColumn(ticket.getContactData());

        try (var con = ConnectionManager.open()) {

            if (isTicketPresence(con, ticketNo)) {
                throw new AlreadyExistException("Ticket " + ticketNo + ", already exist");
            }
            try (var stmt = con.prepareStatement(sqlQuery.getNEW_TICKET())) {

                stmt.setString(1, ticketNo);
                stmt.setString(2, bookRef);
                stmt.setString(3, passengerId);
                stmt.setString(4, passengerName);
                stmt.setObject(5, contactData, java.sql.Types.OTHER);

                var row = stmt.executeUpdate();
                if (row == 0) {
                    throw new SaveException("Failed to save a ticket.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getTicketLiteDtoResponse(userId, ticketNo);
    }

    private TicketLiteDtoResponse get(Connection con, String ticketNo) {
        TicketLiteDtoResponse ticketLiteDtoResponse;
        try (var stmt = con.prepareStatement(sqlQuery.getTICKET_BY_TICKET_NO())) {

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
    public TicketLiteDtoResponse getTicketLiteDtoResponse(Long userId, String ticketNo) {
        try (var con = ConnectionManager.open()) {

            if (!isTicketPresence(con, ticketNo)) {
                throw new NotFoundException("Ticket " + ticketNo + ", does not exist");
            }
            return get(con, ticketNo);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TicketLiteDtoResponse> findAllTicketsLite(Long userId) {
        List<TicketLiteDtoResponse> tickets = new ArrayList<>();
        TicketLiteDtoResponse ticket;
        try (var con = ConnectionManager.open();
             var stmt = con.prepareStatement(sqlQuery.getALL_TICKETS())) {

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
    public TicketLiteDtoResponse update(Long userId, Ticket ticket) {
        String bookRef = ticket.getBookRef();

        String ticketNo = ticket.getTicketNo();
        try (var con = ConnectionManager.open()) {
            if (!isTicketPresence(con, ticketNo)) {
                throw new NotFoundException("Ticket " + ticketNo + ", does not exist");
            }
            if (!bookingService.isBookingPresence(con, bookRef)) {
                throw new NotFoundException("Booking " + bookRef + ", does not exist");
            }

            try (var stmt = con.prepareStatement(sqlQuery.getUPDATE_TICKET())) {
                stmt.setString(1, bookRef);
                stmt.setString(2, formatPassengerIdToString(ticket.getPassengerId()));
                stmt.setString(3, ticket.getPassengerName());

                String contactData = new MapConverter().convertToDatabaseColumn(ticket.getContactData());
                stmt.setObject(4, contactData, java.sql.Types.OTHER);
                stmt.setString(5, ticketNo);

                var row = stmt.executeUpdate();
                if (row == 0) {
                    throw new UpdateException("Failed to update the ticket.");
                }
                return get(con, ticketNo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> delete(Long userId, String bookRef) {
        return null;
    }

    @Override
    public TicketDtoResponse getTicketDtoResponse(Long userId, Long ticketNo) {
        return null;
    }

    @Override
    public TicketDtoResponse getTicketById(Long userId, Long ticketNo) {
        return new TicketDtoResponse();
    }

    private boolean isTicketPresence(Connection con, String ticketNo) {
        try (var stmt = con.prepareStatement(sqlQuery.getIS_TICKET_PRESENT())) {

            stmt.setString(1, ticketNo);
            var rs = stmt.executeQuery();
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

    private String formatPassengerIdToString(long number) {
        String numStr = String.valueOf(number);
        StringBuilder sb = new StringBuilder();

        IntStream.range(0, numStr.length())
                .mapToObj(i -> i == 3 ? numStr.charAt(i) + " " : String.valueOf(numStr.charAt(i)))
                .forEach(sb::append);

        return sb.toString();
    }
}

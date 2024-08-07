package ru.dmt100.flight_booking.ticket.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmt100.flight_booking.booking.dao.BookingDaoImpl;
import ru.dmt100.flight_booking.dao.Dao;
import ru.dmt100.flight_booking.exception.*;
import ru.dmt100.flight_booking.ticket.dto.TicketLiteDtoResponse;
import ru.dmt100.flight_booking.ticket.model.Ticket;
import ru.dmt100.flight_booking.util.ConnectionManager;
import ru.dmt100.flight_booking.util.MapConverter;
import ru.dmt100.flight_booking.sql.SqlQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Service("ticketDaoImpl")
@AllArgsConstructor
@Transactional(readOnly = true)
public class TicketDaoImpl implements Dao<Long, String, Ticket, TicketLiteDtoResponse> {
    BookingDaoImpl bookingService;

    SqlQuery sqlQuery;

    @Override
    public Optional<TicketLiteDtoResponse> save(Long userId, Ticket ticket) {
        var ticketNo = String.format("%013d", ticket.getTicketNo());  // Добавляем три нуля впереди
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
        return find(userId, ticketNo);
    }

    public Optional<TicketLiteDtoResponse> find(Long userId, String ticketNo) {
        try (var con = ConnectionManager.open()) {

            if (!isTicketPresence(con, ticketNo)) {
                throw new NotFoundException("Ticket " + ticketNo + ", does not exist");
            }
            return get(con, ticketNo);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TicketLiteDtoResponse> findAll(Long userId) {
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
    public Optional<TicketLiteDtoResponse> update(Long userId, Ticket ticket) {
        String bookRef = ticket.getBookRef();

        String ticketNo = String.format("%013d", ticket.getTicketNo());
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
                    throw new UpdateException("Failed to update the ticket: " + ticketNo);
                }
                return get(con, ticketNo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long userId, String ticketNo) {
        try (var con = ConnectionManager.open()) {

            if (!isTicketPresence(con, ticketNo)) {

                throw new NotFoundException("Ticket " + ticketNo + ", does not exist");
            }
            try (var stmt = con.prepareStatement(sqlQuery.getDELETE_TICKET())) {
                stmt.setString(1, ticketNo);
                var rs = stmt.executeUpdate();
                if (rs == 0) {
                    throw new DeleteException("Failed to delete ticket: " + ticketNo);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean deleteList(Long userId, List<String> ticketNos) {
        for (String ticketNo : ticketNos) {
            delete(userId, ticketNo);
        }
        return true;
    }

    private Optional<TicketLiteDtoResponse> get(Connection con, String ticketNo) {
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
        return Optional.of(ticketLiteDtoResponse);
    }

    private boolean isTicketPresence(Connection con, String ticketNo) {
        try (var stmt = con.prepareStatement(sqlQuery.getIS_PRESENT())) {

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


package ru.dmt100.flight_booking.entity.booking.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.dmt100.flight_booking.entity.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.entity.ticket.model.Ticket;
import ru.dmt100.flight_booking.entity.ticket.sql.TicketQuery;
import ru.dmt100.flight_booking.repository.mapper.Loader;
import ru.dmt100.flight_booking.util.MapConverter;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository("bookingMapper")
public class BookingMapper<T, TDto> implements Loader<T, TDto> {

    private final TicketQuery ticketQuery;

    public BookingMapper(@Qualifier("ticketQuery") TicketQuery ticketQuery) {
        this.ticketQuery = ticketQuery;
    }

    @Override
    public Optional<T> getOptional(Connection con, ResultSet rs) {
        return Optional.of(mapWithTicketsNos(con, rs));
    }

    @Override
    public TDto get(Connection con, ResultSet rs) {
        return mapWithoutTicketsNos(con, rs);
    }

    private TDto mapWithoutTicketsNos(Connection con, ResultSet rs) {
        TDto booking;
        try {
            String bookRef = rs.getString("book_ref");
            ZonedDateTime bookDate = rs.getTimestamp("book_date").toInstant().atZone(ZoneId.systemDefault());
            BigDecimal totalAmount = rs.getBigDecimal("total_amount");

            booking = (TDto) new BookingDtoResponse(bookRef, bookDate, totalAmount, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booking;
    }

    private T mapWithTicketsNos(Connection con, ResultSet rs) {
        T booking;
        try {
            String bookRef = rs.getString("book_ref");
            ZonedDateTime bookDate = rs.getTimestamp("book_date").toInstant().atZone(ZoneId.systemDefault());
            BigDecimal totalAmount = rs.getBigDecimal("total_amount");

            Set<Ticket> ticketsNos = getTicketsNosByBookRef(con, bookRef);

            booking = (T) new BookingDtoResponse(bookRef, bookDate, totalAmount, ticketsNos);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booking;
    }


    @NotNull
    private Set<Ticket> getTicketsNosByBookRef(Connection con, String bookRef) {
        Set<Ticket> ticketNos = new HashSet<>();
        String query = ticketQuery.getTICKETS_BY_BOOK_REF();
//        String query = ticketQuery.getTICKETS_NOS_BY_BOOK_REF();
        try (var stmt = con.prepareStatement(query)) {
            stmt.setString(1, bookRef);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ticket ticket;
                    Long ticketNo = rs.getLong("ticket_no");
                    Long passengerId = Long.parseLong(rs.getString("passenger_id")
                            .replaceAll("\\s", ""));
                    String passengerName = rs.getString("passenger_name");
                    String contactDataJson = rs.getString("contact_data") != null ?
                            rs.getString("contact_data") : null;

                    Map<String, String> contactData = new MapConverter().convertToEntityAttribute(contactDataJson);

                    ticket = new Ticket(
                            ticketNo, bookRef, passengerId, passengerName, contactData);
                    ticketNos.add(ticket);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketNos;
    }
}

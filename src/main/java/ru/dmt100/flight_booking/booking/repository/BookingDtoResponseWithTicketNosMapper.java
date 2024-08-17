package ru.dmt100.flight_booking.booking.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.repository.mapper.Loader;
import ru.dmt100.flight_booking.sql.provider.SqlQueryProvider;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository("bookingDtoResponseMapper")
public class BookingDtoResponseWithTicketNosMapper implements Loader<BookingDtoResponse> {
    private final SqlQueryProvider sqlQuery;

    public BookingDtoResponseWithTicketNosMapper(SqlQueryProvider sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    @Override
    public Optional<BookingDtoResponse> map(Connection con, ResultSet rs) throws SQLException {
        String bookRef = rs.getString("book_ref");
        ZonedDateTime bookDate = rs.getTimestamp("book_date").toInstant().atZone(ZoneId.systemDefault());
        BigDecimal totalAmount = rs.getBigDecimal("total_amount");

        Set<String> ticketsNos = getTicketsNosByBookRef(con, bookRef);

        BookingDtoResponse BookingDtoResponse = new BookingDtoResponse(bookRef, bookDate, totalAmount, ticketsNos);

        return Optional.of(BookingDtoResponse);
    }

    @NotNull
    private Set<String> getTicketsNosByBookRef(Connection con, String bookRef) {
        Set<String> ticketNos = new HashSet<>();
        try (var stmt = con.prepareStatement(sqlQuery.getQuery("TICKETS_BY_BOOK_REF"))) {
            stmt.setString(1, bookRef);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ticketNos.add(rs.getString("ticket_no"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketNos;
    }
}

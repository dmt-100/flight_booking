package ru.dmt100.flight_booking.booking.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dmt100.flight_booking.booking.model.dto.BookingLiteDto;
import ru.dmt100.flight_booking.mapper.EntityMapper;
import ru.dmt100.flight_booking.sql.SqlQueryProvider;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class BookingLiteDtoMapper implements EntityMapper<BookingLiteDto> {
    private final SqlQueryProvider sqlQuery;

    @Override
    public BookingLiteDto map(Connection con, ResultSet rs) throws SQLException {
        String bookRef = rs.getString("book_ref");
        ZonedDateTime bookDate = rs.getTimestamp("book_date").toInstant().atZone(ZoneId.systemDefault());
        BigDecimal totalAmount = rs.getBigDecimal("total_amount");

        Set<String> ticketsNos = getTicketsNosByBookRef(con, bookRef);

        return new BookingLiteDto(bookRef, bookDate, totalAmount, ticketsNos);
    }

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

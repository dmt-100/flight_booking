package ru.dmt100.flight_booking.booking.fetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dmt100.flight_booking.booking.model.dto.BookingLiteDto;
import ru.dmt100.flight_booking.dao.Fetcher;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.fetcher.EntityFetcher;
import ru.dmt100.flight_booking.mapper.EntityMapper;
import ru.dmt100.flight_booking.sql.SqlQueryProvider;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service("bookingFetcher")
public class BookingFetcherImpl extends BookingFetcher implements Fetcher<BookingLiteDto, String> {
    private SqlQueryProvider sqlQuery;

    private EntityFetcher entityFetcher;

//    public BookingFetcherImpl(SqlQueryProvider sqlQuery, EntityFetcher entityFetcher) {
//        this.sqlQuery = sqlQuery;
//        this.entityFetcher = entityFetcher;
//    }

    @Autowired
    public BookingFetcherImpl(
            EntityMapper<BookingLiteDto> mapper,
            SqlQueryProvider sqlQueryProvider,
            SqlQueryProvider sqlQuery) {
        super(mapper, sqlQueryProvider);
        this.sqlQuery = sqlQuery;
    }


    @Override
    public Optional<BookingLiteDto> fetch(Connection con, String bookRef) {
        try (var stmt = con.prepareStatement(sqlQuery.getQuery("BOOKING_BY_BOOK_REF"))) {
            stmt.setString(1, bookRef);
            try (var rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new NotFoundException("Booking " + bookRef + ", does not exist");
                } else {
                    BookingLiteDto bookingDto;

                    String bookingRef = rs.getString("book_ref");
                    ZonedDateTime zonedDateTime = rs.getTimestamp("book_date").toInstant().atZone(ZoneId.systemDefault());
                    BigDecimal totalAmount = rs.getBigDecimal("total_amount");

                    Set<String> tickets = getTicketNosByBookRef(con, bookRef);

                    bookingDto = new BookingLiteDto(bookingRef, zonedDateTime, totalAmount, tickets);

                    return Optional.of(bookingDto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<String> getTicketNosByBookRef(Connection con, String bookref) {
        Set<String> ticketsNos = new HashSet<>();
        try (var stmt = con.prepareStatement(sqlQuery.getQuery("TICKETS_BY_BOOK_REF"))) {
            stmt.setString(1, bookref);
            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ticketsNos.add(rs.getString("ticket_no"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketsNos;
    }

}

package ru.dmt100.flight_booking.booking.dataaccess;

import org.springframework.stereotype.Component;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoRequest;
import ru.dmt100.flight_booking.booking.sql.BookingSql;
import ru.dmt100.flight_booking.repository.persistent.impl.PersistentEntityImpl;
import ru.dmt100.flight_booking.repository.mapper.Persistent;
import ru.dmt100.flight_booking.sql.provider.SqlQueryProvider;

import java.sql.Connection;
import java.sql.SQLException;

@Component("bookingPersistent")
public class BookingPersistent extends PersistentEntityImpl<BookingDtoRequest> {
    private final Persistent<BookingDtoRequest> persistent;
    private final String queryType;

    public BookingPersistent(Persistent persistent,
                             SqlQueryProvider sqlQueryProvider,
                             BookingSql bookingSql) {
        super(persistent, sqlQueryProvider, bookingSql.getNEW_BOOKING());
        this.persistent = persistent;
        this.queryType = bookingSql.getNEW_BOOKING();
    }
    @Override
    public void save(Connection con, BookingDtoRequest bookingDtoRequest) {
        try(var stmt = con.prepareStatement(queryType)) {

            persistent.insertToDb(stmt, bookingDtoRequest);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void mapToStatement(PreparedStatement ps, BookingDto bookingDto) {
//        persistent.mapToStatement(ps, bookingDto);
//    }
}

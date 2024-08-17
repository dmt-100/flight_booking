package ru.dmt100.flight_booking.booking.dataaccess;

import org.springframework.stereotype.Component;
import ru.dmt100.flight_booking.booking.sql.BookingSql;
import ru.dmt100.flight_booking.repository.eraser.impl.EraserEntityImpl;
import ru.dmt100.flight_booking.sql.provider.SqlQueryProvider;

import java.sql.Connection;

@Component("bookingEraser")
public class BookingEraser extends EraserEntityImpl<String> {
    private final String queryType;

    public BookingEraser(SqlQueryProvider sqlQueryProvider,
                         BookingSql bookingSql) {
        super(sqlQueryProvider, bookingSql.getDELETE_BOOKING_BY_BOOK_REF());
        this.queryType = bookingSql.getDELETE_BOOKING_BY_BOOK_REF();
    }

    @Override
    public void delete(Connection con, String key) {
        super.delete(con, key);
    }
}

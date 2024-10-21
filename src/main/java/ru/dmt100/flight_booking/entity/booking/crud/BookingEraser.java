package ru.dmt100.flight_booking.entity.booking.crud;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.dmt100.flight_booking.crud.eraser.impl.EraserEntityImpl;
import ru.dmt100.flight_booking.entity.booking.repository.sql.BookingQuery;

import java.sql.Connection;

@Component("bookingEraser")
public class BookingEraser extends EraserEntityImpl<String> {

    public BookingEraser(@Qualifier("bookingQuery") BookingQuery bookingQuery) {
        super(bookingQuery.getDELETE_BOOKING_BY_BOOK_REF());
    }

    @Override
    public boolean delete(Connection con, String key) {
        return super.delete(con, key);
    }
}

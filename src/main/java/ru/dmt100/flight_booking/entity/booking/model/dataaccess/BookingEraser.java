package ru.dmt100.flight_booking.entity.booking.model.dataaccess;

import org.springframework.stereotype.Component;
import ru.dmt100.flight_booking.entity.booking.repository.sql.BookingQuery;
import ru.dmt100.flight_booking.repository.eraser.impl.EraserEntityImpl;

import java.sql.Connection;

@Component("bookingEraser")
public class BookingEraser extends EraserEntityImpl<String> {

    public BookingEraser() {
        super(BookingQuery.DELETE_BOOKING_BY_BOOK_REF.getQuery());
    }

    @Override
    public void delete(Connection con, String key) {
        super.delete(con, key);
    }
}

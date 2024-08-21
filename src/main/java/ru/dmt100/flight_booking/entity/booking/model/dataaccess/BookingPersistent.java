package ru.dmt100.flight_booking.entity.booking.model.dataaccess;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.dmt100.flight_booking.entity.booking.model.dto.BookingDto;
import ru.dmt100.flight_booking.entity.booking.repository.sql.BookingQuery;
import ru.dmt100.flight_booking.enums.TableType;
import ru.dmt100.flight_booking.repository.mapper.Persistent;
import ru.dmt100.flight_booking.repository.persistent.impl.PersistentEntityImpl;
import ru.dmt100.flight_booking.util.validator.Validator;

import java.sql.Connection;

@Component("bookingPersistent")
public class BookingPersistent extends PersistentEntityImpl<BookingDto> {

    public BookingPersistent(@Qualifier("bookingValidator") Validator validator,
                             Persistent persistent) {
        super(validator, persistent, BookingQuery.NEW_BOOKING.getQuery());
    }

    @Override
    protected TableType getTableType() {
        return TableType.BOOKINGS;
    }

    @Override
    public void save(Connection con, BookingDto bookingDto) {
        super.save(con, bookingDto);
    }

}

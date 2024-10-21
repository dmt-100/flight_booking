package ru.dmt100.flight_booking.entity.booking.crud;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.dmt100.flight_booking.entity.booking.model.dto.BookingDto;
import ru.dmt100.flight_booking.entity.booking.repository.sql.BookingQuery;
import ru.dmt100.flight_booking.enums.TableType;
import ru.dmt100.flight_booking.repository.mapper.Persistent;
import ru.dmt100.flight_booking.crud.persistent.impl.PersistentEntityImpl;
import ru.dmt100.flight_booking.util.validator.Validator;

import java.sql.Connection;

@Component("bookingPersistent")
public class BookingPersistent extends PersistentEntityImpl<BookingDto> {

    public BookingPersistent(@Qualifier("bookingValidator") Validator validator,
                             @Qualifier("bookingInserter") Persistent persistent,
                             @Qualifier("bookingQuery") BookingQuery bookingQuery) {
        super(validator, persistent, bookingQuery.getNEW_BOOKING());
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

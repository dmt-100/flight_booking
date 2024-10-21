package ru.dmt100.flight_booking.entity.booking.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.dmt100.flight_booking.entity.booking.repository.sql.BookingQuery;
import ru.dmt100.flight_booking.enums.TableType;
import ru.dmt100.flight_booking.repository.mapper.Loader;
import ru.dmt100.flight_booking.crud.reader.impl.ReaderEntityImpl;

import java.sql.Connection;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Component("bookingReader")
public class BookingReader<K, T, TDto> extends ReaderEntityImpl<K, T, TDto> {

    @Autowired
    public BookingReader(@Qualifier("bookingMapper") Loader<T, TDto> loader,
                         @Qualifier("bookingQuery") BookingQuery bookingQuery) {
        super(loader,
                bookingQuery.getBOOKING_BY_BOOK_REF(),
                bookingQuery.getALL_BOOKINGS_BY_DATE_RANGE()
        );
    }

    @Override
    protected TableType getTableType() {
        return TableType.BOOKINGS;
    }

    @Override
    public Optional<T> load(Connection con, K bookRef) {
        return super.load(con, bookRef);
    }

    @Override
    public List<TDto> load(Connection con,
                        OffsetDateTime startTime,
                        OffsetDateTime endTime,
                        Integer limit) {
        return super.load(con, startTime, endTime, limit);
    }
}

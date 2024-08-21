package ru.dmt100.flight_booking.entity.booking.model.dataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.dmt100.flight_booking.entity.booking.repository.sql.BookingQuery;
import ru.dmt100.flight_booking.enums.TableType;
import ru.dmt100.flight_booking.repository.mapper.Loader;
import ru.dmt100.flight_booking.repository.reader.impl.ReaderEntityImpl;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@Component("bookingReader")
public class BookingReader<T> extends ReaderEntityImpl<String, T> {


    @Autowired
    public BookingReader(@Qualifier("bookingMapper") Loader<T> loader) {
        super(loader,
                BookingQuery.BOOKING_BY_BOOK_REF.getQuery(),
                BookingQuery.ALL_BOOKINGS.getQuery()
        );
    }

    @Override
    protected TableType getTableType() {
        return TableType.BOOKINGS;
    }

    @Override
    public Optional<T> load(Connection con, String bookRef) {
        return super.load(con, bookRef);
    }

    @Override
    public List<T> loadAll(Connection con, Integer limit){
        return super.loadAll(con, limit);
    }
}

package ru.dmt100.flight_booking.booking.dataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.booking.sql.BookingSql;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.repository.loader.impl.LoaderEntityImpl;
import ru.dmt100.flight_booking.repository.mapper.Loader;
import ru.dmt100.flight_booking.sql.provider.SqlQueryProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@Component("bookingLoaderByKey")
public class BookingLoaderByKey extends LoaderEntityImpl<BookingDtoResponse, String> {
    private final Loader<BookingDtoResponse> loader;
    private final String queryType;

    @Autowired
    public BookingLoaderByKey(Loader<BookingDtoResponse> loader,
                              SqlQueryProvider sqlQueryProvider,
                              BookingSql bookingSql) {
        super(loader, sqlQueryProvider, bookingSql.getBOOKING_BY_BOOK_REF());
        this.loader = loader;
        this.queryType = bookingSql.getBOOKING_BY_BOOK_REF();
    }

    @Override
    public Optional<BookingDtoResponse> load(Connection con, String bookRef) {
        try (var stmt = con.prepareStatement(queryType)) {
            stmt.setString(1, bookRef);

            try (var rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new NotFoundException("Booking " + bookRef + " does not exist");
                } else {
                    return loader.map(con, rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

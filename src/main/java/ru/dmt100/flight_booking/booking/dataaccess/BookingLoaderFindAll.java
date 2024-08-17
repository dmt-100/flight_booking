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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("bookingLoaderFindAll")
public class BookingLoaderFindAll extends LoaderEntityImpl<BookingDtoResponse, String> {
    private final Loader<BookingDtoResponse> loader;
    private final String queryType;

    @Autowired
    public BookingLoaderFindAll(Loader<BookingDtoResponse> loader,
                                SqlQueryProvider sqlQueryProvider,
                                BookingSql bookingSql) {
        super(loader, sqlQueryProvider, bookingSql.getALL_BOOKINGS());
        this.loader = loader;
        this.queryType = bookingSql.getALL_BOOKINGS();
    }

    @Override
    public List<BookingDtoResponse> loadAll(Connection con, Integer limit) {
        List<BookingDtoResponse> list = new ArrayList<>();
        try (var stmt = con.prepareStatement(queryType);
             var rs = stmt.executeQuery()) {
            while (list.size() <= limit) {
                if (!rs.next()) {
                    throw new NotFoundException("No one entity does not exist");
                } else {
                    Optional<BookingDtoResponse> bookingDtoResponseOptional = loader.map(con, rs);
                    bookingDtoResponseOptional.ifPresent(list::add);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}

package ru.dmt100.flight_booking.booking.dataaccess;

import org.springframework.stereotype.Component;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoRequest;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.repository.updater.impl.UpdaterEntityImpl;

import java.sql.Connection;
import java.util.Optional;

@Component("bookingUpdater")
public class BookingUpdater extends UpdaterEntityImpl<BookingDtoRequest, BookingDtoResponse> {

    @Override
    public Optional<BookingDtoResponse> update(Connection con, BookingDtoRequest bookingDtoRequest) {
        return Optional.empty();
    }
}

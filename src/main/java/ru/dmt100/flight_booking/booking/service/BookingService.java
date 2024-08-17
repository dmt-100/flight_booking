package ru.dmt100.flight_booking.booking.service;

import ru.dmt100.flight_booking.booking.model.dto.BookingDtoRequest;

import java.util.List;

public interface BookingService {

    List<BookingDtoRequest> findBookingsByFlightId(Long userId, Long flightId);

}

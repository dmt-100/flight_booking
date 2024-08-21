package ru.dmt100.flight_booking.entity.booking.service;

import ru.dmt100.flight_booking.entity.booking.model.Booking;

import java.util.List;

public interface BookingService {

    List<Booking> findBookingsByFlightId(Long userId, Long flightId);

}

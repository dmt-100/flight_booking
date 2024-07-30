package ru.dmt100.bookingflight.booking.service;

import org.springframework.http.ResponseEntity;
import ru.dmt100.bookingflight.booking.model.Booking;
import ru.dmt100.bookingflight.booking.model.dto.PassengerInfo;

import java.util.List;

public interface BookingService {

    ResponseEntity<Booking> save(Long userId, Booking booking);

    Booking findBookingById(Long userId, String bookRef);

    List<PassengerInfo> findPassengersByBooking(Long userId, String bookRef);

    List<Booking> findBookingsByFlightId(Long userId, Long flightId);

    List<Booking> findAll(Long userId);

    ResponseEntity<?> update(Long userId, String bookRef);

    ResponseEntity<?> delete(Long userId, String bookRef);
}

package ru.dmt100.flight_booking.booking.service;

import org.springframework.http.ResponseEntity;
import ru.dmt100.flight_booking.booking.model.Booking;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.booking.model.dto.PassengerInfo;

import java.util.List;

public interface BookingService {

    BookingDtoResponse save(Long userId, Booking booking);

    BookingDtoResponse findBookingById(Long userId, String bookRef);

    List<PassengerInfo> findPassengersInfoByBookingId(Long userId, String bookRef);

    List<BookingDtoResponse> findBookingsByFlightId(Long userId, Long flightId, boolean isTickets);

    List<BookingDtoResponse> findAll(Long userId, Integer limit);

    BookingDtoResponse update(Long userId, String bookRef, Booking booking);

    ResponseEntity<?> delete(Long userId, String bookRef);
}

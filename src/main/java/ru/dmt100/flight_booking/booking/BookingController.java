package ru.dmt100.flight_booking.booking;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmt100.flight_booking.booking.model.Booking;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.booking.model.dto.PassengerInfo;
import ru.dmt100.flight_booking.booking.service.BookingService;

import java.util.List;

import static ru.dmt100.flight_booking.constant.Constant.USER_ID;

@RestController
@AllArgsConstructor
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookingDtoResponse> saveBooking(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestBody Booking booking) {
        BookingDtoResponse bookingDtoResponse = bookingService.save(userId, booking);
        return ResponseEntity.ok(bookingDtoResponse);
    }

    @GetMapping("/{bookRef}")
    public ResponseEntity<BookingDtoResponse> getBookingById(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String bookRef) {
        BookingDtoResponse booking = bookingService.findBookingById(userId, bookRef);
        return ResponseEntity.ok(booking);
    }


    @GetMapping("/bookingRef/{bookRef}")
    public ResponseEntity<List<PassengerInfo>> findPassengersByBooking(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String bookRef) {
        List<PassengerInfo> passengerInfos = bookingService.findPassengersInfoByBookingId(userId, bookRef);
        return ResponseEntity.ok(passengerInfos);
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<BookingDtoResponse>> getBookingsByFlightId(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable Long flightId,
            @RequestParam(value = "isTickets", required = false) Boolean isTickets) {
        List<BookingDtoResponse> bookings = bookingService.findBookingsByFlightId(userId, flightId, isTickets);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping()
    public ResponseEntity<List<BookingDtoResponse>> getAllBookings(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<BookingDtoResponse> bookings = bookingService.findAll(userId, limit);
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{bookRef}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<BookingDtoResponse> updateBooking(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String bookRef,
            @RequestBody Booking bookingDtoRequest) {
        BookingDtoResponse bookingDtoResponse = bookingService.update(userId, bookRef, bookingDtoRequest);
        return ResponseEntity.ok(bookingDtoResponse);
    }

    @DeleteMapping("/{bookRef}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteBooking(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String bookRef) {
        bookingService.delete(userId, bookRef);
        return ResponseEntity.accepted().build();
    }

}

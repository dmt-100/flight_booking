package ru.dmt100.flight_booking.booking;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmt100.flight_booking.booking.model.Booking;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.booking.model.dto.BookingLiteDtoResponse;
import ru.dmt100.flight_booking.booking.model.dto.PassengerInfo;
import ru.dmt100.flight_booking.booking.service.BookingService;

import java.util.List;

import static ru.dmt100.flight_booking.constant.Constant.USER_ID;
import static ru.dmt100.flight_booking.constant.Constant.X_PROCESSING_TIME;

@RestController
@AllArgsConstructor
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookingDtoResponse> createBooking(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestBody Booking booking) {
        double timeStart = System.currentTimeMillis();

        BookingDtoResponse bookingDtoResponse = bookingService.save(userId, booking);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000.0;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(bookingDtoResponse);
    }

    @GetMapping("/{bookRef}")
    public ResponseEntity<BookingDtoResponse> getBookingById(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestParam(value = "isTickets") Boolean isTickets,
            @PathVariable String bookRef) {
        double timeStart = System.currentTimeMillis();

        BookingDtoResponse booking = bookingService.get(userId, isTickets, bookRef);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(booking);
    }


    @GetMapping("/bookingRef/{bookRef}")
    public ResponseEntity<List<PassengerInfo>> findPassengersByBooking(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String bookRef) {
        double timeStart = System.currentTimeMillis();

        List<PassengerInfo> passengerInfos = bookingService.findPassengersInfoByBookingId(userId, bookRef);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(passengerInfos);
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<BookingDtoResponse>> getBookingsByFlightId(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable Long flightId,
            @RequestParam(value = "isTickets", required = false) Boolean isTickets) {
        double timeStart = System.currentTimeMillis();

        List<BookingDtoResponse> bookings = bookingService.findBookingsByFlightId(userId, flightId, isTickets);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(bookings);
    }

    @GetMapping()
    public ResponseEntity<List<BookingLiteDtoResponse>> getAllBookings(
            @RequestHeader(value = USER_ID, required = false) Long userId) {
        double timeStart = System.currentTimeMillis();

        List<BookingLiteDtoResponse> bookings = bookingService.findAll(userId);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(bookings);
    }

    @PatchMapping("/{bookRef}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<BookingDtoResponse> updateBooking(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String bookRef,
            @RequestBody Booking bookingDtoRequest) {
        double timeStart = System.currentTimeMillis();

        BookingDtoResponse bookingDtoResponse = bookingService.update(userId, bookRef, bookingDtoRequest);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(bookingDtoResponse);
    }

    @DeleteMapping("/{bookRef}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteBooking(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String bookRef) {
        double timeStart = System.currentTimeMillis();
        bookingService.delete(userId, bookRef);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.noContent().headers(headers).build();
    }

}

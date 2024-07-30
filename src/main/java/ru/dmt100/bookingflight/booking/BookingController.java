package ru.dmt100.bookingflight.booking;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmt100.bookingflight.booking.model.Booking;
import ru.dmt100.bookingflight.booking.model.dto.PassengerInfo;
import ru.dmt100.bookingflight.booking.service.BookingService;

import java.util.List;

import static ru.dmt100.bookingflight.constant.Constant.USER_ID;

@RestController
@AllArgsConstructor
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Booking> createBooking(@RequestHeader(value = USER_ID, required = false) Long userId,
                                                 @RequestBody Booking booking) {
            bookingService.save(userId,  booking);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/bookingRef/{bookRef}")
    public ResponseEntity<List<PassengerInfo>> findPassengersByBooking(@RequestHeader(value = USER_ID, required = false) Long userId,
                                                  @PathVariable String bookRef ) {
        List<PassengerInfo> passengers = bookingService.findPassengersByBooking(userId, bookRef);
        return ResponseEntity.ok(passengers);
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<Booking>> getBookingsByFlightId(
            @RequestHeader(value = USER_ID, required = false) Long userId, @PathVariable Long flightId) {
        List<Booking> bookings = bookingService.findBookingsByFlightId(userId, flightId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping()
    public ResponseEntity<List<Booking>> getAllBookings(
            @RequestHeader(value = USER_ID, required = false) Long userId) {
        List<Booking> bookings = bookingService.findAll(userId);
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{bookRef}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> updateBooking(
            @RequestHeader(value = USER_ID, required = false) Long userId, @PathVariable String bookRef) {
        bookingService.update(userId, bookRef);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{bookRef}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteBooking(
            @RequestHeader(value = USER_ID, required = false) Long userId, @PathVariable String bookRef) {
        bookingService.delete(userId, bookRef);
        return ResponseEntity.accepted().build();
    }

}

package ru.dmt100.flight_booking.entity.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmt100.flight_booking.dao.Dao;
import ru.dmt100.flight_booking.entity.booking.model.Booking;
import ru.dmt100.flight_booking.entity.booking.model.dto.BookingDto;
import ru.dmt100.flight_booking.entity.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.entity.booking.service.BookingService;
import ru.dmt100.flight_booking.util.HeadersMaker;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.dmt100.flight_booking.constant.Constant.USER_ID;
import static ru.dmt100.flight_booking.constant.Constant.X_PROCESSING_TIME;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final Dao dao;
    private final BookingService bookingService;

    @Autowired
    public BookingController(
            @Qualifier("bookingDaoImpl") Dao dao,
            @Qualifier("bookingServiceImpl") BookingService bookingService) {
        this.dao = dao;
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Optional<Booking>> save(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestBody BookingDto bookingDto) {
        double timeStart = System.currentTimeMillis();

        Optional<Booking> bookingDtoResponse = dao.save(userId, bookingDto);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000.0;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(bookingDtoResponse);
    }

    @GetMapping(params = "bookRef")
    public ResponseEntity<Optional<BookingDtoResponse>> find(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestParam String bookRef) {

        double timeStart = System.currentTimeMillis();

        Optional<BookingDtoResponse> booking = dao.find(userId, bookRef);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(booking);
    }


    @GetMapping(params = "limit")
    public ResponseEntity<?> findAllByDate(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestParam OffsetDateTime startTime,
            @RequestParam OffsetDateTime endTime,
            @RequestParam Integer limit) {
        double timeStart = System.currentTimeMillis();

        List<?> bookings = dao.find(userId, startTime, endTime, limit);

        return HeadersMaker.make(timeStart, bookings);
    }

    @PatchMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<BookingDtoResponse>> update(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestBody BookingDto bookingDto) {
        double timeStart = System.currentTimeMillis();

        Optional<BookingDtoResponse> bookingDtoResponse = dao.update(userId, bookingDto);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(bookingDtoResponse);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> delete(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestParam String bookRef) {
        double timeStart = System.currentTimeMillis();

        boolean isBookingDeleted = dao.delete(userId, bookRef);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body("Is booking deleted: " + isBookingDeleted);
    }

    @DeleteMapping("/deleteList")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteList(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestBody List<String> bookRefs) {
        double timeStart = System.currentTimeMillis();
        Map<String, Boolean> deletedBookings;
        deletedBookings = dao.deleteList(userId, bookRefs);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(deletedBookings);
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<?> findBookingsByFlightId(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable Long flightId) {
        double timeStart = System.currentTimeMillis();

        List<Booking> bookings = bookingService.findBookingsByFlightId(userId, flightId);

        return HeadersMaker.make(timeStart, bookings);
    }


}

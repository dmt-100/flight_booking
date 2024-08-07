package ru.dmt100.flight_booking.booking;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmt100.flight_booking.booking.model.Booking;
import ru.dmt100.flight_booking.booking.model.dto.*;
import ru.dmt100.flight_booking.booking.service.BookingService;
import ru.dmt100.flight_booking.dao.Dao;

import java.util.List;
import java.util.Optional;

import static ru.dmt100.flight_booking.constant.Constant.USER_ID;
import static ru.dmt100.flight_booking.constant.Constant.X_PROCESSING_TIME;

@RestController
@AllArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    @Qualifier("BookingDaoImpl")
    private final Dao bookingDao;

    @Qualifier("BookingServiceImpl")
    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Optional<BookingDtoResponse>> createBooking(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestBody Booking booking) {
        double timeStart = System.currentTimeMillis();

        Optional<BookingDtoResponse> bookingDtoResponse = bookingDao.save(userId, booking);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000.0;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(bookingDtoResponse);
    }

    @GetMapping("/{bookRef}")
    public ResponseEntity<Optional<BookingDtoResponse>> getBookingById(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String bookRef) {
        double timeStart = System.currentTimeMillis();

        Optional<BookingDtoResponse> booking = bookingDao.find(userId, bookRef);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(booking);
    }

    @GetMapping()
    public ResponseEntity<List<BookingLiteDtoResponse>> getAllBookings(
            @RequestHeader(value = USER_ID, required = false) Long userId) {
        double timeStart = System.currentTimeMillis();

        List<BookingLiteDtoResponse> bookings = bookingDao.findAll(userId);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(bookings);
    }

    @PatchMapping("/{bookRef}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<BookingDtoResponse>> update(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String bookRef,
            @RequestBody Booking bookingDtoRequest) {
        double timeStart = System.currentTimeMillis();

        Optional<BookingDtoResponse> bookingDtoResponse = bookingDao.update(userId, bookingDtoRequest);

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
        bookingDao.delete(userId, bookRef);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.noContent().headers(headers).build();
    }



    @GetMapping("/bookingRef/{bookRef}")
    public ResponseEntity<List<PassengerInfo>> getPassengersInfoByBooking(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String bookRef) {
        double timeStart = System.currentTimeMillis();

        List<PassengerInfo> passengerInfos = bookingService.findPassengersInfoByBookingId(userId, bookRef);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(passengerInfos);
    }

    @GetMapping("/flightId/{flightId}")
    public ResponseEntity<List<PassengerInfo>> getPassengersInfoByFlight(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable Long flightId) {
        double timeStart = System.currentTimeMillis();

        List<PassengerInfo> passengerInfos = bookingService.findPassengersInfoByFlightId(userId, flightId);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");
        headers.add("X-Total-Records", String.valueOf(passengerInfos.size()));

        return ResponseEntity.ok()
                .header(X_PROCESSING_TIME, qTime + " sec.")
                .header("X-Total-Records", String.valueOf(passengerInfos.size()))
                .body(passengerInfos);
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<BookingDtoResponse>> getBookingsByFlightId(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable Long flightId) {
        double timeStart = System.currentTimeMillis();

        List<BookingDtoResponse> bookings = bookingService.getBookingsByFlightId(userId, flightId);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(bookings);
    }

    @GetMapping("/statistics/daily")
    public ResponseEntity<List<BookingStatisticsDateDto>> getDailyBookingStatistics(
            @RequestHeader(value = USER_ID, required = false) Long userId) {
        double timeStart = System.currentTimeMillis();

        List<BookingStatisticsDateDto> statistics = bookingService.getStats_RevenueByDate(userId);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Processing-Time", qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(statistics);
    }

    @GetMapping("/statistics/weekly")
    public ResponseEntity<List<BookingStatisticsWeekDto>> getWeeklyBookingSummary(
            @RequestHeader(value = USER_ID, required = false) Long userId) {
        double timeStart = System.currentTimeMillis();

        List<BookingStatisticsWeekDto> statistics = bookingService.getStats_RevenueByWeek(userId);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Processing-Time", qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(statistics);
    }


}

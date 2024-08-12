package ru.dmt100.flight_booking.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmt100.flight_booking.booking.model.Booking;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.booking.model.dto.BookingLiteDtoResponse;
import ru.dmt100.flight_booking.booking.model.dto.records.*;
import ru.dmt100.flight_booking.booking.service.BookingService;
import ru.dmt100.flight_booking.dao.Dao;
import ru.dmt100.flight_booking.util.ResponseUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.dmt100.flight_booking.constant.Constant.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final Dao bookingDao;
    private final BookingService bookingService;

    @Autowired
    public BookingController(@Qualifier("bookingDaoImpl") Dao bookingDao,
                             @Qualifier("bookingServiceImpl") BookingService bookingService) {
        this.bookingDao = bookingDao;
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Optional<BookingDtoResponse>> save(
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
    public ResponseEntity<Optional<BookingDtoResponse>> find(
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
    public ResponseEntity<List<BookingLiteDtoResponse>> findAllBookings(
            @RequestHeader(value = USER_ID, required = false) Long userId) {
        double timeStart = System.currentTimeMillis();

        List<BookingLiteDtoResponse> bookings = bookingDao.findAll(userId);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");
        headers.add(X_TOTAL_RECORDS, String.valueOf(bookings.size()));

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


    @GetMapping("/flight/{flightId}")
    public ResponseEntity<?> findBookingsByFlightId(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable Long flightId) {
        double timeStart = System.currentTimeMillis();

        List<BookingDtoResponse> bookings = bookingService.getBookingsByFlightId(userId, flightId);

        return ResponseUtil.headersMaker(timeStart, bookings);
    }

    @GetMapping("/stats/daily")
    public ResponseEntity<?> getBookingStatsDaily(
            @RequestHeader(value = USER_ID, required = false) Long userId) {
        double timeStart = System.currentTimeMillis();

        List<DailyBookingStats> stats = bookingService.getDailyBookingStats(userId);

        return ResponseUtil.headersMaker(timeStart, stats);
    }

    @GetMapping("/stats/weekly")
    public ResponseEntity<?> getBookingStatsWeekly(
            @RequestHeader(value = USER_ID, required = false) Long userId) {
        double timeStart = System.currentTimeMillis();

        List<WeeklyBookingStats> stats = bookingService.getWeeklyBookingStats(userId);

        return ResponseUtil.headersMaker(timeStart, stats);
    }

    @GetMapping("/stats/spentByPassenger/{limit}")
    public ResponseEntity<?> getTotalAmountSpentByPassenger(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable int limit) {
        double timeStart = System.currentTimeMillis();

        List<TotalAmountSpentByPassenger> stats = bookingService.getTotalAmountSpentByPassenger(userId, limit);

        return ResponseUtil.headersMaker(timeStart, stats);
    }

    @GetMapping("/stats/revenueByBookingByAirport")
    public ResponseEntity<List<RevenueByBookingsByAirport>> getRevenueByBookingByAirport(
            @RequestHeader(value = USER_ID, required = false) Long userId) {
        double timeStart = System.currentTimeMillis();

        List<RevenueByBookingsByAirport> stats =
                bookingService.getTotalRevenueByBookingsByAirport(userId);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");
        headers.add(X_TOTAL_RECORDS, String.valueOf(stats.size()));

        return ResponseEntity.ok().headers(headers).body(stats);
    }

    @GetMapping("/stats/summaryBookCountWithClassification")
    public ResponseEntity<?> getSummaryClassification(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        double timeStart = System.currentTimeMillis();

        List<SummaryBookCountWithClassification> stats = bookingService
                .getSummaryClassification(userId, startDate, endDate);

        return ResponseUtil.headersMaker(timeStart, stats);
    }

}

package ru.dmt100.flight_booking.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmt100.flight_booking.entity.booking.model.stats.*;
import ru.dmt100.flight_booking.statistic.service.Statistic;
import ru.dmt100.flight_booking.util.HeadersMaker;

import java.time.LocalDate;
import java.util.List;

import static ru.dmt100.flight_booking.constant.Constant.*;

@RestController
@RequestMapping("/stat")
public class StatisticController {

    private final Statistic stat;

    @Autowired
    public StatisticController(@Qualifier("statisticImpl") Statistic stat) {
        this.stat = stat;
    }

    @GetMapping("/stats/daily")
    public ResponseEntity<?> getBookingStatsDaily(
            @RequestHeader(value = USER_ID, required = false) Long userId) {
        double timeStart = System.currentTimeMillis();

        List<DailyBookingStats> stats = stat.getDailyBookingStats(userId);

        return HeadersMaker.make(timeStart, stats);
    }

    @GetMapping("/stats/weekly")
    public ResponseEntity<?> getBookingStatsWeekly(
            @RequestHeader(value = USER_ID, required = false) Long userId) {
        double timeStart = System.currentTimeMillis();

        List<WeeklyBookingStats> stats = stat.getWeeklyBookingStats(userId);

        return HeadersMaker.make(timeStart, stats);
    }

    @GetMapping("/stats/spentByPassenger/{limit}")
    public ResponseEntity<?> getTotalAmountSpentByPassenger(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable int limit) {
        double timeStart = System.currentTimeMillis();

        List<TotalAmountSpentByPassenger> stats = stat.getTotalAmountSpentByPassenger(userId, limit);

        return HeadersMaker.make(timeStart, stats);
    }

    @GetMapping("/stats/revenueByBookingByAirport")
    public ResponseEntity<List<RevenueByBookingsByAirport>> getRevenueByBookingByAirport(
            @RequestHeader(value = USER_ID, required = false) Long userId) {
        double timeStart = System.currentTimeMillis();

        List<RevenueByBookingsByAirport> stats =
                stat.getTotalRevenueByBookingsByAirport(userId);

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

        List<SummaryBookCountWithClassification> stats = stat
                .getSummaryClassification(userId, startDate, endDate);

        return HeadersMaker.make(timeStart, stats);
    }
}

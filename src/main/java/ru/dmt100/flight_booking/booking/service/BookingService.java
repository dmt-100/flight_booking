package ru.dmt100.flight_booking.booking.service;

import ru.dmt100.flight_booking.booking.model.dto.BookingLiteDto;
import ru.dmt100.flight_booking.booking.model.dto.stats.*;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    List<BookingLiteDto> findBookingsByFlightId(Long userId, Long flightId);

    List<DailyBookingStats> getDailyBookingStats(Long userId);

    List<WeeklyBookingStats> getWeeklyBookingStats(Long userId);

    List<TotalAmountSpentByPassenger> getTotalAmountSpentByPassenger(Long userId, int limit);


    List<RevenueByBookingsByAirport> getTotalRevenueByBookingsByAirport(
            Long userId);

    List<SummaryBookCountWithClassification> getSummaryClassification(Long userId, LocalDate date1, LocalDate date2);
}

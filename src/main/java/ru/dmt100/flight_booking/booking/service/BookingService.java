package ru.dmt100.flight_booking.booking.service;

import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.booking.model.dto.records.*;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    List<BookingDtoResponse> getBookingsByFlightId(Long userId, Long flightId);

    List<DailyBookingStats> getDailyBookingStats(Long userId);

    List<WeeklyBookingStats> getWeeklyBookingStats(Long userId);

    List<TotalAmountSpentByPassenger> getTotalAmountSpentByPassenger(Long userId, int limit);


    List<RevenueByBookingsByAirport> getTotalRevenueByBookingsByAirport(
            Long userId);

    List<SummaryBookCountWithClassification> getSummaryClassification(Long userId, LocalDate date1, LocalDate date2);
}

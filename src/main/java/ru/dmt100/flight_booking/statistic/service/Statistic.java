package ru.dmt100.flight_booking.statistic.service;

import ru.dmt100.flight_booking.entity.booking.model.stats.*;

import java.time.LocalDate;
import java.util.List;

public interface Statistic {


    List<DailyBookingStats> getDailyBookingStats(Long userId);

    List<WeeklyBookingStats> getWeeklyBookingStats(Long userId);

    List<TotalAmountSpentByPassenger> getTotalAmountSpentByPassenger(Long userId, int limit);

    List<RevenueByBookingsByAirport> getTotalRevenueByBookingsByAirport(Long userId);

    List<SummaryBookCountWithClassification> getSummaryClassification(Long userId, LocalDate date1, LocalDate date2);


}

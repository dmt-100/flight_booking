package ru.dmt100.flight_booking.entity.booking.model.stats;


import java.time.LocalDate;

public record DailyBookingStats(
        LocalDate date,
        String month,
        Long totalBookings,
        Long totalRevenue,
        Long avgBookingAmount) {
}

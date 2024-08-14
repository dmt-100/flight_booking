package ru.dmt100.flight_booking.booking.model.dto.stats;


import java.time.LocalDate;

public record DailyBookingStats(
        LocalDate date,
        String month,
        Long totalBookings,
        Long totalRevenue,
        Long avgBookingAmount) {
}

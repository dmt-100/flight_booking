package ru.dmt100.flight_booking.booking.model.stats;

public record WeeklyBookingStats(
        Integer weekOfYear,
        Long totalBookings,
        Long totalRevenue,
        Long avgBookingAmount) {
}

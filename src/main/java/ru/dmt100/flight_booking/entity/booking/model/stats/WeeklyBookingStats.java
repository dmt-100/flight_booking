package ru.dmt100.flight_booking.entity.booking.model.stats;

public record WeeklyBookingStats(
        Integer weekOfYear,
        Long totalBookings,
        Long totalRevenue,
        Long avgBookingAmount) {
}

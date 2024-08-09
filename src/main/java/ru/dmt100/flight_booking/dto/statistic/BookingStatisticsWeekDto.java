package ru.dmt100.flight_booking.booking.model.dto.statistic;

public record BookingStatisticsWeekDto(Integer weekOfYear, Long totalBookings, Long totalRevenue, Long avgBookingAmount) {
}

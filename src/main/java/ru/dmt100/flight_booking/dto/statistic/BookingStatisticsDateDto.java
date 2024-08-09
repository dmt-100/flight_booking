package ru.dmt100.flight_booking.booking.model.dto.statistic;


import java.time.LocalDate;

public record BookingStatisticsDateDto(LocalDate date, String month, Long totalBookings, Long totalRevenue, Long avgBookingAmount) {
}

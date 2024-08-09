package ru.dmt100.flight_booking.dto.statistic;

import java.math.BigDecimal;

public record RevenueByBookingsByAirports(
        String flightNo,
        String departureAirport,
        String arrivalAirport,
        Long totalBookings,
        BigDecimal totalRevenue
) {
}

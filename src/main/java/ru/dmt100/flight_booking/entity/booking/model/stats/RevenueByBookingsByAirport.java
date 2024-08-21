package ru.dmt100.flight_booking.entity.booking.model.stats;

import java.math.BigDecimal;

public record RevenueByBookingsByAirport(
        String flightNo,
        String departureAirport,
        String arrivalAirport,
        Long totalBookings,
        BigDecimal totalRevenue
) {
}

package ru.dmt100.flight_booking.booking.model.dto.records;

import java.math.BigDecimal;

public record RevenueByBookingsByAirport(
        String flightNo,
        String departureAirport,
        String arrivalAirport,
        Long totalBookings,
        BigDecimal totalRevenue
) {
}

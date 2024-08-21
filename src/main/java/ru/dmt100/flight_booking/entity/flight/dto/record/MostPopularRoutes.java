package ru.dmt100.flight_booking.entity.flight.dto.record;

public record MostPopularRoutes(
        String departureAirport,
        String arrivalAirport,
        Integer routeCount
) {
}

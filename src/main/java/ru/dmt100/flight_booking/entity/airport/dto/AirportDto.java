package ru.dmt100.flight_booking.entity.airport.dto;

import org.postgresql.geometric.PGpoint;

public record AirportDto(
        String airportCode,
        String airportName,
        String city,
        PGpoint coordinates,
        String timezone) {
}

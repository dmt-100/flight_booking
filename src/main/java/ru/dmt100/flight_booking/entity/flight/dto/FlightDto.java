package ru.dmt100.flight_booking.entity.flight.dto;

import java.time.ZonedDateTime;

public record FlightDto(
        Long flightId,
        String flightNo,
        ZonedDateTime scheduledDeparture,
        ZonedDateTime scheduledArrival,
        String departureAirport,
        String arrivalAirport,
        String status,
        String aircraftCode,
        ZonedDateTime actualDeparture,
        ZonedDateTime actualArrival) {
}

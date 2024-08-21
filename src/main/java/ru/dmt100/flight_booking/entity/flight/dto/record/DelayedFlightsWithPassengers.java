package ru.dmt100.flight_booking.entity.flight.dto.record;

public record DelayedFlightsWithPassengers(
        String flightNo,
        Integer passengersCount
) {
}

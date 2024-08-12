package ru.dmt100.flight_booking.aircraft.dto;

public record AircraftDto (
        String aircraftCode,
        String model,
        Integer range) {
}
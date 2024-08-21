package ru.dmt100.flight_booking.entity.aircraft.dto;

public record AircraftDto (
        String aircraftCode,
        String model,
        Integer range) {
}
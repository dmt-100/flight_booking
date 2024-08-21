package ru.dmt100.flight_booking.entity.flight.dto.record;

import ru.dmt100.flight_booking.enums.Status;

public record FlightCountByStatus(
        Status status,
        Integer flightsCount
) {
}

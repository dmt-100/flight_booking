package ru.dmt100.flight_booking.flight.dto.record;

import java.time.LocalDate;

public record FlightsCountByMonth(
        LocalDate month,
        Integer flightsCount
) {
}

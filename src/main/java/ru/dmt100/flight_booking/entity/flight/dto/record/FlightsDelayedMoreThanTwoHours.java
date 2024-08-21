package ru.dmt100.flight_booking.entity.flight.dto.record;

import java.time.LocalDate;

public record FlightsDelayedMoreThanTwoHours(
        Long flightId,
        LocalDate scheduledDeparture,
        LocalDate actual_departure,
        Float delayHours
) {

}

package ru.dmt100.flight_booking.flight.dto.record;

import java.time.LocalDate;

public record FlightsDelayedMoreThanTwoHours(
        Long flightId,
        LocalDate scheduledDeparture,
        LocalDate actual_departure,
        Float delayHours
) {

}

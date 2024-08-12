package ru.dmt100.flight_booking.flight.dto.record;

public record AvgDelayByDayOfWeek(
        String dayOfWeek,
        Float averageDelayHours
) {
}

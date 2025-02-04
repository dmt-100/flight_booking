package ru.dmt100.flight_booking.entity.ticket.model.dto.record;

import ru.dmt100.flight_booking.enums.Status;

import java.time.LocalDateTime;

public record TicketOnScheduledFlightsByTimeRange(
        String flightNo,
        LocalDateTime scheduledDeparture,
        LocalDateTime scheduledArrival,
        Status status,
        Integer ticketCount
) {}

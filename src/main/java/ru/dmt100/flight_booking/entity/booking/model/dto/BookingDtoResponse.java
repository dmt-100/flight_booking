package ru.dmt100.flight_booking.entity.booking.model.dto;

import ru.dmt100.flight_booking.entity.ticket.model.Ticket;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

public record BookingDtoResponse(
        String bookRef,
        ZonedDateTime bookDate,
        BigDecimal totalAmount,
        Set<Ticket> ticketNos) {}

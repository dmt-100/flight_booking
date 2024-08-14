package ru.dmt100.flight_booking.booking.model.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

public record BookingLiteDto(
        String bookRef,
        ZonedDateTime bookDate,
        BigDecimal totalAmount,
        Set<String> ticketNos
) {
}

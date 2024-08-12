package ru.dmt100.flight_booking.booking.model.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record BookingDtoResponse(
        String bookRef,
        ZonedDateTime bookDate,
        BigDecimal totalAmount) {
}

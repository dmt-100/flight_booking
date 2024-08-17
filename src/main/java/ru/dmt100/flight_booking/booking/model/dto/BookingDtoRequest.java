package ru.dmt100.flight_booking.booking.model.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record BookingDtoRequest(
        String bookRef,
        ZonedDateTime bookDate,
        BigDecimal totalAmount) {
}

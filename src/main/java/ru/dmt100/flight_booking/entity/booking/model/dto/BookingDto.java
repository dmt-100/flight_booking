package ru.dmt100.flight_booking.entity.booking.model.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record BookingDto(
        String bookRef,
        ZonedDateTime bookDate,
        BigDecimal totalAmount) {

}

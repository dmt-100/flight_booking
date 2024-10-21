package ru.dmt100.flight_booking.entity.booking.model.dto;

import ru.dmt100.flight_booking.util.KeyProvider;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record BookingDto (
        String bookRef,
        OffsetDateTime bookDate,
        BigDecimal totalAmount) implements KeyProvider<String> {

    public String getId() {
        return bookRef;
    }


    @Override
    public String getKey() {
        return bookRef;
    }

}

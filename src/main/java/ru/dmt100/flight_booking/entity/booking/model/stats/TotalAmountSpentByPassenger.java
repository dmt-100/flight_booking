package ru.dmt100.flight_booking.entity.booking.model.stats;

import java.math.BigDecimal;

public record TotalAmountSpentByPassenger(
        Long passengerId,
        String passengerName,
        BigDecimal totalSpent) {

}

package ru.dmt100.flight_booking.booking.model.stats;

import java.math.BigDecimal;

public record TotalAmountSpentByPassenger(
        Long passengerId,
        String passengerName,
        BigDecimal totalSpent) {

}

package ru.dmt100.flight_booking.booking.model.dto.statistic;

import java.math.BigDecimal;

public record PassengerSpentDto(Long passengerId, String passengerName, BigDecimal totalSpent) {

}

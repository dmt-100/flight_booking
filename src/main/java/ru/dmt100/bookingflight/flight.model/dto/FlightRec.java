package ru.dmt100.bookingflight.flight.model.dto;

import java.sql.Date;

public record FlightRec(Integer flightId, String flightNo, Date scheduled_departure, Date scheduled_arrival,
                     String airport_name) {
}

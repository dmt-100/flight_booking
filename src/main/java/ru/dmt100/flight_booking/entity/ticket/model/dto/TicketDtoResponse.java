package ru.dmt100.flight_booking.entity.ticket.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class TicketDtoResponse {

    @JsonProperty("ticket_no")
    Long ticketNo;

    @JsonProperty("book_ref")
    String bookRef;

    @JsonProperty("passenger_id")
    Long passengerId;

    @JsonProperty("passenger_name")
    String passengerName;

    @JsonProperty("contact_data")
    Map<String, String> contactData;

    @JsonProperty("flight_no")
    String flightNo;

    @JsonProperty("seat_no")
    String seatNo;

    String fareConditions;

    @JsonProperty("boarding_no")
    String boardingNo;

    @JsonProperty("scheduled_departure")
    ZonedDateTime scheduledDeparture;

    @JsonProperty("scheduled_arrival")
    ZonedDateTime scheduledArrival;

    @JsonProperty("departure_Airport")
    String departureAirport;

    @JsonProperty("arrival_Airport")
    String arrivalAirport;

    @JsonProperty("aircraft_model")
    String aircraftModel;

    String boardingPassCompositeKey;
}

package ru.dmt100.flight_booking.ticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.dmt100.flight_booking.boardingPass.model.dto.BoardingPassDto;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TicketLiteDtoResponse {

    @JsonProperty("ticket_no")
    String ticketNo;

    @JsonProperty("book_ref")
    String bookRef;

    @JsonProperty("passenger_id")
    Long passengerId;

    @JsonProperty("passenger_name")
    String passengerName;

    @JsonProperty("contact_data")
    Map<String, String> contactData;

}

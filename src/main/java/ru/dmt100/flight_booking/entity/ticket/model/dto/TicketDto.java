package ru.dmt100.flight_booking.entity.ticket.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonPropertyOrder({
        "ticket_no",
        "book_ref",
        "passenger_id",
        "passenger_name",
        "contact_data",
        "boardingPassCompositeKeys" })
public class TicketDto {

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

    List<String> boardingPassCompositeKeys;

}

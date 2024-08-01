package ru.dmt100.flight_booking.ticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.dmt100.flight_booking.ticket.model.Ticket;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TicketLiteDtoResponse {

    @JsonProperty("ticket_no")
    Long ticketNo;

    @JsonProperty("book_ref")
    String bookRef;

    @JsonProperty("passenger_id")
    Long passengerId;

    @JsonProperty("passenger_name")
    String passengerName;

    @JsonProperty("contact_data")
    String contactData;

    public void getTicketLiteDtoResponseFromTicket(Ticket ticket) {
        this.ticketNo = ticket.getTicketNo();
        this.bookRef = ticket.getBookRef();;
        this.passengerId = ticket.getPassengerId();
        this.passengerName = ticket.getPassengerName();
        this.contactData = ticket.getContactData();
    }
}

package ru.dmt100.flight_booking.booking.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.dmt100.flight_booking.ticket.dto.TicketLiteDtoResponse;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookingDtoResponse {

    @JsonProperty("book_ref")
    private String bookRef;

    @JsonProperty("book_date")
    private ZonedDateTime bookDate;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    @JsonProperty("tickets")
    private Set<TicketLiteDtoResponse> tickets;
}

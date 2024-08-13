package ru.dmt100.flight_booking.booking.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingLiteDto {

    @JsonProperty("book_ref")
    String bookRef;

    @JsonProperty("book_date")
    ZonedDateTime bookDate;

    @JsonProperty("total_amount")
    BigDecimal totalAmount;

    @JsonProperty("tickets")
    Set<String> tickets;
}

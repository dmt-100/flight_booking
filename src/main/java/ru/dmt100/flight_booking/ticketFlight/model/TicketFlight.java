package ru.dmt100.flight_booking.ticketFlight.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.dmt100.flight_booking.airport.enums.FareConditions;
import ru.dmt100.flight_booking.flight.model.Flight;

import java.math.BigDecimal;

@Entity
@Table(name = "ticket_flights")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class TicketFlight {

    @Id
    @JoinColumn(name = "ticket_no",
            insertable = false,
            updatable = false)
    String ticketNo;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    Flight flight;

    @Enumerated(EnumType.STRING)
    @Column(name = "fare_conditions", nullable = false)
    FareConditions fareConditions;

    @Column(name = "amount", nullable = false)
    BigDecimal amount;

}
